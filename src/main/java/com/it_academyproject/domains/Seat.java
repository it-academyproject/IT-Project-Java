package com.it_academyproject.domains;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

import javax.persistence.*;
import java.util.List;

@Entity
public class Seat
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @JsonView(View.Summary.class)
    private int rowNumber;
    @JsonView(View.Summary.class)
    private int colNumber;
    @JsonView(View.Summary.class)
    private int classRoom;

    @OneToMany
    private List<MyAppUser> myAppUser;

    
    
    public Seat() {
		
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public List<MyAppUser> getMyAppUser() {
        return myAppUser;
    }

    public void setMyAppUser(List<MyAppUser> myAppUser) {
        this.myAppUser = myAppUser;
    }

    public int getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(int classRoom) {
        this.classRoom = classRoom;
    }

    // TODO Delete when OK
    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", rowNumber=" + rowNumber +
                ", colNumber=" + colNumber +
                ", classRoom=" + classRoom +
                '}';
    }
}
