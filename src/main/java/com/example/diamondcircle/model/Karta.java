package com.example.diamondcircle.model;

public abstract class Karta {

    private String putanjaDoSlike;

    public Karta() {
    }

    public Karta(String putanjaDoSlike) {
        this.putanjaDoSlike = putanjaDoSlike;
    }

    public String getPutanjaDoSlike() {
        return putanjaDoSlike;
    }

    public void setPutanjaDoSlike(String putanjaDoSlike) {
        this.putanjaDoSlike = putanjaDoSlike;
    }
}
