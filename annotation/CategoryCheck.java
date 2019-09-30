package jp.co.sss.shop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.sss.shop.validator.CategoryValidator;

/**
 * カテゴリ名重複チェックの独自アノテーション定義
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
 * 入力チェック対象：CategoryValidator
 */
@Constraint(validatedBy = { CategoryValidator.class })
public @interface CategoryCheck {
	String message() default "{categoryListAdmin.duplicate.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String fieldName() default "name";

	String fieldId() default "id";

}
