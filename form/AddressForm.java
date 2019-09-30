package jp.co.sss.shop.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



/**
 * 送付先入力フォーム
 *
 * @author SystemShared
 */

public class AddressForm {

	/**
	 * 注文ID
	 */
	private Integer	id;

	/**
	 * 郵便番号
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

	@Override
	public String toString() {
		return "AddressForm [id=" + id + ", postalCode=" + postalCode + ", address=" + address + ", name=" + name
				+ ", phoneNumber=" + phoneNumber + "]";
	}

}
