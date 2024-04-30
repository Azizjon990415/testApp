package uz.onevision.testapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.onevision.testapp.entity.Book;
import uz.onevision.testapp.mapper.BookRowMapper;
import uz.onevision.testapp.model.BookDTO;
import uz.onevision.testapp.service.BookService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<BookDTO> getBooks() {
        String query = """
                Select * from book
                order by title desc
                """;
        List<Book> books = jdbcTemplate.query(query, new BookRowMapper());

        return books.stream().map(BookDTO::new).toList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public BookDTO addBook(BookDTO bookDTO) {
        String insertIntoSql = """
                INSERT INTO book(title, author, description) VALUES('%s','%s','%s')
                """.formatted(bookDTO.title(), bookDTO.author(), bookDTO.description());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> connection.prepareStatement(insertIntoSql, new String[]{"id"}), keyHolder);

        return new BookDTO(keyHolder.getKey().intValue(), bookDTO);
    }

    @Override
    public List<BookDTO> getBooksGroupByAuthor() {
        String query = """
                Select * from book 
                group by author,id
                order by author asc 
                """;
        List<Book> books = jdbcTemplate.query(query, new BookRowMapper());

        return books.stream().map(BookDTO::new).toList();
    }

    @Override
    public List<Map<String, Integer>> countSearchTextByTitleGroupByAuthor(String searchText) {
        String query = """
                Select * from book
                where LOWER(title) like '%searchText%' 
                """.replace("searchText", searchText.toLowerCase());
        List<Book> books = jdbcTemplate.query(query, new BookRowMapper());

        return countAndGroup(searchText, books);
    }

    private static List<Map<String, Integer>> countAndGroup(String searchText, List<Book> books) {
        return books.stream().collect(Collectors.groupingBy(Book::getAuthor))
                .entrySet().stream().map(stringListEntry -> Map.of(stringListEntry.getKey(), stringListEntry.getValue().stream()
                        .reduce(0, (integer, book) -> integer + countOfSearchText(searchText, book), Integer::sum))).toList();
    }

    private static int countOfSearchText(String searchText, Book book) {
        return (book.getTitle().length() - book.getTitle().toLowerCase().replaceAll(searchText.toLowerCase(), "").length()) / searchText.length();
    }
}
