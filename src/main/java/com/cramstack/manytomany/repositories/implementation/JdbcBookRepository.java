package com.cramstack.manytomany.repositories.implementation;

import com.cramstack.manytomany.controllers.requests.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

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
}
