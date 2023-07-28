package ua.zhurba.springcourse.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "bookName")
    @NotEmpty(message = "Назва книги не повинна бути порожньою")
    private String bookName;

    @Column(name = "author")
    @NotEmpty(message = "Автор не повинен бути порожнім")
    private String author;

    @Column(name = "year")
    @Min(value = 1900, message = "Рік не повинен бути меншим за 1900")
    private Integer year;

    @Column(name = "whenWasTaken")
    private Timestamp whenWasTaken;

    @Transient
    private Boolean ifOverdue;

    public Book(){

    }

    public Book(String bookName, String author, Integer year) {
        this.bookName = bookName;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Timestamp getWhenWasTaken() {
        return whenWasTaken;
    }

    public void setWhenWasTaken(Timestamp whenWasTaken) {
        this.whenWasTaken = whenWasTaken;
    }

    public Boolean getIfOverdue() { //864000000
        return System.currentTimeMillis() - this.getWhenWasTaken().getTime() > 864000000;
    }
}