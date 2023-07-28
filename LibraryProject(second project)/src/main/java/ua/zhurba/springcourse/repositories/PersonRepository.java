package ua.zhurba.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zhurba.springcourse.models.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findPersonBySurnameNameSurname(String surnamenamesurname);

}
