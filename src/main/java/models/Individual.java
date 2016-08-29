package models;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * <h1>Created by Mattias on 2016-08-29.</h1>
 */
public class Individual {
    private String firstName, lastName, info;
    private int id = 0;

    public Individual() {}

    public Individual(String firstName, String lastName, String info, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.info = info;
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
