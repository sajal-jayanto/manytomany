package com.cramstack.manytomany.controllers;

import com.cramstack.manytomany.controllers.requests.PublisherRequest;
import com.cramstack.manytomany.entities.Publisher;
import com.cramstack.manytomany.services.interfaces.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PublisherController {

    private PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/publisher/create")
    private Publisher create(@RequestBody PublisherRequest publisherRequest){
        return publisherService.create(publisherRequest);
    }
}
