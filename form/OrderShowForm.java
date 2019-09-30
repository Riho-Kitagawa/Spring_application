package jp.co.sss.shop.form;

/**
 * 注文情報表示フォーム
 *
 * @author SystemShared
 */
public class OrderShowForm {

	/**
	 * 注文ID
	 */
	private Integer	id;

	/**
	 * 注文情報ページ番号
	 */
	private Integer	index	= 1;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
