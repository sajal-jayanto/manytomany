package com.cramstack.manytomany.services.implementation;

import com.cramstack.manytomany.controllers.requests.BookRequest;
import com.cramstack.manytomany.entities.Book;
import com.cramstack.manytomany.repositories.BookRepository;
import com.cramstack.manytomany.repositories.PublisherRepository;
import com.cramstack.manytomany.services.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository , PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Book create(BookRequest bookRequest) {

        Book book = new Book();
        book.setName(bookRequest.getName());
        Set<Integer> publishers = bookRequest.getPublishers();
//        Set<Publisher> savePublishers = new HashSet<>();
//        for(Integer publisher : publishers){
//            Optional<Publisher> isThere = publisherRepository.findById(publisher);
//            if(isThere.isPresent()){
//                savePublishers.add(isThere.get());
//            }
//            else {
//
//            }
//        }
//        book.setPublishers(savePublishers);
        book.setPublishers(publisherRepository.findAllByIdIn(publishers));
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
