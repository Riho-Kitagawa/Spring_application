package jp.co.sss.shop.controller.category;

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
 * カテゴリ管理 登録機能のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class CategoryRegistAdminController {

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
	 * カテゴリ情報登録画面の表示処理
	 *
	 * @param Model Viewとの値受渡し
	 * @return "category/regist/category_regist_input" カテゴリ情報 登録画面へ
	 */
	@RequestMapping(path = "/category/regist/input" , method = RequestMethod.GET)
	public String registInput(Model model ) {
		
		if (!model.containsAttribute("categoryForm")) {
			model.addAttribute("categoryForm", new CategoryForm());
		}
		return "category/regist/category_regist_input";
		
	}
	
	/**
	 * POSTメソッドを利用してカテゴリ情報登録画面に戻る処理
	 * 
	 * @param form カテゴリ情報
	 * @return "category/regist/category_regist_input" 
	 */
	@RequestMapping(path = "/category/regist/input" , method = RequestMethod.POST)
	public String registInputBack(CategoryForm form ) {
		
		return "category/regist/category_regist_input";
		
	}
	
	

	/**
	 * カテゴリ情報登録確認処理
	 *
	 * @param form カテゴリ情報
	 * @param result 入力値チェックの結果
 	 * @param redirectAttributes リダイレクト後情報保持
	 * @return 
	 * 	入力値エラーあり："redirect:/category/regist/input" カテゴリ情報登録画面へ
	 * 	入力値エラーなし："category/regist/category_regist_check" カテゴリ情報 登録確認画面へ
	 */
	@RequestMapping(path = "/category/regist/check", method = RequestMethod.POST)
	public String registCheck(@Valid @ModelAttribute CategoryForm form, BindingResult result, RedirectAttributes redirectAttributes) {

		// 入力値にエラーがあった場合、エラー情報を保持したまま入力画面に戻る
		if (result.hasErrors()) {
			
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryForm", result);
			
			redirectAttributes.addFlashAttribute("categoryForm", form);
			
			return "redirect:/category/regist/input";
		}
		return "category/regist/category_regist_check";
	}

	/**
	 * カテゴリ情報登録完了処理
	 *
	 * @param form カテゴリ情報
	 * @return "redirect:/category/regist/complete" カテゴリ情報 登録完了画面
	 */
	@RequestMapping(path = "/category/regist/complete", method = RequestMethod.POST)
	public String registComplete(@ModelAttribute CategoryForm form) {

		// カテゴリ情報を生成
		Category category = new Category();

		// 入力されたカテゴリ情報を取得
		BeanUtils.copyProperties(form, category);

		// 入力されたカテゴリ情報をレポジトリに保存
		categoryRepository.save(category);

		// カテゴリ情報を全件検索
		List<Category> categoryList = 
			categoryRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

		// エンティティ内の検索結果をJavaBeansにコピー
		List<CategoryBean> categoryBeanList = BeanCopy.copyEntityToCategoryBean(categoryList);

		// セッションスコープ中に保存されたカテゴリ一覧の情報を更新
		session.setAttribute("categories", categoryBeanList);

		return "redirect:/category/regist/complete";
	}
	
	
	/**
	 * カテゴリ情報登録完了画面表示
	 *
	 * @return "category/regist/category_regist_complete" カテゴリ情報 登録完了画面
	 */
	@RequestMapping(path = "/category/regist/complete", method = RequestMethod.GET)
	public String registCompleteRedirect() {
	
		return "category/regist/category_regist_complete";
	}
	
	

}
