package jp.co.sss.shop.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.AddressForm;
import jp.co.sss.shop.form.OrderForm;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.repository.UserRepository;

/**
 * 注文登録 一覧表示機能(一般会員用)のコントローラクラス
 *
 * @author Kubota
 */
@Controller
public class OrderRegistCustomerController {

	/**
	 * 商品情報
	 */
	@Autowired
	ItemRepository itemRepository;
	/**
	 * ユーザー情報
	 */
	@Autowired
	UserRepository userRepository;
	/**
	 * 購入商品情報
	 */
	@Autowired
	OrderItemRepository orderItemRepository;
	/**
	 * 購入者情報
	 */
	@Autowired
	OrderRepository orderRepository;
	/**
	 * セッション
	 */
	@Autowired
	HttpSession session;
	/**
	 * エラーメッセージ
	 */
	@Autowired
	MessageSource msg;

//　稼働機能一覧
// 〇4-1-1-2 買い物かご画面 「ご注文のお手続き」ボタン /address/input POST
//	〇5-3-1-2 届け先入力画面 「次へ」ボタン /payment/input POST 
//	〇5-3-1-2 届け先入力画面 「戻る」ボタン /basket/list?page=0 GET 
//	〇5-3-2-2 支払方法選択画面 「次へ」ボタン /order/check POST 
//	〇5-3-2-2 支払方法選択画面 「戻る」ボタン /address/input POST 
//	〇5-3-3-2 注文登録確認画面 「ご注文の確定」ボタン /order/complete POST 
//	〇5-3-3-2 注文登録確認画面 「戻る」ボタン /payment/input POST 
//	5-3-4-2 注文登録完了画面 「トップ画面へ」リンク / GET 

	/**
	 * 届け先入力画面へ遷移 4-1-1-2 買い物かご画面 「ご注文のお手続き」ボタン /address/input POST 入力内容に不備があった場合
	 * /address/input GET 5-3-2-2 支払方法選択画面 「戻る」ボタン /address/input POST
	 *
	 * @param model		Viewとの値受渡し
	 * @param form		入力された届け先情報
	 * @param backflg		戻るボタンを押されたときの判断(戻るからであればflg==true)
	 * 
	 * @return "order/regist/order_address_input" 届け先入力画面へ
	 */
	@RequestMapping(path = "/address/input", method = RequestMethod.POST)
	public String inputAddress(@ModelAttribute AddressForm form, Model model, boolean backflg) {
		// 戻るボタンを使ったとき：直前に入力された値を引き継ぐ
		if (backflg == true) {
			model.addAttribute("user", form);
		} else {
			// それ以外の場合
			UserBean loginUser = (UserBean) session.getAttribute("user");
			int id = loginUser.getId();
			User user = userRepository.findById(id);
			model.addAttribute("user", user);
		}
		// 遷移
		return "order/regist/order_address_input";
	}
	@RequestMapping(path = "/address/input", method = RequestMethod.GET)
	public String inputAddressRedirect(Model model, @ModelAttribute("form") AddressForm form) {
		/// payment/inputで入力が不正だった場合に送ったエラーや入力内容を受け取って遷移
		model.addAttribute("user", form);
		return "order/regist/order_address_input";
	}

	/**
	 * 支払い方法選択画面表示処理 5-3-1-2 届け先入力画面 「次へ」ボタン /payment/input POST 5-3-3-2 注文登録確認画面
	 * 「戻る」ボタン /payment/input POST
	 *
	 * @param form		入力された送付先情報
	 * @param result		入力チェックの結果
	 * @param attributes		入力に不備があった場合にリダイレクト先に値を受け渡す
	 * @param model		Viewとの値受渡し
	 * 
	 * @return "order/regist/order_payment_input" 支払い方法選択画面へ
	 */
	@RequestMapping(path = "/payment/input", method = RequestMethod.POST)
	public String inputPayment(@Valid @ModelAttribute AddressForm form, BindingResult result,
			RedirectAttributes attributes, Model model) {
		// 入力に不備があった場合
		if (result.hasErrors()) {
			attributes.addFlashAttribute("form", form);
			attributes.addFlashAttribute("errors", result);
			attributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(form), result);
			return "redirect:/address/input";
		}
		// 正常な入力だった場合
		model.addAttribute("user", form);
		return "order/regist/order_payment_input";
	}

	/**
	 * 注文確認画面表示処理 5-3-2-2 支払方法選択画面 「次へ」ボタン /order/check POST
	 *
	 * @param model         Viewとの値受渡し
	 * @param form     入力された届け先情報・支払い方法
	 * @param result 入力チェック
	 * 
	 * @return "order/regist/order_check" 注文確認画面へ
	 */
	@RequestMapping(path = "/order/check", method = RequestMethod.POST)
	public String checkOrder(@Valid @ModelAttribute OrderForm form, BindingResult result, Model model,
			HttpSession session) {

		// 買い物かごの中身を判断‐在庫確認、最終的なリスト(OrderItemBean)への追加、小計/合計の算出
		@SuppressWarnings("unchecked") // ↓なぜかここが黄色波線になるので
		List<BasketBean> cartItems = (List<BasketBean>) session.getAttribute("cartItems");
		List<OrderItemBean> items = new ArrayList<>();
		int total = 0;
		List<String> errorMessages = null;
		String errorMessage = null;

		for (int i = 0; i < cartItems.size(); i++) {
			BasketBean cartItem = cartItems.get(i);
			OrderItemBean item = new OrderItemBean();

			// 対象商品の検索
			Item wantItem = itemRepository.getOne(cartItem.getId());
			int stock = wantItem.getStock(); // 在庫
			int orderNum = cartItem.getOrderNum(); // 注文個数

			// 在庫数と注文数の確認
			if (stock == 0) {// 在庫がなかった時：エラー文を吐かせる・リストに追加しない
				if (errorMessages == null) {
					errorMessages = new ArrayList<>();
				}
				errorMessage = msg.getMessage("orderResist.itemStockNone", new String[] { wantItem.getName() }, null);
				errorMessages.add(errorMessage);
				continue;

			} else if (stock < orderNum) {// 在庫より注文数が多かった時：注文個数を在庫数にする・エラー文を吐かせる
				item.setOrderNum(stock); // 注文個数
				if (errorMessages == null) {
					errorMessages = new ArrayList<>();
				}
				errorMessage = msg.getMessage("orderResist.itemStockNotEnough", new String[] { wantItem.getName() },
						null);
				errorMessages.add(errorMessage);

			} else {	// 注文数より在庫が多かった時(平常)
				item.setOrderNum(cartItem.getOrderNum()); // 注文個数

			}
			item.setId(cartItem.getId()); // 商品ID
			item.setName(cartItem.getName()); // 商品名
			item.setPrice(wantItem.getPrice()); // 単価
			item.setImage(wantItem.getImage()); // 画像ファイル名
			item.setSubtotal(item.getPrice() * item.getOrderNum()); // 小計
			// リストに追加
			items.add(item);
			total = total + item.getSubtotal(); // 合計
		}

		// 最終的なリスト、合計、届け先、支払い方法の受け渡し
		model.addAttribute("user", form);
		model.addAttribute("items", items);
		model.addAttribute("total", total);
		model.addAttribute("errorMessagesOrderItem", errorMessages);
		return "order/regist/order_check";
	}

	/**
	 * 注文確定画面表示処理 5-3-3-2 注文登録確認画面 「ご注文の確定」ボタン /order/complete POST
	 *
	 * @param session			買い物かごの中身受け渡し用				
	 * @param form 				OrderForm(入力させた注文情報)
	 * 
	 * @return "order/regist order_complete" 	注文確定画面へ
	 */
	@RequestMapping(path = "/order/complete", method = RequestMethod.POST)
	public String completeOrder(@ModelAttribute OrderForm form, HttpSession session) {
//エンティティの作成… ①Order
		Order order = new Order();
		order.setPostalCode(form.getPostalCode()); // 郵便番号
		order.setAddress(form.getAddress()); // 住所
		order.setName(form.getName()); // 名前
		order.setPhoneNumber(form.getPhoneNumber()); // 電話番号
		order.setPayMethod(form.getPayMethod()); // 支払い方法
		
		// ユーザー検索をしてUserに登録
		UserBean loginUser = (UserBean) session.getAttribute("user");
		int id = loginUser.getId();
		User user = userRepository.findById(id);
		order.setUser(user); // ログインユーザー情報
		
		//DB上のorderへ登録
		orderRepository.save(order);

//エンティティの作成… ②OrderItem
		List<OrderItem> orderItemList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<BasketBean> cartItems = (List<BasketBean>) session.getAttribute("cartItems");

		for (int i = 0; i < cartItems.size(); i++) {
			OrderItem orderItem = new OrderItem();
			BasketBean basketItem = cartItems.get(i); // OrderItemBeanの要素をそれぞれ取得
			
			orderItem.setQuantity(basketItem.getOrderNum()); // 注文個数
			
			// 先ほど登録したorderを検索、orderItemにorder情報として追加
			Order newOrder = orderRepository.findTop1ByOrderByInsertDateDesc();
			orderItem.setOrder(newOrder);//order情報
			
			// 商品IDを取得してItemエンティティに登録⇒OrderItemエンティティに入れる
			Item item = itemRepository.getOne(basketItem.getId());
			orderItem.setItem(item); // Item情報
			orderItem.setPrice(item.getPrice()); // 注文時の単価
			
			// List<OrderItem>に追加
			orderItemList.add(orderItem);
			
			// 在庫数を減らす処理
			item.setStock((item.getStock()) - (orderItem.getQuantity()));
			itemRepository.save(item);
			
			//DB上のorderItemに登録
			orderItemRepository.save(orderItem);
		}

		// 買い物かごの中身に関するセッションをいったんリセットする
		session.removeAttribute("cartItems");
		// 注文完了画面に遷移
		return "redirect:/order/complete";
	}

	@RequestMapping(path = "/order/complete", method = RequestMethod.GET)
	public String completeOrderRedirect() {
		return "order/regist/order_complete";
	}

}
