package jp.co.sss.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.util.JPQLConstant;

/**
 * ordersテーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository

public interface OrderRepository extends JpaRepository<Order, Integer> {

	Order findTop1ByOrderByInsertDateDesc();

	// 会員IDに該当する注文情報を注文日付順で検索
	Page<Order> findByUserIdOrderByInsertDateDesc(int userId, Pageable pageable);

	// 注文日付順で注文情報すべてを検索
	@Query(JPQLConstant.FIND_ALL_ORDERS_ORDER_BY_INSERT_DATE)
	Page<Order> findAllOrderByInsertDateDesc(Pageable pageable);
}
