package com.example.diamondcircle.model;

public class ObicnaKarta extends Karta{
    private int pomjeraj;

    public ObicnaKarta() {

    }

    public ObicnaKarta(String putanja) {
        super(putanja);
        if (putanja.endsWith("1.png")) {
            pomjeraj = 1;
        } else if (putanja.endsWith("2.png")) {
            pomjeraj = 2;
        } else if (putanja.endsWith("3.png")) {
            pomjeraj = 3;
        } else if (putanja.endsWith("4.png")) {
            pomjeraj = 4;
        }
    }

    public int getPomjeraj() {
        return pomjeraj;
    }

    public void setPomjeraj(int pomjeraj) {
        this.pomjeraj = pomjeraj;
    }
}
