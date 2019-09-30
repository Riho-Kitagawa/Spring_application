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
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

/**
 * 商品管理 一覧表示機能(一般会員用)のコントローラクラス
 *
 * @author SystemShared,Kubota,Riho,Kawahara
 * 
 */
@Controller
public class ItemShowCustomerController {
	/**
	 * 商品情報
	 */
	@Autowired
	ItemRepository itemRepository;
	
	/**
	 * 検索機能における共通処理メソッド(価格帯検索/カテゴリ検索/全件検索)
	 * @param model    Viewとの値受渡し
	 * @param itemList　エンティティ内の検索結果(itemRepositoryから取得した各検索情報)
	 */
		public void ItemShow(Model model,Page<Item> itemList) {
					// エンティティ内の検索結果をJavaBeansにコピー
					List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList.getContent());

					// 商品情報をViewへ渡す
					model.addAttribute("pages", itemList);
					model.addAttribute("items", itemBeanList);
		}

	/*********************************************************************************************************
	 * トップ画面 全件表示処理
	 *
	 *
	 * @param model    Viewとの値受渡し
	 * @param pageable ページング情報
	 * @return "index" トップ画面へ
	 */
	@RequestMapping(path = "/")
	public String index(Model model, Pageable pageable) {
		// 商品情報を全件検索(売れ筋順)
		Page<Item> itemList = itemRepository.findByDeleteFlagOrderByQuentity(Constant.NOT_DELETED, pageable);
		//共通処理
		ItemShow(model, itemList);
		//Viewへ渡す(URL情報)
		model.addAttribute("url", "/");
		// トップ画面に遷移
		return "index";
	}

	/***********************************************************************************************************
	 * 
	 * 商品情報 価格帯検索 表示処理 //窪田
	 *
	 * @param model    Viewとの値受渡し
	 * @param pageable ページング情報
	 * @return "item/list/item_list" カテゴリ情報 一覧画面へ
	 */
	@RequestMapping(path = "/item/list/price/{sortType}")
	public String showItemFindByPrice(@PathVariable int sortType,@RequestParam(name = "min", required = false) int min,
			@RequestParam(name = "max", required = false) int max, Model model, Pageable pageable) {
		//Viewへ渡す(検索方法、価格帯情報)
		model.addAttribute("refine","price");
		model.addAttribute("min", min);
		model.addAttribute("max", max);
		
		if(sortType ==1) {
			// 商品情報を価格検索(新着順)
			Page<Item> itemList = itemRepository.findByPriceBetweenOrderByInsertDateDesc(min, max, pageable);
			//共通処理
			ItemShow(model, itemList);
			//Viewへ渡す(ソート指定、URL情報)
			model.addAttribute("sortType",sortType);
			model.addAttribute("url", "/item/list/price/{sortType}");		
			
		}else if(sortType ==2) {
			// 商品情報を価格検索(売れ筋順)
			Page<Item> itemList = itemRepository.findByPriceBetweenOrderByQuentity(min, max, pageable);
			//共通処理
			ItemShow(model, itemList);
			//Viewへ渡す(ソート指定、URL情報)
			model.addAttribute("sortType",sortType);
			model.addAttribute("url", "/item/list/price/{sortType}");	
		}
		// 商品一覧画面に遷移
		return "item/list/item_list";
	}

	/***********************************************************************************************************
	 * 商品情報 カテゴリ検索 表示処理 //窪田
	 *
	 * @param model    Viewとの値受渡し
	 * @param pageable ページング情報
	 * @return "item/list/item_list" 一覧画面へ
	 */
	@RequestMapping(path = "/item/list/category/{sortType}")
	public String showItemFindByCategory(@PathVariable int sortType,@RequestParam(name="categoryId", required=false) Integer categoryId,
			Model model, Pageable pageable){

		//Viewへ渡す(検索方法の指定)
		model.addAttribute("refine","category");;
		
		// 参照先テーブルに対応付けられたエンティティCategoryのオブジェクトを生成
		Category category = new Category();
		// Categoryのオブジェクト内のidフィールドにパラメータの値を代入
		model.addAttribute("categoryId",categoryId);
		// Categoryのオブジェクト内のidフィールドにパラメータの値を代入
		category.setId(categoryId);
		
		if(sortType ==1) {
			// Categoryのオブジェクト内のidフィールドを使用した条件検索を実行(新着順)
			Page<Item> itemList = itemRepository.findByCategoryOrderByInsertDateDesc(category, pageable);
			//共通処理
			ItemShow(model, itemList);
			//Viewへ渡す(ソート指定、URL情報)
			model.addAttribute("sortType",sortType);
			model.addAttribute("url", "/item/list/category/{sortType}");
			
		}else if(sortType ==2) {
			// Categoryのオブジェクト内のidフィールドを使用した条件検索を実行(売れ筋順)
			Page<Item> itemList = itemRepository.findByCategoryOrderByQuentity(categoryId, pageable);
			//共通処理
			ItemShow(model, itemList);
			//Viewへ渡す(ソート指定、URL情報)
			model.addAttribute("sortType",sortType);
			model.addAttribute("url", "/item/list/category/{sortType}");			
		}
		// 商品一覧画面に遷移
		return "item/list/item_list";
	}


	/***********************************************************************************************************
	 * アイテム詳細 表示処理
	 * 
	 * @param id
	 * @param model
	 * @return "item/detail/item" アイテム詳細
	 */
	@RequestMapping(path = "/item/detail/{id}")
	public String showItemDetail(@PathVariable int id, Model model) {

		// 商品IDに該当する商品情報を取得
		Item item = itemRepository.findById(id).orElse(null);

		// itemBeanの生成
		ItemBean itemBean = new ItemBean();

		// Itemエンティティの各フィールドの値をItemBeanにコピー
		BeanUtils.copyProperties(item, itemBean);

		// 商品情報にカテゴリ名を設定
		itemBean.setCategoryName(item.getCategory().getName());

		// 商品情報をViewへ渡す
		model.addAttribute("item", itemBean);

		// 商品詳細ページへ遷移
		return "item/detail/item_detail";
	}

	/***********************************************************************************************************
	 * 新着一覧 表示処理
	 * 
	 * @param model
	 * @param pageable
	 * @return "item/list/item_list"
	 */
	@RequestMapping(path = "/item/list/{sortType}")
	public String showNewItem(@PathVariable int sortType,Model model, Pageable pageable) {		
		
		//Viewへ渡す(検索方法の指定)
		model.addAttribute("refine","all");
		
		if(sortType ==1) {
			// 商品情報を全件検索(新着順)
			Page<Item> itemList = itemRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED, pageable);			
			//共通処理
			ItemShow(model, itemList);
			//Viewへ渡す(ソート指定、URL情報)
			model.addAttribute("sortType",sortType);
			model.addAttribute("url", "/item/list/{sortType}");
			
		}else if(sortType ==2) {
			// 商品情報を全件検索(売れ筋順)
			Page<Item> itemList = itemRepository.findByDeleteFlagOrderByQuentity(Constant.NOT_DELETED, pageable);
			//共通処理
			ItemShow(model, itemList);
			//Viewへ渡す(ソート指定、URL情報)
			model.addAttribute("sortType",sortType);
			model.addAttribute("url", "/item/list/2");
		}
		// 新着一覧ページへ遷移
		return "item/list/item_list";
	}
}

/***********************************************************************************************************
 * 売れ筋順 表示処理
 * 
 * @param model
 * @param pageable
 * @return "item/list/item_list"
 */
