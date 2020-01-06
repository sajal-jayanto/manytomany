package com.cramstack.manytomany.repositories.implementation;

import com.cramstack.manytomany.controllers.requests.BookRequest;
import com.cramstack.manytomany.entities.BookDto;
import com.cramstack.manytomany.entities.BookMapper;
import com.cramstack.manytomany.entities.BookWithPublisherDto;
import com.cramstack.manytomany.entities.PublisherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.function.BinaryOperator;

@Repository
public class JdbcBookRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(BookRequest bookRequest) {

        //System.out.println("bookRequest = " + bookRequest.getName());
        //System.out.println("bookRequest = " + bookRequest.getPublishers());

        long bid = 0;

        try {
            bid = insertBook(bookRequest.getName());

            for (Integer pid : bookRequest.getPublishers()) {
                jdbcTemplate.update("INSERT INTO book_publisher(book_id, publisher_id) VALUES (?,?)", bid, pid);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return (int) bid;
    }

    public long insertBook(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String INSERT_BOOK_SQL = "INSERT INTO book (name) values(?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_BOOK_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public BookWithPublisherDto findBookById(Integer id) {

        String GET_ALL_BOOK_BY_ID = "SELECT book.id AS book_id, book.name AS book_name, publisher.id AS publisher_id , publisher.name AS publisher_name FROM publisher Join book_publisher ON publisher.id = book_publisher.publisher_id Join book ON book.id = book_publisher.book_id where book.id = ?";
        List<Map<String, Object>> bookList = jdbcTemplate.queryForList(GET_ALL_BOOK_BY_ID , id);

        Set<PublisherDto> publishers = new HashSet<>();

        BookWithPublisherDto bookWithPublisherDto = new BookWithPublisherDto();

        for(Map<String , Object> eachRow : bookList){

            PublisherDto publisherDto = new PublisherDto();
            publisherDto.setId((Integer) eachRow.get("publisher_id"));
            publisherDto.setName((String) eachRow.get("publisher_name"));

            publishers.add(publisherDto);

            bookWithPublisherDto.setId((Integer) eachRow.get("book_id"));
            bookWithPublisherDto.setName((String) eachRow.get("book_name"));
        }

        bookWithPublisherDto.setPublisherDto(publishers);

        return bookWithPublisherDto;

    }

    public List<BookWithPublisherDto> findAllBooks() {

        List<BookWithPublisherDto> bookWithPublisherDtos = new ArrayList<>();

        String GET_ALL_BOOKS = "SELECT book.id AS book_id, book.name AS book_name, publisher.id AS publisher_id , publisher.name AS publisher_name FROM publisher Join book_publisher ON publisher.id = book_publisher.publisher_id Join book ON book.id = book_publisher.book_id";
        List<Map<String, Object>> bookList = jdbcTemplate.queryForList(GET_ALL_BOOKS);

        Map<Integer , Set<PublisherDto>> mapBookAndPublisher = new HashMap<>();
        Map<Integer , String> bookName = new HashMap<>();

        for(Map<String , Object> bookWithPublisher : bookList){

            Integer bookId = (Integer) bookWithPublisher.get("book_id");

            PublisherDto publisherDto = new PublisherDto();
            publisherDto.setId((Integer) bookWithPublisher.get("publisher_id"));
            publisherDto.setName((String) bookWithPublisher.get("publisher_name"));

            if(!mapBookAndPublisher.containsKey(bookId)) {

                Set<PublisherDto> tempSet = new HashSet<>();
                tempSet.add(publisherDto);
                mapBookAndPublisher.put(bookId , tempSet);
                bookName.put(bookId , (String) bookWithPublisher.get("book_name"));
            }
            else {

                Set<PublisherDto> tempSet = new HashSet<>(mapBookAndPublisher.get(bookId));
                tempSet.add(publisherDto);
                mapBookAndPublisher.replace(bookId , tempSet);
            }
        }

        for (Map.Entry<Integer , Set<PublisherDto>> book : mapBookAndPublisher.entrySet()) {

            BookWithPublisherDto bookWithPublisherDto = new BookWithPublisherDto();

            bookWithPublisherDto.setId(book.getKey());
            bookWithPublisherDto.setName(bookName.get(book.getKey()));
            bookWithPublisherDto.setPublisherDto(book.getValue());

            bookWithPublisherDtos.add(bookWithPublisherDto);
        }

        return bookWithPublisherDtos;
    }
}
