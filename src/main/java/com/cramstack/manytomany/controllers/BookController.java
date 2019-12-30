package com.cramstack.manytomany.controllers;

import com.cramstack.manytomany.controllers.requests.BookRequest;
import com.cramstack.manytomany.entities.Book;
import com.cramstack.manytomany.services.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/show")
    public List<Book> getAll(){
        return bookService.getAll();
    }

    @PostMapping("/Book/create")
    public Book create(@RequestBody BookRequest bookRequest){
       return bookService.create(bookRequest);
    }
}
