package jp.co.sss.shop.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

/**
 * 商品情報のフォーム
 *
 * @author SystemShared
 */
public class ItemForm {

	/**
	 * 商品ID
	 */
	private String			id;

	/**
	 * 商品名
	 */
	@NotBlank
	@Size(min = 1, max = 100)
	private String			name;

	/**
	 * 価格
	 */
	@NotBlank
	@Pattern(regexp = "^[0-9]{1,7}$")
	private String			price;

	/**
	 * 在庫数
	 */
	@NotBlank
	@Pattern(regexp = "^[0-9]{0,4}$")
	private String			stock;

	/**
	 * 商品説明文
	 */
	@Size(min = 0, max = 400)
	private String			description;

	
	/**
	 * 商品画像ファイル
	 */
	private MultipartFile	imageFile;

	/**
	 * 商品画像ファイル名
	 */
	private String			image;

	/**
	 * カテゴリID
	 */
	private String			categoryId;

	/**
	 * 商品カテゴリ名
	 */
	private String			categoryName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
