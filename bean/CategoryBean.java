package jp.co.sss.shop.bean;

/**
 * カテゴリ情報クラス
 *
 * @author SystemShared
 */
public class CategoryBean {

	/**
	 * カテゴリID
	 */
	private Integer	id;
	/**
	 * カテゴリ名
	 */
	private String	name;
	/**
	 * カテゴリ説明
	 */
	private String	description;
	/**
	 * 削除フラグ 0:未削除、1:削除済み
	 */
	private Integer	deleteFlag;
	/**
	 * 登録日付
	 */
	private String	insertDate;

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

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
}
