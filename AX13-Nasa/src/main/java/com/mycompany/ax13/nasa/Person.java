package com.mycompany.ax13.nasa;

/**
 *
 * @author nicop
 */
public class Person {

    private String name;

    private String lastName;

    private double age;

    private String genre;

    private double height;

    private double weight;

    private int ID;

    public Person() {

    }

    public Person(String name, String lastName, double age, String genre, double height, double weight) {
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

    public void setAge(double age) {
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getID() {
        return ID;
    }

    public double getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double calcularCalorias() {
        double bmr;
        if (genre.equals("hombre") || genre.equals("masculino") || genre.equals("m")) {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        } else {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }
        return bmr * 1.725;
    }
}
