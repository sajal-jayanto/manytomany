package com.cramstack.manytomany.services.interfaces;

import com.cramstack.manytomany.controllers.requests.BookRequest;
import com.cramstack.manytomany.entities.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookService {

    Book create(BookRequest bookRequest);

    List<Book> getAll();
}
