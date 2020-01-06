package com.cramstack.manytomany.repositories.implementation;

import com.cramstack.manytomany.controllers.requests.PublisherRequest;
import com.cramstack.manytomany.entities.PublisherDto;
import com.cramstack.manytomany.entities.PublisherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class JdbcPublisherRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPublisherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int createPublisher(PublisherRequest publisherRequest) {

        long publisherId = 0;

        try {
            publisherId = insertPublisher(publisherRequest.getName());

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return (int) publisherId;
    }

    private long insertPublisher(String publisherName) {

        KeyHolder retProperty = new GeneratedKeyHolder();
        String CREATE_PUBLISHER_SQL = "INSERT INTO publisher (name) VALUES(?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(CREATE_PUBLISHER_SQL , Statement.RETURN_GENERATED_KEYS);
            ps.setString(1 , publisherName);
            return ps;
        }, retProperty);

        return retProperty.getKey().longValue();
    }

    public PublisherDto getPublisherById(Integer id){

        String GET_PUBLISHER_BY_ID_SQL = "Select publisher.id , publisher.name FROM publisher where publisher.id = ?";
        return jdbcTemplate.queryForObject(GET_PUBLISHER_BY_ID_SQL , new PublisherMapper(), id);
    }

    public Set<PublisherDto> getPublisherByBookId(Integer id){

        String GET_PUBLISHER_BY_BOOK_ID_SQL = "SELECT publisher.id , publisher.name FROM publisher Join book_publisher ON publisher.id = book_publisher.publisher_id where book_publisher.book_id = ?";
        List<Map<String, Object>> publishers = jdbcTemplate.queryForList(GET_PUBLISHER_BY_BOOK_ID_SQL , id);

        Set<PublisherDto> publisherSet = new HashSet<>();

        for(Map<String , Object> publisher : publishers){

            PublisherDto publisherDto = new PublisherDto();
            publisherDto.setId((Integer) publisher.get("id"));
            publisherDto.setName((String) publisher.get("name"));

            publisherSet.add(publisherDto);
        }

        return publisherSet;
    }

}
