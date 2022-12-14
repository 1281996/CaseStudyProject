package com.cog.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cog.entity.Book;

import com.cog.enums.Event;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findByStatus(Event unblock);

	@Query(value = "SELECT  * FROM book_tran b WHERE category=?1 AND user_id=?2 AND publisher=?3 AND price BETWEEN 0 AND ?4 ", nativeQuery = true)
	List<Book> findByCategoryUserIdPublisherPrice(String category, Integer id, String publisher, BigDecimal price);

	@Query(value = "SELECT distinct publisher FROM book_tran", nativeQuery = true)
	List<String> findDistinctPublishers();

	List<Book> findByUserId(Integer authorId);

}
