package com.cog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cog.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
