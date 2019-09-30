package jp.co.sss.shop.bean;

/**
 * 会員情報クラス
 *
 * @author SystemShared
 */
public class UserBean {
	/**
	 * 会員ID
	 */
	private Integer	id;
	/**
	 * 会員メールアドレス
	 */
	private String	email;
	/**
	 * パスワード
	 */
	private String	password;
	/**
	 * 会員名
	 */
	private String	name;
	/**
	 * 郵便番号
	 */
	private String	postalCode;
	/**
	 * 住所
	 */
	private String	address;
	/**
	 * 電話番号
	 */
	private String	phoneNumber;
	/**
	 * 権限
	 */
	private Integer	authority;

	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
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
