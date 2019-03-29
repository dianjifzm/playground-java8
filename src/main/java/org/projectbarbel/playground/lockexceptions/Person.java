package org.projectbarbel.playground.lockexceptions;

import javax.annotation.concurrent.NotThreadSafe;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NotThreadSafe
public class Person {
    private String id;
    private String firstname;
    private String lastname;
    private String street;
    private String postalcode;
    private String city;
    public Person(Person template) {
        this.id = template.id;
        this.firstname = template.firstname;
        this.lastname = template.lastname;
        this.street = template.street;
        this.postalcode = template.postalcode;
        this.city = template.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}