package com.example.petrescue.domain.subClasses;

public class Localizacao {
    private Double latitudee;
    private Double longitude;

    public Localizacao(Double latitudee, Double longitude) {
        this.latitudee = latitudee;
        this.longitude = longitude;
    }

    public Localizacao() {
    }

    public Double getLatitudee() {
        return latitudee;
    }

    public void setLatitudee(Double latitudee) {
        this.latitudee = latitudee;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
