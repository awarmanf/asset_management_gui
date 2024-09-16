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
public class Position {
    
    private short id;
    private String position;
    private short iddept;
    private boolean staff;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public short getIddept() {
        return iddept;
    }

    public void setIddept(short iddept) {
        this.iddept = iddept;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }
    
 }
