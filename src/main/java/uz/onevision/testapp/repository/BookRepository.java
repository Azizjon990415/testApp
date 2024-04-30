package uz.onevision.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.onevision.testapp.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
}
