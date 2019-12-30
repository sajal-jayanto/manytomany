package com.cramstack.manytomany.services.implementation;

import com.cramstack.manytomany.controllers.requests.PublisherRequest;
import com.cramstack.manytomany.entities.Publisher;
import com.cramstack.manytomany.repositories.PublisherRepository;
import com.cramstack.manytomany.services.interfaces.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PublisherServiceImpl implements PublisherService {

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher create(PublisherRequest publisherRequest) {

        Publisher publisher = new Publisher();
        publisher.setName(publisherRequest.getName());
        return publisherRepository.save(publisher);
    }
}
