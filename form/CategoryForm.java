package jp.co.sss.shop.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import jp.co.sss.shop.annotation.CategoryCheck;

/**
 * カテゴリ情報のフォーム
 *
 * @author SystemShared
 */
@CategoryCheck
public class CategoryForm {

	/**
	 * カテゴリID
	 */
	private Integer	id;

	/**
	 * カテゴリ名
	 */
	@NotBlank(message = "カテゴリ名を入力してください。")
	@Size(min = 1, max = 15)
	private String	name;

	/**
	 * カテゴリ説明
	 */
	@Size(max = 30)
	private String	description	= "";

	/**
	 * 表示ページ
	 */
	private Integer	index		= 1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
