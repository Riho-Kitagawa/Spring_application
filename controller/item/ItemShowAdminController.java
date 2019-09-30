package jp.co.sss.shop.controller.item;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

/**
 * 商品管理 一覧表示機能(運用管理者用)のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class ItemShowAdminController {
	/**
	 * 商品情報
	 */
	@Autowired
	ItemRepository itemRepository;

	/**
	 * 商品情報一覧表示処理
	 *
	 * @param model
	 *            Viewとの値受渡し
	 * @param pageable
	 *            ページング情報
	 * @return "category/list/category_list" カテゴリ情報 一覧画面へ
	 */
	@RequestMapping(path = "/item/list/admin")
	public String showItem(Model model, Pageable pageable) {
		// 商品情報を全件検索(新着順)
		Page<Item> itemList = itemRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED, pageable);

		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList.getContent());

		// 商品情報をViewへ渡す
		model.addAttribute("pages", itemList);
		model.addAttribute("items", itemBeanList);
		model.addAttribute("url", "/item/list/admin/");

		return "item/list/item_list_admin";
	}

	/**
	 * 商品情報詳細表示処理
	 *
	 * @param id
	 *            商品ID
	 * @param model
	 *            Viewとの値受渡し
	 * @param session
	 *            セッション情報
	 * @return "/item/detail/item_detail_admin" 商品情報 詳細画面へ
	 */
	@RequestMapping(path = "/item/detail/admin/{id}")
	public String showItem(@PathVariable int id, Model model) {

		// 商品IDに該当する商品情報を取得
		Item item = itemRepository.findById(id).orElse(null);

		ItemBean itemBean = new ItemBean();

		// Itemエンティティの各フィールドの値をItemBeanにコピー
		BeanUtils.copyProperties(item, itemBean);

		// 商品情報にカテゴリ名を設定
		itemBean.setCategoryName(item.getCategory().getName());

		// 商品情報をViewへ渡す
		model.addAttribute("item", itemBean);

		return "item/detail/item_detail_admin";
	}
}
