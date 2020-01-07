package com.cramstack.manytomany.repositories.implementation;

import com.cramstack.manytomany.controllers.requests.BookRequest;

import com.cramstack.manytomany.entities.BookWithPublisherDto;
import com.cramstack.manytomany.entities.PublisherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

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

        String JOIN_BOOK_AND_PUBLISHER_SQL = "INSERT INTO book_publisher(book_id, publisher_id) VALUES (?,?)";

        try {
            bid = insertBook(bookRequest.getName());

            for (Integer pid : bookRequest.getPublishers()) {
                jdbcTemplate.update(JOIN_BOOK_AND_PUBLISHER_SQL , bid, pid);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return (int) bid;
    }

    private long insertBook(String name) {

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

        BookWithPublisherDto bookWithPublisherDto = new BookWithPublisherDto();

        try {

            String GET_BOOK_BY_ID_SQL = "SELECT book.id AS book_id, book.name AS book_name, publisher.id AS publisher_id , publisher.name AS publisher_name FROM publisher Join book_publisher ON publisher.id = book_publisher.publisher_id Join book ON book.id = book_publisher.book_id where book.id = ?";

            List<Map<String, Object>> bookList = jdbcTemplate.queryForList(GET_BOOK_BY_ID_SQL, id);
            Set<PublisherDto> publishers = new HashSet<>();

            if(!bookList.isEmpty()) {

                int ZERO = 0;

                bookWithPublisherDto.setId((Integer) bookList.get(ZERO).get("book_id"));
                bookWithPublisherDto.setName((String) bookList.get(ZERO).get("book_name"));

                for (Map<String, Object> eachBook : bookList) {

                    PublisherDto publisherDto = new PublisherDto();
                    publisherDto.setId((Integer) eachBook.get("publisher_id"));
                    publisherDto.setName((String) eachBook.get("publisher_name"));

                    publishers.add(publisherDto);
                }
            }
            else {

                // throw ex
            }
            bookWithPublisherDto.setPublisherDto(publishers);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return bookWithPublisherDto;
    }

    public List<BookWithPublisherDto> findAllBooks() {

        List<BookWithPublisherDto> bookWithPublisherDtos = new ArrayList<>();

        try {

            String GET_ALL_BOOKS_SQL = "SELECT book.id AS book_id, book.name AS book_name, publisher.id AS publisher_id , publisher.name AS publisher_name FROM publisher Join book_publisher ON publisher.id = book_publisher.publisher_id Join book ON book.id = book_publisher.book_id";

            List<Map<String, Object>> bookList = jdbcTemplate.queryForList(GET_ALL_BOOKS_SQL);
            Map<Integer, Set<PublisherDto>> mapBookAndPublisher = new HashMap<>();
            Map<Integer, String> bookName = new HashMap<>();

            for (Map<String, Object> bookWithPublisher : bookList) {

                Integer bookId = (Integer) bookWithPublisher.get("book_id");

                PublisherDto publisherDto = new PublisherDto();
                publisherDto.setId((Integer) bookWithPublisher.get("publisher_id"));
                publisherDto.setName((String) bookWithPublisher.get("publisher_name"));

                if (!mapBookAndPublisher.containsKey(bookId)) {

                    mapBookAndPublisher.put(bookId, new HashSet<>());
                    bookName.put(bookId, (String) bookWithPublisher.get("book_name"));
                }
                mapBookAndPublisher.get(bookId).add(publisherDto);
            }

            for (Map.Entry<Integer, Set<PublisherDto>> eachBook : mapBookAndPublisher.entrySet()) {

                BookWithPublisherDto bookWithPublisherDto = new BookWithPublisherDto();

                bookWithPublisherDto.setId(eachBook.getKey());
                bookWithPublisherDto.setName(bookName.get(eachBook.getKey()));
                bookWithPublisherDto.setPublisherDto(eachBook.getValue());

                bookWithPublisherDtos.add(bookWithPublisherDto);
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return bookWithPublisherDtos;
    }
}
