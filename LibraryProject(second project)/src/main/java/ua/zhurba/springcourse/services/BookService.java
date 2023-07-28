package ua.zhurba.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ua.zhurba.springcourse.models.Book;
import ua.zhurba.springcourse.models.Person;
import ua.zhurba.springcourse.repositories.BookRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findOne(int id){
        Optional<Book> foundPerson = bookRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Book person){
        bookRepository.save(person);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    @Transactional
    public void addBook(int id, Person person){
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(person);
                    book.setWhenWasTaken(new Timestamp(System.currentTimeMillis()));
                }
        );
    }

    @Transactional
    public void releaseBook(int id){
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                }
        );
    }

    public Person getPersonWithBookId(int book_id){
        return bookRepository.findById(book_id).get().getOwner();
    }

    public List<Book> booksPerPage(int page, int books_per_page){
        return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
    }

    public List<Book> booksSortedByYear(){
        return bookRepository.findAll(Sort.by("year"));
    }

    public List<Book> index(Integer page, Integer books_per_page, Boolean sort_by_year){

        if (page != null && books_per_page != null && (sort_by_year == null || !sort_by_year)){
            return this.booksPerPage(page, books_per_page);

        } else if (sort_by_year != null && sort_by_year && page == null && books_per_page == null){
            return this.booksSortedByYear();

        } else if (sort_by_year != null && sort_by_year && page != null && books_per_page != null){
            return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();

        } else {
            return this.findAll();
        }

    }

    public ArrayList<Book> findBook(String name){
        return (ArrayList<Book>) bookRepository.findByBookNameStartingWith(name);
    }

}