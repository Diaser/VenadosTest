package com.example.venadostest.vo;

import java.util.Date;

public class Jugador {

    private String name;
    private String first_surname;
    private String second_surname;
    private Date birthday;
    private String birth_place;
    private float weight;
    private float height;
    private String position;
    private String position_short;
    private int number;
    private String last_team;
    private String image;


    public Jugador(){ }

    public Jugador(String name, String first_surname, String second_surname, Date birthday,
                   String birth_place, float weight, float height, String position, String position_short,
                   int number, String last_team, String image){
        this.name =  name;this.first_surname = first_surname;this.second_surname = second_surname; this.birthday= birthday;
        this.birth_place = birth_place; this.weight = weight; this.height = height; this.position = position;
        this.position_short = position_short; this.number = number; this.last_team = last_team; this.image = image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_surname() {
        return first_surname;
    }

    public void setFirst_surname(String first_surname) {
        this.first_surname = first_surname;
    }

    public String getSecond_surname() {
        return second_surname;
    }

    public void setSecond_surname(String second_surname) {
        this.second_surname = second_surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition_short() {
        return position_short;
    }

    public void setPosition_short(String position_short) {
        this.position_short = position_short;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLast_team() {
        return last_team;
    }

    public void setLast_team(String last_team) {
        this.last_team = last_team;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
