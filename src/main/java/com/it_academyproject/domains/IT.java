package com.it_academyproject.domains;

import com.it_academyproject.exceptions.EmptyFieldException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("IT")
public class IT extends MyAppUser {

    public IT() {
        super();
        this.setRole(Role.IT);
    }

    public IT(String firstName, String lastName, String email, char gender, String portrait, String password, boolean enabled) {
        super(firstName, lastName, email, gender, password, enabled, Role.IT);
    }

    public IT(String email, String password) throws EmptyFieldException {
        super(email, password);
        this.setRole(Role.IT);
    }
}
