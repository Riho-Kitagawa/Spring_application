package jp.co.sss.shop.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;
import jp.co.sss.shop.util.Constant;

/**
 * 会員管理 表示機能(運用管理者、システム管理者)のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class UserShowAdminController {
	/**
	 * 会員情報
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * 会員一覧表示処理
	 *
	 * @param model    Viewとの値受渡し
	 * @param form     会員情報フォーム
	 * @param pageable ページング情報
	 * @return "user/list/user_list" 会員情報一覧表示画面へ
	 */
	@RequestMapping(path = "/user/list", method = RequestMethod.GET)
	public String showUserList(Model model, @ModelAttribute UserForm form, Pageable pageable) {

		// 会員情報リストを取得
		Page<User> userList = userRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED, pageable);

		// 会員情報をViewに渡す
		model.addAttribute("pages", userList);
		model.addAttribute("users", userList.getContent());
		model.addAttribute("url", "/user/list");

		return "user/list/user_list";
	}

	/**
	 * 会員詳細表示処理
	 *
	 * @param model   Viewとの値受渡し
	 * @param form    会員情報フォーム
	 * @param session セッション情報
	 * @return "/user/detail/user_detail_admin" 会員詳細表示画面へ
	 */
	@RequestMapping(path = "/user/detail/admin/{id}")
	public String showUser(@PathVariable int id, Model model, @ModelAttribute UserForm form, HttpSession session) {
		// 表示対象の会員情報を取得
		User user = userRepository.findById(form.getId()).orElse(null);

		UserBean userBean = new UserBean();

		// Userエンティティの各フィールドの値をUserBeanにコピー
		BeanUtils.copyProperties(user, userBean);

		// 会員情報をViewに渡す
		model.addAttribute("user", userBean);

		return "/user/detail/user_detail_admin";
	}
}
