package jp.co.sss.shop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.sss.shop.validator.EmailValidator;

/**
 * Email重複チェックの独自アノテーション定義
 *
 * @author SystemShared
 */

/**
 * アノテーション付与対象
 */
@Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE,
        java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD,
        java.lang.annotation.ElementType.PARAMETER })

/**
 * アノテーション情報の維持範囲
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
 * 入力チェック対象：EmailValidator
 */
@Constraint(validatedBy = { EmailValidator.class })
public @interface EmailCheck {
	String message() default "{userRegist.duplicate.message}";;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String fieldEmail() default "email";

	String fieldId() default "id";

}
