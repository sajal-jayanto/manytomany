package com.cramstack.manytomany.entities;


public class PublisherDto {


    private Integer id;
    private String name;

    public PublisherDto() {
    }

    public PublisherDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
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

    @Override
    public String toString() {
        return "PublisherDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
