package com.cramstack.manytomany.repositories;

import com.cramstack.manytomany.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query(value = "SELECT * FROM book",
           nativeQuery = true)
    List<Book> findAllUser();
}
