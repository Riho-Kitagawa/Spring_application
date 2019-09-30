package jp.co.sss.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.Item;

/**
 * itemsテーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {


	// 商品情報を新着順で検索
	public Page<Item> findByDeleteFlagOrderByInsertDateDesc(int deleteFlag, Pageable pageable);
	//カテゴリー検索 予定地 
	public Page<Item> findByCategoryOrderByInsertDateDesc(Category category, Pageable pageable);
	//価格帯検索　予定地
	public Page<Item>findByPriceBetweenOrderByInsertDateDesc(int min ,int max, Pageable pageable);
	
	
	//売れ筋順検索(全件)
	String sqlSelect = "SELECT new Item(i.id, i.name, i.price, i.description, i.image, c.name) "
			+ "FROM Item i LEFT OUTER JOIN i.orderItemList o LEFT OUTER JOIN i.category c ";
	String sqlGroupBy = "GROUP BY i.id, i.name, i.price, i.description, i.image, c.name "
			+ "ORDER BY SUM(o.quantity) DESC NULLS LAST, i.id ASC ";
			
	@Query(sqlSelect+sqlGroupBy)
	public Page<Item> findByDeleteFlagOrderByQuentity(int deleteFlag, Pageable pageable);

	//売れ筋順検索(カテゴリ)
	@Query(sqlSelect+"WHERE c.id = :categoryId "+sqlGroupBy)
	public Page<Item> findByCategoryOrderByQuentity(int categoryId, Pageable pageable);

	//売れ筋順検索(価格)
	@Query(sqlSelect+"WHERE i.price BETWEEN :min AND :max "+sqlGroupBy)
	public Page<Item>findByPriceBetweenOrderByQuentity(int min ,int max, Pageable pageable);
	
}
