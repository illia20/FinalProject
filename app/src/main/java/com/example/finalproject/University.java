package com.example.finalproject;

import java.io.Serializable;

public class University implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String city;
    private int numberOfStudents;
    private int worldrank;
    private int impactrank;
    private int opennessrank;
    private int excellencerank;
    private double lng;
    private double lat;


    public University(String name, String city, int numberOfStudents, int worldrank, int impactrank, int opennessrank, int excellencerank, double lng, double lat) {
        this.name = name;
        this.city = city;
        this.numberOfStudents = numberOfStudents;
        this.worldrank = worldrank;
        this.impactrank = impactrank;
        this.opennessrank = opennessrank;
        this.excellencerank = excellencerank;
        this.lng = lng;
        this.lat = lat;
    }

    public University() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getWorldrank() {
        return worldrank;
    }

    public void setWorldrank(int worldrank) {
        this.worldrank = worldrank;
    }

    public int getImpactrank() {
        return impactrank;
    }

    public void setImpactrank(int impactrank) {
        this.impactrank = impactrank;
    }

    public int getOpennessrank() {
        return opennessrank;
    }

    public void setOpennessrank(int opennessrank) {
        this.opennessrank = opennessrank;
    }

    public int getExcellencerank() {
        return excellencerank;
    }

    public void setExcellencerank(int excellencerank) {
        this.excellencerank = excellencerank;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
