package com.example.asus.masteryviiintaichung;

public class Place {
    String name;
    String addr;
    int imgID;
    float score;
    double Px;
    double Py;
    String info;

    public Place(String name, int imgID, String addr, float score, double Px, double Py, String info) {
        this.name = name;
        this.addr = addr;
        this.imgID = imgID;
        this.Px = Px;
        this.Py = Py;
        this.info = info;
        this.score = score;
    }
}
