package com.it_academyproject.domains;

import com.it_academyproject.exceptions.EmptyFieldException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends MyAppUser {

    public Admin() {
        super();
        this.setRole(Role.ADMIN);
    }

    public Admin(String firstName, String lastName, String email, char gender, String portrait, String password, boolean enabled) {
        super(firstName, lastName, email, gender, password, enabled, Role.ADMIN);
    }

    public Admin(String email, String password) throws EmptyFieldException {
        super(email, password);
    }
}