package jp.co.sss.shop.bean;

/**
 * 商品情報クラス
 *
 * @author SystemShared
 */
public class ItemBean {

	/**
	 * 商品ID
	 */
	private Integer	id;

	/**
	 * 商品名
	 */
	private String	name;

	/**
	 * 価格
	 */
	private Integer	price;

	/**
	 * 商品説明
	 */
	private String	description;

	/**
	 * 在庫数
	 */
	private Integer	stock;

	/**
	 * 商品画像ファイル名
	 */
	private String	image;

	/**
	 * カテゴリID
	 */
	private Integer	categoryId;

	/**
	 * カテゴリ名
	 */
	private String	categoryName;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
