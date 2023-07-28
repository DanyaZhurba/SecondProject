package ua.zhurba.springcourse.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Book> bookList;

    @Column(name = "surnameNameSurname")
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String surnameNameSurname;

    @Column(name = "birthday")
    @Min(value = 0, message = "Birthday should be greater than 0")
    private Integer birthday;

    public Person(){

    }

    public Person(String surnameNameSurname, Integer birthday){
        this.surnameNameSurname = surnameNameSurname;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurnameNameSurname() {
        return surnameNameSurname;
    }

    public void setSurnameNameSurname(String surnameNameSurname) {
        this.surnameNameSurname = surnameNameSurname;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
