package com.example.petrescue.domain.subClasses;

public class Localizacao {
    private Double altitude;
    private Double longitude;

    public Localizacao(Double altitude, Double longitude) {
        this.altitude = altitude;
        this.longitude = longitude;
    }

    public Localizacao() {
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
