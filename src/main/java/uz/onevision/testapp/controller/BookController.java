package uz.onevision.testapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.onevision.testapp.model.BookDTO;
import uz.onevision.testapp.service.BookService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("group/author")
    public ResponseEntity<List<BookDTO>> getBooksGroupedByAuthor() {
        return ResponseEntity.ok(bookService.getBooksGroupByAuthor());
    }

    @GetMapping("search/count-text")
    public ResponseEntity<List<Map<String, Integer>>> countSearchTextByTitleGroupByAuthor(@RequestParam(required = true) String searchText) {
        if (searchText.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(bookService.countSearchTextByTitleGroupByAuthor(searchText));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

}
