package jp.co.sss.shop.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import jp.co.sss.shop.bean.CategoryBean;
import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.form.ItemForm;

/**
 * オブジェクト間でのフィールドコピー処理を行うクラス
 *
 * @author System Shared
 */
public class BeanCopy {
	/**
	 * ItemFormクラスの各フィールドの値をItemBeanクラスにコピー
	 *
	 * @param form
	 *            コピー元のオブジェクト
	 * @return コピー先のオブジェクト
	 */
	public static ItemBean copyFormToBean(ItemForm form) {

		ItemBean bean = new ItemBean();

		// 商品フォームに入力された情報を商品情報にコピー
		BeanUtils.copyProperties(form, bean);


		bean.setId(Integer.parseInt(form.getId()));
		
		
		bean.setPrice(Integer.parseInt(form.getPrice()));
		
		
		bean.setCategoryId(Integer.parseInt(form.getCategoryId()));
		
		bean.setStock(Integer.parseInt(form.getStock()));

		return bean;
	}

	/**
	 * ItemFormクラスの各フィールドの値をItemエンティティにコピー
	 *
	 * @param form
	 *            コピー元のオブジェクト
	 * @return コピー先のエンティティ
	 */
	public static Item copyFormToEntity(ItemForm form) {
		Category category = new Category();
		Item entity = new Item();

		BeanUtils.copyProperties(form, entity);

		if (form.getId() != null && form.getId().length() > 0) {
			entity.setId(Integer.parseInt(form.getId()));
		}

		if (form.getPrice() != null && form.getPrice().length() > 0) {
			entity.setPrice(Integer.parseInt(form.getPrice()));
		}

		if (form.getStock() != null && form.getStock().length() > 0) {
			entity.setStock(Integer.parseInt(form.getStock()));
		}

		category.setId(Integer.parseInt(form.getCategoryId()));
		entity.setCategory(category);

		return entity;
	}

	/**
	 * Itemエンティティの各フィールドの値をItemBeanクラスにコピー
	 *
	 * @param entity
	 *            コピー元のエンティティ
	 * @return コピー先のオブジェクト
	 */
	public static ItemBean copyEntityToBean(Item entity) {
		ItemBean bean = new ItemBean();

		BeanUtils.copyProperties(entity, bean);

		bean.setCategoryId(entity.getCategory().getId());
		bean.setCategoryName(entity.getCategory().getName());

		return bean;
	}

	/**
	 * Itemエンティティの各フィールドの値をItemBeanクラスにコピー(リスト形式)
	 *
	 * @param entityList
	 *            コピー元のエンティティ(リスト形式)
	 * @return コピー先のオブジェクト(リスト形式)
	 */
	public static List<ItemBean> copyEntityToItemBean(List<Item> entityList) {
		List<ItemBean> beanList = new ArrayList<ItemBean>();

		for (int i = 0; i < entityList.size(); i++) {
			ItemBean bean = new ItemBean();
			BeanUtils.copyProperties(entityList.get(i), bean);

			if (entityList.get(i).getCategory() != null) {
				bean.setCategoryName(entityList.get(i).getCategory().getName());
			}

			beanList.add(bean);
		}

		return beanList;
	}

	/**
	 * Categoryエンティティの各フィールドの値をCategoryBeanクラスにコピー(リスト形式)
	 *
	 * @param entityList
	 *            コピー元のエンティティ(リスト形式)
	 * @return コピー先のオブジェクト(リスト形式)
	 */
	public static List<CategoryBean> copyEntityToCategoryBean(List<Category> entityList) {
		List<CategoryBean> beanList = new ArrayList<CategoryBean>();

		for (int i = 0; i < entityList.size(); i++) {
			CategoryBean bean = new CategoryBean();
			BeanUtils.copyProperties(entityList.get(i), bean);
			beanList.add(bean);
		}

		return beanList;
	}
}
