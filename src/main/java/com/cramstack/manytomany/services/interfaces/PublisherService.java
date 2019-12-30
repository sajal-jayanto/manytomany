package com.cramstack.manytomany.services.interfaces;

import com.cramstack.manytomany.controllers.requests.PublisherRequest;
import com.cramstack.manytomany.entities.Publisher;


public interface PublisherService  {
    Publisher create(PublisherRequest publisherRequest);
}

