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
public class TK {
    
    private int NIK;
    private short IDDepartment;
    private short IDPosition;
    private String Fullname;
    private String SID;
    private String Status;
    private String Department;
    private String Position;
    private boolean staff;

    public int getNIK() {
        return NIK;
    }

    public void setNIK(int NIK) {
        this.NIK = NIK;
    }

    public short getIDDepartment() {
        return IDDepartment;
    }

    public void setIDDepartment(short IDDepartment) {
        this.IDDepartment = IDDepartment;
    }

    public short getIDPosition() {
        return IDPosition;
    }

    public void setIDPosition(short IDPosition) {
        this.IDPosition = IDPosition;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String Fullname) {
        this.Fullname = Fullname;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }
}
