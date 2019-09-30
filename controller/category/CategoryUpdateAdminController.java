package jp.co.sss.shop.controller.category;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.shop.bean.CategoryBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.form.CategoryForm;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

/**
 * カテゴリ管理 変更機能のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class CategoryUpdateAdminController {

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
	 * カテゴリ情報変更入力処理
	 *
	 * @param backFlg true 戻るボタンを押した / false 戻るボタン以外で表示された
	 * @param model   Viewとの値受渡し
	 * @param form    カテゴリ情報
	 * @return "category/update/category_update_input" カテゴリ情報 変更入力画面へ
	 */
	@RequestMapping(path = "/category/update/input", method = RequestMethod.POST)
	public String updateInput(boolean backFlg, Model model, @ModelAttribute CategoryForm form) {

		if (!backFlg) {
			// 変更対象のカテゴリ情報取得
			Category category = categoryRepository.getOne(form.getId());

			// カテゴリ情報の生成
			CategoryBean categoryBean = new CategoryBean();
			BeanUtils.copyProperties(category, categoryBean);

			// カテゴリ情報をViewに渡す
			model.addAttribute("categoryForm", categoryBean);
		} else {

			CategoryBean categoryBean = new CategoryBean();

			// カテゴリ情報をフォームにコピー
			BeanUtils.copyProperties(form, categoryBean);

			// カテゴリ情報をViewに渡す
			model.addAttribute("categoryForm", categoryBean);

		}

		return "category/update/category_update_input";
	}

	/**
	 * カテゴリ情報変更入力チェックエラー時
	 *
	 * @return "category/update/category_update_input" カテゴリ情報 変更入力画面へ
	 */
	@RequestMapping(path = "/category/update/input", method = RequestMethod.GET)
	public String updateInputError() {

		return "category/update/category_update_input";
	}

	/**
	 * カテゴリ情報変更確認処理
	 *
	 * @param model              Viewとの値受渡し
	 * @param form               カテゴリ情報
	 * @param result             入力チェック結果
	 * @param redirectAttributes リダイレクト後情報保持
	 * @return 
	 *   入力値エラーあり："redirect:/category/update/input" カテゴリ情報変更入力画面へ 
	 *   入力値エラーなし："category/update/category_update_check" カテゴリ情報 変更確認画面へ
	 */
	@RequestMapping(path = "/category/update/check", method = RequestMethod.POST)
	public String updateCheck(Model model, @Valid @ModelAttribute CategoryForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {

		// 入力値にエラーがあった場合、入力画面に戻る
		if (result.hasErrors()) {

			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryForm", result);

			redirectAttributes.addFlashAttribute("categoryForm", form);

			return "redirect:/category/update/input";

		}
		return "category/update/category_update_check";
	}

	/**
	 * カテゴリ情報変更完了処理
	 *
	 * @param model   Viewとの値受渡し
	 * @param form    カテゴリ情報
	 * @param redirectAttributes リダイレクト後情報保持
	 * @return "redirect:/category/update/complete" カテゴリ情報 変更完了画面へ
	 */
	@RequestMapping(path = "/category/update/complete", method = RequestMethod.POST)
	public String updateComplete(Model model, @ModelAttribute CategoryForm form,
			RedirectAttributes redirectAttributes) {

		// カテゴリ情報を取得
		Category category = categoryRepository.getOne(form.getId());

		Integer deleteFlag = category.getDeleteFlag();
		Date insertDate = category.getInsertDate();

		// 入力されたカテゴリ情報を設定
		BeanUtils.copyProperties(form, category);

		// 入力値以外のカテゴリ情報を設定
		category.setDeleteFlag(deleteFlag);
		category.setInsertDate(insertDate);

		// カテゴリ情報を保存
		categoryRepository.save(category);

		// FlashAttributeにカテゴリ情報を保存
		redirectAttributes.addFlashAttribute("categoryId", form.getId());

		// カテゴリ情報を全件検索
		List<Category> categoryList = categoryRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

		// エンティティ内の検索結果をJavaBeansにコピー
		List<CategoryBean> categoryBeanList = BeanCopy.copyEntityToCategoryBean(categoryList);

		// セッションスコープ中に保存されたカテゴリ一覧の情報を更新
		session.setAttribute("categories", categoryBeanList);

		return "redirect:/category/update/complete";
	}

	/**
	 * カテゴリ情報変更完了処理
	 * 
	 * @return "category/update/category_update_complete"
	 */
	@RequestMapping(path = "/category/update/complete", method = RequestMethod.GET)
	public String updateCompleteRedirect() {
		return "category/update/category_update_complete";
	}

}
