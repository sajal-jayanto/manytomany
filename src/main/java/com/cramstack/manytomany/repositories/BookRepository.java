package com.cramstack.manytomany.repositories;

import com.cramstack.manytomany.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {


}
