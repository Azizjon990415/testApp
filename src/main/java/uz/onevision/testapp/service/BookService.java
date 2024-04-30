package uz.onevision.testapp.service;


import uz.onevision.testapp.model.BookDTO;

import java.util.List;
import java.util.Map;


public interface BookService {
    List<BookDTO> getBooks();
    BookDTO addBook(BookDTO bookDTO);
    List<BookDTO> getBooksGroupByAuthor();
    List<Map<String,Integer>> countSearchTextByTitleGroupByAuthor(String searchText);
}
