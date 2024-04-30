package uz.onevision.testapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uz.onevision.testapp.entity.Book;

public record BookDTO(Integer id,
                      @NotNull(message = "title field is required") @NotBlank(message = "title field is required") String title,
                      @NotNull(message = "title field is required") @NotBlank(message = "title field is required") String author,
                      String description) {
    public BookDTO(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription());
    }

    public BookDTO(int id, BookDTO bookDTO) {
        this(id, bookDTO.title(), bookDTO.author, bookDTO.description());
    }
}
