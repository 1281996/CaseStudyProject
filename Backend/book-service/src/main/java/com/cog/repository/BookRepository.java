package com.cog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cog.entity.Book;
import com.cog.enums.Event;

public interface BookRepository extends JpaRepository<Book, Integer> {

	

	List<Book> findByStatus(Event unblock);

}
