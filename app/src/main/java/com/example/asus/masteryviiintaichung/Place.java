package com.example.asus.masteryviiintaichung;

public class Place {
    String name;
    String addr;
    int imgID;
    double Px;
    double Py;
    String info;

    public Place(String name, int imgID, String addr, double Px, double Py, String info) {
        this.name = name;
        this.addr = addr;
        this.imgID = imgID;
        this.Px = Px;
        this.Py = Py;
        this.info = info;
    }
}
