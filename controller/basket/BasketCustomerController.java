package jp.co.sss.shop.controller.basket;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.shop.bean.BasketBean;

import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
/**
 * かご機能
 * @author asuwa
 *
 */
@Controller
public class BasketCustomerController {
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	MessageSource msg;
	/**
	 * 商品かごに追加機能　
	 * @param itemId
	 * @param session
	 * @param model
	 * @param attributes
	 * @return　リダイレクトかご一覧画面
	 */
	@RequestMapping(path ="/basket/add", method=RequestMethod.POST)
	public String addItem(@RequestParam("id") Integer itemId ,HttpSession session,Model model ,RedirectAttributes attributes) {

		//同じアイテムがカートにあるときtrue
		Boolean addFlag = false;
		//注文数量
		Integer basketOderNum =1;
		//注文数合計
		Integer basketOderNumSum= basketOderNum;
		//cartIistsのすでにアイテムがあるインデックスを保持するため
		int listCount = 0;
		
		//かご情報取得
		@SuppressWarnings("unchecked")
		List<BasketBean> cartItems = (List<BasketBean>) session.getAttribute("cartItems");
		//かご情報取得が未定義の時作成
		if(cartItems==null) {
			cartItems = new ArrayList<>();
		}
		//カートに追加予定の商品情報をdbから情報取得
		Item item =	itemRepository.getOne(itemId);
		//entityをbeanに変換
		BasketBean basketBean = new BasketBean(item.getId(), item.getName(), item.getStock(),basketOderNum);
		
		//追加する商品がすでにカートにあるかチェック
		for(BasketBean basket: cartItems) {
			//すでにカートに同じ商品が入っているとき、数量を増加するため準備
			if(basket.getId().equals(basketBean.getId())) {
				Integer basketOderNumBefore = basket.getOrderNum();
				Integer basketOderNumNow = basketBean.getOrderNum();
				basketOderNumSum = basketOderNumNow+basketOderNumBefore;
				basketBean = basket;
				addFlag=true;
				break;
			}
			listCount++;
		}
		
		//追加する商品数が在庫数以上の時trueエラーメッセージ
		if (basketOderNumSum>item.getStock()) {
			List<String>	errorMessagesCart = new ArrayList<>();
			String errorMessage = null;
			
			if (item.getStock()==0) {

				errorMessage = msg.getMessage("basketList.itemStockNone",new String[] {basketBean.getName()}, null);
				errorMessagesCart.add(errorMessage);
			}
			
			errorMessage = msg.getMessage("basketList.itemStockNotEnough",new String[] {basketBean.getName()}, null);
			errorMessagesCart.add(errorMessage);
			attributes.addFlashAttribute("errorMessagesCartAdd",errorMessagesCart);
			
		}else {
			if(addFlag) {
				//商品数量変更
				cartItems.remove(listCount);
				basketBean.setOrderNum(basketOderNumSum);
				cartItems.add(0,basketBean);
			}else {
				//商品追加	
				cartItems.add(0,basketBean);			
				session.setAttribute("cartItems",cartItems);
			}
		}
		return	"redirect:/basket/list";
	}
	/**
	 * かご内商品削除機能
	 * @param itemId
	 * @param session
	 * @return　リダイレクトかご一覧画面
	 */
	@RequestMapping(path ="/basket/delete",method=RequestMethod.POST)
	public String deleteItem (@RequestParam("id") Integer itemId ,HttpSession session) {
		@SuppressWarnings("unchecked")
		List<BasketBean> cartItems =	(List<BasketBean>) session.getAttribute("cartItems");
		
		int listCount = 0;
		for(BasketBean basket: cartItems) {
			//すでにカートに同じ商品が入っているとき、数量を追加するため
			if(basket.getId().equals(itemId)) {
				Integer	basketOderNumAfter =basket.getOrderNum()-1;
				//0個の時listから情報削除
				//1以上の時数量セット
				if(basketOderNumAfter<=0) {
					cartItems.remove(listCount);
				}else {
					basket.setOrderNum(basketOderNumAfter);					
				}
				break;
			}
			listCount++;
		}
		return	"redirect:/basket/list";
	}
	/**
	 * 	かご内商品すべて削除機能
	 * @param session
	 * @return リダイレクトかご一覧画面
	 */
	@RequestMapping(path ="/basket/allDelete",method=RequestMethod.POST)
	public String deleteAll (HttpSession session) {
		session.removeAttribute("cartItems");
		return	"redirect:/basket/list";
	}
	/**
	 * かご内一覧表示機能
	 * @param model
	 * @param session
	 * @return　かご一覧画面
	 */
	@RequestMapping(path ="/basket/list")
	public String basketList (Model model ,HttpSession session) {
		
		@SuppressWarnings("unchecked")
		List<BasketBean> cartItems =(List<BasketBean>) session.getAttribute("cartItems");
		List<String> errorMessages = null;
		
		//商品情報の在庫数を更新する
		if (cartItems != null) {
			
			for(BasketBean basketBean :cartItems) {
				
				int itemId = basketBean.getId();
				Item item = itemRepository.getOne(itemId);
				int basketItemStock = basketBean.getStock();
				int dbItemStock = item.getStock();
				int basketItemNum = basketBean.getOrderNum();
				
				//かごの商品数が商品在庫数を超えてしまった時、メッセージをリストに追加
				if(basketItemNum > dbItemStock ) {
					if(errorMessages==null) {
						errorMessages = new ArrayList<>();							
					}
					String	errorMessage = msg.getMessage("basketList.itemCountOverStock",new String[] {basketBean.getName()}, null);
					errorMessages.add(errorMessage);
					model.addAttribute("errorMessagesShowCart", errorMessages);
					//商品在庫数がセッションよりdbが大きいときの時、在庫数を上書きする
					if(basketItemStock != dbItemStock) {
						basketBean.setStock(dbItemStock);	
					}
				}
			}
		}
		return	"basket/shopping_basket";
	}
}
