package jp.co.sss.shop.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.sss.shop.bean.UserBean;

/**
 * ログインアカウントの認証チェック用クラス
 *
 * @author User
 */
@Aspect
@Component
public class AccountCheckInterceptor {
	@Autowired
	HttpSession			session;

	@Autowired
	HttpServletResponse	response;

	@Autowired
	HttpServletRequest	request;

	/**
	 * 非会員向けアクセス制限
	 *
	 * @param joinPoint
	 *            ジョインポイント
	 * @throws IOException
	 *             リダイレクト失敗時に送出
	 */
	@Before("execution(* jp.co.sss.shop.controller.*.*.*(..)) && "
	        + "(! within(jp.co.sss.shop.controller.login.LoginController)) && "
	        + "(! within(jp.co.sss.shop.controller.user.UserRegistCustomerController)) && "
	        + "(! within(jp.co.sss.shop.controller.item.ItemShowCustomerController)) && "
	        + "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void checkAccount(JoinPoint joinPoint) throws IOException {

		if (session.getAttribute("user") == null) {
			// 不正アクセスの場合、ログイン画面にリダイレクト
			
			response.sendRedirect(request.getContextPath() + "/login");
			
		}
	}

	/**
	 * 一般会員向けアクセス制限
	 *
	 * @param joinPoint
	 *            ジョインポイント
	 * @throws IOException
	 *             リダイレクト失敗時に送出
	 */
	@Before("execution(* jp.co.sss.shop.controller.*.*AdminController.*(..)) &&"
	        + " @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void checkAccountCustomer(JoinPoint joinPoint) throws IOException {
		
		if (session.getAttribute("user") != null) {
			
			UserBean user = (UserBean) session.getAttribute("user");
			
			if (user.getAuthority() == 2) {
				// ログイン画面にリダイレクト
				response.sendRedirect(request.getContextPath() + "/login");
			}
		}
	}

	/**
	 * 運用管理者向けアクセス制限
	 *
	 * @param joinPoint
	 *            ジョインポイント
	 * @throws IOException
	 *             リダイレクト失敗時に送出
	 */
	@Before("(! execution(* jp.co.sss.shop.controller.login.*.*(..))) && "
	        + "(! execution(* jp.co.sss.shop.controller.*.*AdminController.*(..))) && "
	        + "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void checkAccountAdmin(JoinPoint joinPoint) throws IOException {

		
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
		
			if (user.getAuthority() == 1) {
				// ログイン画面にリダイレクト
				response.sendRedirect(request.getContextPath() + "/login");
			}
		}
	}

	/**
	 * システム管理者向けアクセス制限
	 *
	 * @param joinPoint
	 *            ジョインポイント
	 * @throws IOException
	 *             リダイレクト失敗時に送出
	 */
	@Before("(! execution(* jp.co.sss.shop.controller.login.*.*(..))) && "
	        + "(! execution(* jp.co.sss.shop.controller.user.*AdminController.*(..))) && "
	        + "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void checkAccountSystemAdmin(JoinPoint joinPoint) throws IOException {

		
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			
			if (user.getAuthority() == 0) {
				// ログイン画面にリダイレクト
				response.sendRedirect(request.getContextPath() + "/login");
			}
		}
	}
}
