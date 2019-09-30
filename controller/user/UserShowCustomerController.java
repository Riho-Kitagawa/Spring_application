package jp.co.sss.shop.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;

/**
 * 会員管理 表示機能(一般会員)のコントローラクラス
 *
 * @author 
 */
@Controller
public class UserShowCustomerController {
	/**
	 * 会員情報
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * 会員詳細表示処理
	 *
	 * @param model   Viewとの値受渡し
	 * @param form    会員情報フォーム
	 * @param session セッション情報
	 * @return "/user/detail/user_detail" 会員詳細表示画面へ
	 */
	@RequestMapping(path = "/user/detail/{id}")
	public String showUser(@PathVariable int id,Model model, @ModelAttribute UserForm form, HttpSession session) {
		
		Integer loginUserId = ((UserBean) session.getAttribute("user")).getId();
		if(loginUserId == form.getId()) {
			// 表示対象の会員情報を取得
			User user = userRepository.findById(form.getId()).orElse(null);
			
			UserBean userBean = new UserBean();
			// Userエンティティの各フィールドの値をUserBeanにコピー
			BeanUtils.copyProperties(user, userBean);
			// 会員情報をViewに渡す
			model.addAttribute("user", userBean);
			// 会員情報 詳細表示画面へ遷移
			return "user/detail/user_detail";
		}else {
			return "error";
		}
		
	}
}
