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
import ua.zhurba.springcourse.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonValidator personValidator;
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public PeopleController(BookService bookService, PersonService personService, PersonValidator personValidator){
        this.bookService = bookService;
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personService.findAll());
        return "/people/allPeople";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "/people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/people/new";
        }

        personValidator.validate(person, bindingResult);

        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "people/edit";

        personValidator.validate(person, bindingResult);

        personService.update(id, person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}/edit")
    public String updateWithoutValidator(Model model, @PathVariable("id") int id) {

        model.addAttribute("person", personService.findOne(id));

        return "people/edit";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.findOne(id));
        model.addAttribute("books", personService.findOne(id).getBookList());

        return "/people/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/people";
    }
}