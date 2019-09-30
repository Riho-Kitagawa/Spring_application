package jp.co.sss.shop.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.OrderBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.form.OrderShowForm;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.util.PriceCalc;

/**
 * 注文管理 一覧表示機能(運用管理者用)のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class OrderShowAdminController {

	/**
	 * 注文情報
	 */
	@Autowired
	OrderRepository orderRepository;
	
	/**
	 * セッション
	 */
	@Autowired
	HttpSession session;

	/**
	 * 注文情報一覧表示処理
	 *
	 * @param model
	 *            Viewとの値受渡し
	 * @param form
	 *            表示用注文情報
	 * @param session
	 *            セッション情報
	 * @param pageable
	 *            ページング情報
	 * @return "order/list/order_list_admin" 注文情報 一覧画面へ
	 */
	@RequestMapping(path = "/order/list/admin", method = RequestMethod.GET)
	public String showOrderList(Model model, @ModelAttribute OrderShowForm form, 
	        Pageable pageable) {

		// すべての注文情報を取得
		Page<Order> orderList = orderRepository.findAllOrderByInsertDateDesc(pageable);		

		// 注文情報リストを生成
		List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
		for (Order order : orderList) {
			OrderBean orderBean = new OrderBean();
			orderBean.setId(order.getId());
			orderBean.setUserName(order.getUser().getName());
			orderBean.setInsertDate(order.getInsertDate().toString());
			orderBean.setPayMethod(order.getPayMethod());

			//注文時点の単価を合計するための一時変数
			int total = 0;

			//orderレコードから紐づくOrderItemのListを取り出す
			List<OrderItem> orderItemList = order.getOrderItemsList();
			
			for(OrderItem orderItem :  orderItemList) {
				
				//購入時単価 * 買った個数をもとめて、合計に加算
				total += ( orderItem.getPrice() * orderItem.getQuantity() );
			}
			//Orderに改めて注文時点の単価をセット
			orderBean.setTotal(total);
			
			orderBeanList.add(orderBean);
		}

		// 注文情報リストをViewへ渡す
		model.addAttribute("pages", orderList);
		model.addAttribute("orders", orderBeanList);
		model.addAttribute("url", "/order/list/admin");

		return "order/list/order_list_admin";

	}

	/**
	 * 注文情報詳細表示処理
	 *
	 * @param model
	 *            Viewとの値受渡し
	 * @param form
	 *            表示用注文情報
	 * @param session
	 *            セッション情報
	 * @return "/order/detail/order_detail_admin" 注文情報 詳細画面へ
	 */
	@RequestMapping(path = "/order/detail/admin/{id}")
	public String showOrder(@PathVariable int id, Model model, @ModelAttribute OrderShowForm form) {

		// 選択された注文情報に該当する情報を取得
		Order order = orderRepository.findById(form.getId()).orElse(null);

		// 表示する注文情報を生成
		OrderBean orderBean = new OrderBean();
		BeanUtils.copyProperties(order, orderBean);
		orderBean.setInsertDate(order.getInsertDate().toString());
		
		// 会員名を注文情報に設定
		orderBean.setUserName(order.getUser().getName());
		
		// 注文商品情報を取得
		List<OrderItemBean> orderItemBeanList = new ArrayList<OrderItemBean>();
		for (OrderItem orderItem : order.getOrderItemsList()) {
			OrderItemBean orderItemBean = new OrderItemBean();
			
			orderItemBean.setName(orderItem.getItem().getName());
			orderItemBean.setPrice(orderItem.getPrice());
			orderItemBean.setOrderNum(orderItem.getQuantity());
			
			//購入時単価の合計値を計算
			//※OrderItemのItemフィールドからgetPriceを利用すると、購入時ではなく現在の単価になってしまう。
			int subtotal = orderItem.getPrice() * orderItem.getQuantity();
			
			orderItemBean.setSubtotal(subtotal);
			
			orderItemBeanList.add(orderItemBean);
		}
		
		// 合計金額を算出
		int total = PriceCalc.orderItemPriceTotal(orderItemBeanList);

		// 注文情報をViewへ渡す
		model.addAttribute("order", orderBean);
		model.addAttribute("orderItemBeans", orderItemBeanList);
		model.addAttribute("total", total);

		return "order/detail/order_detail_admin";
	}

}
