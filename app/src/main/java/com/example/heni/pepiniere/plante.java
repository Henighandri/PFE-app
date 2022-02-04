package com.example.heni.pepiniere;

import android.graphics.Bitmap;

import java.sql.Blob;

public class plante {
    private String name;
    private  int temperature ;
    private  int humidite ;
    private  int q_eau ;
    private int niv_eau ;
    private  String description ;


    public plante(String name, int temperature, int humidite, String description,int niv_eau) {
        this.name = name;
        this.temperature = temperature;
        this.humidite = humidite;
        this.description = description;
        this.niv_eau=niv_eau;
    }

    public plante(String name,int q_eau, int temperature, int humidite,String description) {
        this.name = name;
        this.q_eau = q_eau;
        this.temperature = temperature;
        this.humidite = humidite;
        this.description = description;
    }

    public plante(String name, int niv_eau) {
        this.name = name;
        this.niv_eau=niv_eau;
    }


    public String getName() {
        return name;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidite() {
        return humidite;
    }

    public String getDescription() {
        return description;
    }
    public int getQ_eau() {
        return q_eau;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setHumidite(int humidite) {
        this.humidite = humidite;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNiv_eau() {
        return niv_eau;
    }

    public void setNiv_eau(int background_cardview) {
        this.niv_eau = background_cardview;
    }
}
