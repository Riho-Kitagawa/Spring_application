package jp.co.sss.shop.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



/**
 * 注文情報入力フォーム
 *
 * @author SystemShared
 */
public class OrderForm {

	/**
	 * 注文ID
	 */
	private Integer	id;

	/**
	 * 送付先郵便番号
	 */
	@NotBlank
	@Size(min = 7, max = 8)
	@Pattern(regexp = "^[0-9]+$")
	private String	postalCode;

	/**
	 * 送付先住所
	 */
	@NotBlank
	@Size(min = 1, max = 150)
	private String	address;

	/**
	 * 送付先氏名
	 */
	@NotBlank
	@Size(min = 1, max = 30)
	private String	name;

	/**
	 * 送付先電話番号
	 */
	@NotBlank
	@Size(min = 10, max = 11)
	@Pattern(regexp = "^[0-9]+$")
	private String	phoneNumber;

	/**
	 * 支払い方法
	 */
	private Integer	payMethod;

	/**
	 * 会員ID
	 */
	private Integer	userId;

	/**
	 * 注文時点合計金額
	 */
	private Integer price;
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
}
