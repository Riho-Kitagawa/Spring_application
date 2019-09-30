package jp.co.sss.shop.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * カテゴリ情報のエンティティクラス
 *
 * @author SystemShared
 */
@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categories_gen")
	@SequenceGenerator(name = "seq_categories_gen", sequenceName = "seq_categories", allocationSize = 1)
	/**
	 * カテゴリID
	 */
	private Integer		id;

	/**
	 * カテゴリ名
	 */
	@Column
	private String		name;

	/**
	 * カテゴリ説明
	 */
	@Column
	private String		description;

	/**
	 * 削除フラグ 0:未削除、1:削除済み
	 */
	@Column(insertable = false)
	private Integer		deleteFlag;

	/**
	 * 登録日付
	 */
	@Column(insertable = false)
	private Date		insertDate;

	/**
	 * 商品リスト
	 */
	@OneToMany(mappedBy = "category")
	private List<Item>	itemList;

	/**
	 * コンストラクタ
	 */
	public Category() {
	}

	/**
	 * コンストラクタ
	 * @param name カテゴリ名
	 */
	public Category(String name) {
		this.name = name;
	}

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

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
}
