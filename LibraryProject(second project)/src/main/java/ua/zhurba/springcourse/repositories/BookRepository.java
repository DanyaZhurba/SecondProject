package ua.zhurba.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zhurba.springcourse.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByBookNameStartingWith(String name);

}