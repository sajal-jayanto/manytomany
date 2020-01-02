package com.cramstack.manytomany.controllers;

import com.cramstack.manytomany.controllers.requests.BookRequest;
import com.cramstack.manytomany.entities.Book;
import com.cramstack.manytomany.repositories.implementation.JdbcBookRepository;
import com.cramstack.manytomany.services.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;
    private JdbcBookRepository jdbcBookRepository;

    @Autowired
    public BookController(JdbcBookRepository jdbcBookRepository) {
        this.jdbcBookRepository = jdbcBookRepository;
    }

    @GetMapping("/show")
    public List<Book> getAll() {
        return jdbcBookRepository.findAllData();
    }

    @PostMapping("/book/create")
    public int create(@RequestBody BookRequest bookRequest) {
        return jdbcBookRepository.save(bookRequest);
    }
}
