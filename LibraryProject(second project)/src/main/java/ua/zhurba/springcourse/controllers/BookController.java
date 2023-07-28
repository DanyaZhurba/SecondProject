package ua.zhurba.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.zhurba.springcourse.models.Book;
import ua.zhurba.springcourse.models.Person;
import ua.zhurba.springcourse.services.BookService;
import ua.zhurba.springcourse.services.PersonService;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Controller
@RequestMapping("/books")
public class BookController {

    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public BookController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer books_per_page,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sort_by_year){

        model.addAttribute("books", bookService.index(page, books_per_page, sort_by_year));

        return "/books/allBooks";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());

        return "/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "/books/new";
        }

        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.findOne(id));

        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, Model model,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "/books/edit";
        }

        bookService.update(id, book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/edit")
    public String updateWithoutValidator(Model model, @PathVariable("id") int id){
        model.addAttribute(bookService.findOne(id));

        return "/books/edit";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("potentialOwner", bookService.getPersonWithBookId(id));
        model.addAttribute("people", personService.findAll());

        return "/books/show";
    }

    @PatchMapping("/{id}/add")
    public String addBook(@PathVariable("id") int id,
                          @ModelAttribute("person") Person person){
        bookService.addBook(id, person);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.releaseBook(id);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);

        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("books") ArrayList<Book> books,
                         @ModelAttribute("inputValue") String inputValue,
                         @RequestParam(value = "inputValue", required = false) String inputValueFromUser,
                         Model model){

        if (inputValueFromUser != null) {
            model.addAttribute("books", bookService.findBook(inputValueFromUser));
        }

        return "/books/search";
    }

    @PostMapping("/search")
    public String operateSearch(@RequestParam(name = "inputValue") String inputValue){

        return "redirect:/books/search?inputValue=" + URLEncoder.encode(inputValue, StandardCharsets.UTF_8);
    }
}