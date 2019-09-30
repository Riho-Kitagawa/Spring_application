package jp.co.sss.shop.validator;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.sss.shop.annotation.EmailCheck;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.repository.UserRepository;

/**
 * メールアドレス重複チェックの独自検証クラス
 *
 * @author System Shared
 */
public class EmailValidator implements ConstraintValidator<EmailCheck, Object> {
	private String	email;
	private String	id;

	@Autowired
	UserRepository	userRepository;

	@Autowired
	HttpSession		session;

	@Override
	public void initialize(EmailCheck annotation) {
		this.email = annotation.fieldEmail();
		this.id = annotation.fieldId();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);

		String email = (String) beanWrapper.getPropertyValue(this.email);
		Integer id = (Integer) beanWrapper.getPropertyValue(this.id);
		User user = userRepository.findByEmail(email);

		if (user == null || user.getId() == id) {
			return true;
		}
		else {
			return false;
		}
	}

	
	
	
	
}
