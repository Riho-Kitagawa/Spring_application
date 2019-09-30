package jp.co.sss.shop.controller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.Constant;
import jp.co.sss.shop.util.BeanCopy;

/**
 * 商品管理 削除機能のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class ItemDeleteAdminController {

	/**
	 * 商品情報
	 */
	@Autowired
	ItemRepository itemRepository;

	/**
	 * 商品情報削除確認処理
	 *
	 * @param id 商品ID
	 * @param model Viewとの値受渡し
	 * @return "item/delete/item_delete_check" 商品情報 削除確認画面へ
	 */
	@RequestMapping(path = "/item/delete/check", method = RequestMethod.POST)
	public String deleteCheck(Integer id, Model model) {
	
		// 削除対象の商品情報を取得
		Item item = itemRepository.findById(id).orElse(null);
		ItemBean itemBean = BeanCopy.copyEntityToBean(item);

		// 商品情報をViewに渡す
		model.addAttribute("item", itemBean);

		return "item/delete/item_delete_check";
	}

	/**
	 * 商品情報削除完了処理
	 *
	 * @param id 商品ID
	 * @return "redirect:/item/delete/complete" 商品情報 削除完了画面へ
	 */
	@RequestMapping(path = "/item/delete/complete", method = RequestMethod.POST)
	public String deleteComplete(Integer id) {

		// 商品情報を取得
		Item item = itemRepository.findById(id).orElse(null);

		// 削除フラグを立てる
		item.setDeleteFlag(Constant.DELETED);

		// 商品情報を保存
		itemRepository.save(item);

		return "redirect:/item/delete/complete";
	}
	
	/**
	 * 商品情報削除完了画面表示
	 * 
	 * @return "item/delete/item_delete_complete"  商品情報 削除完了画面へ
	 */
	@RequestMapping(path = "/item/delete/complete", method = RequestMethod.GET)
	public String deleteCompleteRedirect() {

		return "item/delete/item_delete_complete";
	}

	
}
