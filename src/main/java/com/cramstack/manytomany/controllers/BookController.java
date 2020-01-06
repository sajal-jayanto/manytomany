package com.cramstack.manytomany.controllers;

import com.cramstack.manytomany.controllers.requests.BookRequest;
import com.cramstack.manytomany.entities.*;
import com.cramstack.manytomany.repositories.implementation.JdbcBookRepository;
import com.cramstack.manytomany.repositories.implementation.JdbcPublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BookController {

    private JdbcBookRepository jdbcBookRepository;
    private JdbcPublisherRepository jdbcPublisherRepository;

    @Autowired
    public BookController(JdbcBookRepository jdbcBookRepository , JdbcPublisherRepository jdbcPublisherRepository) {
        this.jdbcBookRepository = jdbcBookRepository;
        this.jdbcPublisherRepository = jdbcPublisherRepository;
    }


    @GetMapping("book/show")
    public List<BookWithPublisherDto> getAll() {

        List<BookWithPublisherDto> allBooks = jdbcBookRepository.findAllBooks();
        return allBooks;
    }


    @GetMapping("book/show/{id}")
    public BookWithPublisherDto getById(@PathVariable("id") Integer id) {

        BookWithPublisherDto bookWithPublisherDto = jdbcBookRepository.findBookById(id);
        return bookWithPublisherDto;
    }

    @PostMapping("/book/create")
    public int create(@RequestBody BookRequest bookRequest) {
        return jdbcBookRepository.save(bookRequest);
    }
}
