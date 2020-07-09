package com.it_academyproject.domains;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "preferences")
public class Preferences {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    private String value;

    private Boolean active;

    public Preferences() {}

    public Preferences(Long id, String name, String value, Boolean active) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.active = active;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preferences that = (Preferences) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value) &&
                Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, active);
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", active=" + active +
                '}';
    }
}
