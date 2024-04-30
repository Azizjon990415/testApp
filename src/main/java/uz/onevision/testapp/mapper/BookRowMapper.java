package uz.onevision.testapp.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.onevision.testapp.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();

        book.setId(rs.getInt("ID"));
        book.setTitle(rs.getString("TITLE"));
        book.setAuthor(rs.getString("AUTHOR"));
        book.setDescription(rs.getString("DESCRIPTION"));

        return book;
    }
}