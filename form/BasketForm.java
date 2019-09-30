package jp.co.sss.shop.form;

/**
 * 買い物かごのフォーム
 *
 * @author SystemShared
 */
public class BasketForm {

	/**
	 * 操作対象の商品ID
	 */
	private Integer	id;

	/**
	 * 買い物かごに入っているの商品IDのリスト
	 */
	private Integer	itemId[];

	/**
	 * 買い物かごに入っているの商品の数量のリスト
	 */
	private Integer	orderNum[];

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer[] getItemId() {
		return itemId;
	}

	public void setItemId(Integer[] itemId) {
		this.itemId = itemId;
	}

	public Integer[] getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer[] orderNum) {
		this.orderNum = orderNum;
	}

}
