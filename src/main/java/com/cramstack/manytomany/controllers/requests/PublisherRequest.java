package com.cramstack.manytomany.controllers.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PublisherRequest {

    @NotNull
    @Size(min = 2 , max = 20)
    private String name;

    public PublisherRequest() {
    }

    public PublisherRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
