package jp.co.sss.shop.bean;

/**
 * 注文商品情報クラス
 *
 * @author SystemShared
 */
public class OrderItemBean {
	/**
	 * 注文商品ID
	 */
	private Integer	id;
	/**
	 * 注文商品名
	 */
	private String	name;
	/**
	 * 価格
	 */
	private Integer	price;
	/**
	 * 商品画像ファイル名
	 */
	private String	image;
	/**
	 * 注文個数
	 */
	private Integer	orderNum;
	/**
	 * 小計
	 */
	private Integer	subtotal;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
