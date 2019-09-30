package jp.co.sss.shop.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 注文情報のエンティティクラス
 *
 * @author SystemShared
 */
@Entity
@Table(name = "orders")
public class Order {
	/**
	 * 注文ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_orders_gen")
	@SequenceGenerator(name = "seq_orders_gen", sequenceName = "seq_orders", allocationSize = 1)
	private Integer			id;

	/**
	 * 送付先郵便番号
	 */
	@Column
	private String			postalCode;

	/**
	 * 送付先住所
	 */
	@Column
	private String			address;

	/**
	 * 送付先宛名
	 */
	@Column
	private String			name;

	/**
	 * 送付先電話番号
	 */
	@Column
	private String			phoneNumber;

	/**
	 * 支払方法
	 */
	@Column
	private Integer			payMethod;


	/**
	 * 注文日付
	 */
	@Column(insertable = false)
	private Date			insertDate;

	/**
	 * 会員情報
	 */
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User			user;

	/**
	 * 注文商品リスト
	 */
	@OneToMany(mappedBy = "order")
	private List<OrderItem>	orderItemsList;

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

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItemsList() {
		return orderItemsList;
	}

	public void setOrderItemsList(List<OrderItem> orderItemsList) {
		this.orderItemsList = orderItemsList;
	}

}
