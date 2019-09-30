package jp.co.sss.shop.util;

import java.util.List;

import jp.co.sss.shop.bean.OrderItemBean;

/**
 * 料金計算用クラス
 *
 * @author System Shared
 */
public class PriceCalc {
	/**
	 * 注文した商品の合計金額を計算
	 *
	 * @param list
	 *            注文した商品情報
	 * @return 合計金額
	 */
	public static int orderItemBeanPriceTotal(List<OrderItemBean> list) {
		int total = 0;

		for (OrderItemBean bean : list) {
			total = total + bean.getSubtotal();
		}

		return total;
	}

	/**
	 * 注文した商品の合計金額を計算
	 *
	 * @param list
	 *            注文した商品情報
	 * @return 合計金額
	 */
	public static int orderItemPriceTotal(List<OrderItemBean> list) {
		int total = 0;

		for (OrderItemBean orderItemBean : list) {
			total = total + (orderItemBean.getPrice() * orderItemBean.getOrderNum());
		}

		return total;
	}

}
