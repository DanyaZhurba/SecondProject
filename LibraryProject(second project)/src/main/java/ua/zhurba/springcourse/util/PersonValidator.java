package ua.zhurba.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.zhurba.springcourse.models.Person;
import ua.zhurba.springcourse.services.PersonService;

@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService){
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personService.findPersonBySurnameNameSurname(person.getSurnameNameSurname()) != null){
            errors.rejectValue("SurnameNameSurname", "", "Цей ПІБ зайнятий");
        }
    }
}
