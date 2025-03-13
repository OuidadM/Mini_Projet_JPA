package Models;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String street;
    private String city;
    private String state;
    //private Student student;


    /*@OneToOne(cascade = CascadeType.PERSIST)
    public Student getStudent() {
        return student;
    }*/

    public Integer getId() {
        return id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "street: " + street + ", city: " + city + ", state: " + state;
    }
}
