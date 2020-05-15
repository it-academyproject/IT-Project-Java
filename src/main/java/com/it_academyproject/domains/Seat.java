package com.it_academyproject.domains;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.tools.View;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

/*
    @OneToMany
    private List<Student> students;
*/

    
    
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

/*
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
*/

    public int getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(int classRoom) {
        this.classRoom = classRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;
        Seat seat = (Seat) o;
        return getRowNumber() == seat.getRowNumber() &&
                getColNumber() == seat.getColNumber() &&
                getClassRoom() == seat.getClassRoom();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRowNumber(), getColNumber(), getClassRoom());
    }
}