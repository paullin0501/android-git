package com.example.asus.masteryviiintaichung;

import android.graphics.Bitmap;

public class Place {
    public String name;
    public String addr;
    public Bitmap imgID;
    public float score = 1;
    public double Px;
    public double Py;
    public String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Bitmap getImgID() {
        return imgID;
    }

    public void setImgID(Bitmap imgID) {
        this.imgID = imgID;
    }

    public double getScore() {
        return score;
    }


    public double getPx() {
        return Px;
    }

    public void setPx(double px) {
        Px = px;
    }

    public double getPy() {
        return Py;
    }

    public void setPy(double py) {
        Py = py;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
