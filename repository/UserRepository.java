package jp.co.sss.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.entity.User;

/**
 * usersテーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository

public interface UserRepository extends JpaRepository<User, Integer> {
	
	// メールアドレスに該当する会員情報を検索（メールアドレスのみ）
	User findByEmail(String email);

	// ユーザーIDに該当する会員情報を検索
	User findById(int userId);
	
	// メールアドレスに該当する会員情報を検索
	User findByEmailAndDeleteFlag(String email, int deleteFlag);

	// 削除フラグに合った会員情報をすべて検索
	Page<User> findByDeleteFlagOrderByInsertDateDesc(int deleteFlag, Pageable pageable);
}
