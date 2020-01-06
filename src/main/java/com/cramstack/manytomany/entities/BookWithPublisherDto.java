package com.cramstack.manytomany.entities;


import java.util.Set;

public class BookWithPublisherDto {

    private Integer id;
    private String name;
    private Set<PublisherDto> publisherDto;

    public BookWithPublisherDto() {
    }

    public BookWithPublisherDto(Integer id, String name, Set<PublisherDto> publisherDto) {
        this.id = id;
        this.name = name;
        this.publisherDto = publisherDto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PublisherDto> getPublisherDto() {
        return publisherDto;
    }

    public void setPublisherDto(Set<PublisherDto> publisherDto) {
        this.publisherDto = publisherDto;
    }
}
