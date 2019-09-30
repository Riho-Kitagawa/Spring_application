package jp.co.sss.shop.controller.user;

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

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;

/**
 * 会員管理 登録機能(運用管理者、システム管理者)のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class UserRegistAdminController {

	/**
	 * 会員情報
	 */
	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	/**
	 * 会員情報入力画面表示処理
	 *
	 * @param Model Viewとの値受渡し
	 * @return "user/regist/user_regist_input_admin" 会員情報 登録入力画面へ
	 */
	@RequestMapping(path = "/user/regist/input/admin", method = RequestMethod.GET)
	public String registInput(Model model) {

		if (!model.containsAttribute("userForm")) {
			UserForm userForm = new UserForm();
			UserBean user = (UserBean) session.getAttribute("user");
			if (user.getAuthority() == 0) {
				// システム管理者としてログイン中の場合、入力フォーム「権限」の初期値を0（システム管理者）に指定する。
				userForm.setAuthority(user.getAuthority());
			}
			model.addAttribute("userForm", userForm);
		}
		return "user/regist/user_regist_input_admin";
	}

	/**
	 * POSTメソッドを利用して会員情報入力画面に戻る処理
	 * 
	 * @param form 会員情報
	 * @return "user/regist/user_regist_input_admin" 会員情報 登録入力画面へ
	 */
	@RequestMapping(path = "/user/regist/input/admin", method = RequestMethod.POST)
	public String registInput(UserForm form) {

		return "user/regist/user_regist_input_admin";
	}

	/**
	 * 会員情報 登録確認処理
	 *
	 * @param form   会員情報フォーム
	 * @param result 入力チェック結果
	 * @return 入力値エラーあり："redirect:/user/regist/input/admin" 会員情報登録画面へ
	 *         入力値エラーなし："user/regist/user_regist_check_admin" 会員情報 登録確認画面へ
	 */
	@RequestMapping(path = "/user/regist/check/admin", method = RequestMethod.POST)
	public String registCheck(@Valid @ModelAttribute UserForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {

		// 入力値にエラーがあった場合、会員情報 入力画面表示処理に戻る
		if (result.hasErrors()) {

			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userForm", result);

			redirectAttributes.addFlashAttribute("userForm", form);

			return "redirect:/user/regist/input/admin";
		}

		return "user/regist/user_regist_check_admin";
	}

	/**
	 * 会員情報登録完了処理
	 *
	 * @param form 会員情報
	 * @return "redirect:/user/regist/complete/admin" 会員情報 登録完了画面へ
	 */
	@RequestMapping(path = "/user/regist/complete/admin", method = RequestMethod.POST)
	public String registComplete(@ModelAttribute UserForm form) {
		// 会員情報の生成
		User user = new User();

		// 入力値を会員情報にコピー
		BeanUtils.copyProperties(form, user);

		// 会員情報を保存
		userRepository.save(user);

		return "redirect:/user/regist/complete/admin";
	}

	/**
	 * 会員情報登録完了画面表示
	 *
	 * @param form 会員情報
	 * @return "user/regist/user_regist_complete_admin" 会員情報 登録完了画面へ
	 */
	@RequestMapping(path = "/user/regist/complete/admin", method = RequestMethod.GET)
	public String registCompleteRedirect() {
		return "user/regist/user_regist_complete_admin";
	}

}
