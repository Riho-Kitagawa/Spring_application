package jp.co.sss.shop.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.co.sss.shop.annotation.LoginCheck;



/**
 * ログインのフォーム
 *
 * @author SystemShared
 */
@LoginCheck
public class LoginForm {

	/**
	 * メールアドレス
	 */
	@NotBlank
	@Email
	private String	email;

	/**
	 * パスワード
	 */
	@NotBlank
	@Size(min = 8, max = 16)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String	password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
