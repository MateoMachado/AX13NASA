package com.mycompany.ax13.nasa;

/**
 *
 * @author nicop
 */
public class Person {

    private String name;

    private String lastName;

    private int age;

    private String genre;

    private int height;

    private int weight;

    private int ID;

    public Person(String name, String lastName, int age, String genre, int height, int weight) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.genre = genre;
        this.height = height;
        this.weight = weight;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getID() {
        return ID;
    }
}
