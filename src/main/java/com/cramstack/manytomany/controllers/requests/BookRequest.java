package com.cramstack.manytomany.controllers.requests;

import com.cramstack.manytomany.entities.Publisher;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class BookRequest {

    @NotNull
    @Size(min = 2 , max = 30)
    private String name;

    @NotEmpty
    private Set<Integer> publishers;


    public BookRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<Integer> publishers) {
        this.publishers = publishers;
    }
}
