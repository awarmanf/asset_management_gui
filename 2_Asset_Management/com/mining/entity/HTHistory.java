/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.entity;

import java.sql.Timestamp;

/**
 *
 * @author yudi
 */
public class HTHistory {
    private String Date;
    private int HID;
    private String ID;
    private int NIK;
    private String Status;
    private String Note;
    private String Merek;
    private String Tipe;
    private String SN;
    private String Fullname;
    private String Department;
    private boolean Current;

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getHID() {
        return HID;
    }
    
    public void setHID(int HID) {
        this.HID = HID;
    }
    
    public int getNIK() {
        return NIK;
    }

    public void setNIK(int NIK) {
        this.NIK = NIK;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public boolean isCurrent() {
        return Current;
    }

    public void setCurrent(boolean Current) {
        this.Current = Current;
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

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String Fullname) {
        this.Fullname = Fullname;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }
    
}
