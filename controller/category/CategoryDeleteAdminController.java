package jp.co.sss.shop.controller.category;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.CategoryBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.form.CategoryForm;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

/**
 * カテゴリ管理 削除機能のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class CategoryDeleteAdminController {

	/**
	 * カテゴリ情報
	 */
	@Autowired
	CategoryRepository categoryRepository;

	
	/**
	 * セッション
	 */
	@Autowired
	HttpSession session;
	
	/**
	 * カテゴリ情報削除確認処理
	 *
	 * @param model Viewとの値受渡し
	 * @param form カテゴリ情報
	 * @return "category/delete/category_delete_check" カテゴリ情報 削除確認画面へ
	 */
	@RequestMapping(path = "/category/delete/check", method = RequestMethod.POST)
	public String deleteCheck(Model model, @ModelAttribute CategoryForm form) {
		Category category = categoryRepository.findById(form.getId()).orElse(null);

		CategoryBean categoryBean = new CategoryBean();

		// Categoryエンティティの各フィールドの値をCategoryBeanにコピー
		BeanUtils.copyProperties(category, categoryBean);

		// カテゴリ情報をViewに渡す
		model.addAttribute("category", categoryBean);

		return "category/delete/category_delete_check";
	}


	/**
	 * カテゴリ情報削除完了処理
	 *
	 * @param form カテゴリ情報
	 * @return "redirect:/category/delete/complete" カテゴリ情報 削除完了画面へ
	 */
	@RequestMapping(path = "/category/delete/complete", method = RequestMethod.POST)
	public String deletetComplete(@ModelAttribute CategoryForm form) {

		// カテゴリ情報を取得
		Category category = categoryRepository.findById(form.getId()).orElse(null);

		// カテゴリ削除フラグを立てる情報を取得
		category.setDeleteFlag(Constant.DELETED);

		// カテゴリ情報を保存
		categoryRepository.save(category);

		// カテゴリ情報を全件検索
		List<Category> categoryList = 
			categoryRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

		// エンティティ内の検索結果をJavaBeansにコピー
		List<CategoryBean> categoryBeanList = BeanCopy.copyEntityToCategoryBean(categoryList);

		// セッションスコープ中に保存されたカテゴリ一覧の情報を更新
		session.setAttribute("categories", categoryBeanList);

		return "redirect:/category/delete/complete";
	}
	
	/**
	 * カテゴリ情報削除完了画面表示
	 *
	 * @return "category/delete/category_delete_complete" カテゴリ情報 削除完了画面へ
	 */
	@RequestMapping(path = "/category/delete/complete", method = RequestMethod.GET)
	public String deletetCompleteRedirect() {
		
		return "category/delete/category_delete_complete";
	}
	
}
