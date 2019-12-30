package com.cramstack.manytomany.services.interfaces;

import com.cramstack.manytomany.controllers.requests.BookRequest;
import com.cramstack.manytomany.entities.Book;

import java.util.List;

public interface BookService {

    Book create(BookRequest bookRequest);

    List<Book> getAll();
}
