package jp.co.sss.shop.validator;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.sss.shop.annotation.CategoryCheck;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.util.Constant;

/**
 * カテゴリの重複登録検証クラス
 *
 * @author System Shared
 */

public class CategoryValidator implements ConstraintValidator<CategoryCheck, Object> {

	/**
	 * カテゴリ名
	 */
	private String		name;

	/**
	 * カテゴリID
	 */
	private String		id;

	/**
	 * カテゴリ情報レポジトリ
	 */
	@Autowired
	CategoryRepository	categoryRepository;

	/**
	 * セッション情報
	 */
	@Autowired
	HttpSession			session;

	/**
	 * 初期化処理
	 *
	 * @param annotation
	 *            カテゴリチェックのアノテーション
	 */
	@Override
	public void initialize(CategoryCheck annotation) {
		this.name = annotation.fieldName();
		this.id = annotation.fieldId();
	}

	/**
	 * 入力値チェック処理
	 *
	 * @param value
	 *            チェック対象
	 * @param context
	 *            バリデーションコンテキスト
	 * @return true:エラーなし false:エラーあり
	 * @see javax.validation.ConstraintValidator
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);

		String name = (String) beanWrapper.getPropertyValue(this.name);
		Integer id = (Integer) beanWrapper.getPropertyValue(this.id);
		Category category_same_name = categoryRepository.findByNameAndDeleteFlag(name, Constant.NOT_DELETED);
		Category category_same_id = categoryRepository.findByIdAndDeleteFlag(id, Constant.NOT_DELETED);

		if (category_same_id != null) {
			// 同じカテゴリIDの情報が存在している場合(変更処理)
			return true;
		}
		else if (category_same_name == null) {
			// 同じカテゴリID、同じカテゴリ名の情報が存在していない場合(新規登録)
			return true;
		}
		else {
			// 同じカテゴリ名が存在していて、同じカテゴリIDの情報が存在していない場合(新規登録時の名前重複)
			return false;
		}
	}
}
