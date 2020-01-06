package com.cramstack.manytomany.entities;

import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;


public class BookMapper implements RowMapper<BookDto> {

    @Override
    public BookDto mapRow(ResultSet resultSet, int i) throws SQLException {

        BookDto bookDto = new BookDto();

        bookDto.setId(resultSet.getInt("id"));
        bookDto.setName(resultSet.getString("name"));

        return bookDto;

    }
}
