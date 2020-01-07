package com.cramstack.manytomany.controllers;

import com.cramstack.manytomany.controllers.requests.PublisherRequest;
import com.cramstack.manytomany.repositories.implementation.JdbcPublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PublisherController {

    private JdbcPublisherRepository jdbcPublisherRepository;

    @Autowired
    public PublisherController(JdbcPublisherRepository jdbcPublisherRepository) {
        this.jdbcPublisherRepository = jdbcPublisherRepository;
    }

    @PostMapping("/publisher/create")
    private int create(@RequestBody PublisherRequest publisherRequest){
        return jdbcPublisherRepository.createPublisher(publisherRequest);
    }
}
