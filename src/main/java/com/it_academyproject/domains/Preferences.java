package com.it_academyproject.domains;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "preferences")
public class Preferences {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long preference_id;

    @Column(unique = true)
    private String preference_name;

    private String preference_value;

    private Boolean active;

    public Preferences() {}

    public Preferences(Long preference_id, String preference_name, String preference_value, Boolean active) {
        this.preference_id = preference_id;
        this.preference_name = preference_name;
        this.preference_value = preference_value;
        this.active = active;
    }

    public Long getPreference_id() {
        return preference_id;
    }

    public void setPreference_id(Long preference_id) {
        this.preference_id = preference_id;
    }

    public String getPreference_name() {
        return preference_name;
    }

    public void setPreference_name(String preference_name) {
        this.preference_name = preference_name;
    }

    public String getPreference_value() {
        return preference_value;
    }

    public void setPreference_value(String preference_value) {
        this.preference_value = preference_value;
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
        return Objects.equals(preference_id, that.preference_id) &&
                Objects.equals(preference_name, that.preference_name) &&
                Objects.equals(preference_value, that.preference_value) &&
                Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preference_id, preference_name, preference_value, active);
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "preference_id=" + preference_id +
                ", preference_name='" + preference_name + '\'' +
                ", preference_value='" + preference_value + '\'' +
                ", active=" + active +
                '}';
    }
}
