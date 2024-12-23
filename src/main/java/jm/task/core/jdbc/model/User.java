package jm.task.core.jdbc.model;

import org.hibernate.annotations.Entity;

import javax.persistence.*;

@javax.persistence.Entity

@Entity
@Table(name = "User")  // Таблица в базе данных
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автоинкремент в MySQL
    @Column(name = "id")  // Колонка id в таблице
    private long id;

    @Column(name = "name")  // Колонка name в таблице
    private String name;

    @Column(name = "lastName")  // Колонка lastName в таблице
    private String lastName;

    @Column(name = "age")  // Колонка age в таблице
    private byte age;

    // Конструкторы, геттеры и сеттеры
    public User() {}

    public User(String name, String lastName, byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }
}
