/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.entity;

/**
 *
 * @author yudi
 */
public class HTMerek {

    private short ID;
    private String Merek;
    private String Tipe;
    private String Frekuensi;

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public String getMerek() {
        return Merek;
    }

    public void setMerek(String Merek) {
        this.Merek = Merek;
    }

    public String getTipe() {
        return Tipe;
    }

    public void setTipe(String Tipe) {
        this.Tipe = Tipe;
    }

    public String getFrekuensi() {
        return Frekuensi;
    }

    public void setFrekuensi(String Frekuensi) {
        this.Frekuensi = Frekuensi;
    }
    
}
