package com.cramstack.manytomany.entities;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherMapper implements RowMapper<PublisherDto> {


    @Override
    public PublisherDto mapRow(ResultSet resultSet, int i) throws SQLException {

        PublisherDto publisherDto = new PublisherDto();

        publisherDto.setId(resultSet.getInt("id"));
        publisherDto.setName(resultSet.getString("name"));

        return publisherDto;
    }
}
