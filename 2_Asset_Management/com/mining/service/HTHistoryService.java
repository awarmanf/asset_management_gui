/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.service;

import com.mining.entity.HTHistory;

import com.mining.resources.RStrings;
import com.mining.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author yudi
 */
public class HTHistoryService {

    private Connection koneksi;
    private final static String table = "HT_History";

    public HTHistoryService() {
        koneksi = DatabaseUtil.getKoneksi();

        if (koneksi == null) {
            System.exit(1);
        }
    }

    public Object[] getMainHeader() {
        Object[] header = {"Date", "ID", "Merek", "Tipe", "SN", "Fullname", "Department", "Status", "Note"};
        return header;
    }

    public Object[] getShowHistoryHeader() {
        //Object[] header = {"Date", "ID", "Fullname", "Department", "Status", "Note"};
        Object[] header = {"HID", "Date", "Current", "Fullname", "Department", "Status", "Note"};
        return header;
    }

    public Object[] getHistoryHeader() {
        Object[] header = {"Date", "ID", "Fullname", "Department", "Status", "Note"};
        return header;
    }

    public Object[] getStatusHeader() {
        Object[] header = {"Date", "ID", "Merek", "Tipe", "SN", "Status", "Note"};
        return header;
    }

    public void insert(HTHistory htHistory) {

        PreparedStatement prepare = null;
        try {

            String sql = "INSERT INTO " + table + " (ID, Date, Current, NIK, Status, Note) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, htHistory.getID());
            prepare.setString(2, htHistory.getDate());
            prepare.setBoolean(3, htHistory.isCurrent());
            prepare.setInt(4, htHistory.getNIK());
            prepare.setString(5, htHistory.getStatus());
            prepare.setString(6, htHistory.getNote());
            prepare.executeUpdate();

            //JOptionPane.showMessageDialog(null, RStrings.dlgInsertOK);
        } catch (SQLException ex) {
            System.out.println(RStrings.DLG_PREPARE_ERROR + ": " + ex.getMessage());
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public void update(HTHistory htHistory) {
        PreparedStatement prepare = null;
        try {

            String sql = "UPDATE " + table + " SET Date = ?, Status = ?, Note = ?, Current = ? "
                    + "WHERE HID = ?";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, htHistory.getDate());
            prepare.setString(2, htHistory.getStatus());
            prepare.setString(3, htHistory.getNote());
            prepare.setBoolean(4, htHistory.isCurrent());
            prepare.setInt(5, htHistory.getHID());
            prepare.executeUpdate();
            //JOptionPane.showMessageDialog(null, RStrings.dlgUpdateOK);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_UPDATE_ERROR, JOptionPane.ERROR_MESSAGE);
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public void delete(int HID) {
        PreparedStatement prepare = null;
        try {
            // Can not delete if radio status is in 'STORE'
            String sql = "DELETE FROM " + table + " WHERE HID = ? AND Status <> 'STORE'";
            prepare = koneksi.prepareStatement(sql);
            prepare.setInt(1, HID);
            prepare.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_DELETE_ERROR, JOptionPane.ERROR_MESSAGE);
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public void ResetCurrentID(String id) {
        PreparedStatement prepare = null;
        try {

            String sql = "UPDATE " + table + " SET Current=0 WHERE ID = ?";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, id);
            prepare.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public void SetCurrentID(String id) {
        PreparedStatement prepare = null;
        try {

            String sql = "UPDATE " + table + " SET Current=1 WHERE ID = ? ORDER BY Date DESC LIMIT 1";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, id);
            prepare.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public List<HTHistory> SelectById(String ID) {

        PreparedStatement prepare = null;
        ResultSet result = null;
        List<HTHistory> list = new ArrayList<>();

        try {
            String sql = "SELECT Date, Current, HID, HT_History.ID, Fullname, Department, HT_History.Status, Note "
                    + "FROM HT_History, PTMaju.TK, PTMaju.Department "
                    + "WHERE HT_History.NIK=PTMaju.TK.NIK AND "
                    + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                    + "HT_History.ID=? ORDER BY Date;";

            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, ID);
            result = prepare.executeQuery();
            while (result.next()) {
                HTHistory htHistory = new HTHistory();
                htHistory.setHID(result.getInt("HID"));
                htHistory.setCurrent(result.getBoolean("Current"));
                htHistory.setDate(result.getString("Date"));
                htHistory.setID(result.getString("ID"));
                htHistory.setFullname(result.getString("Fullname"));
                htHistory.setDepartment(result.getString("Department"));
                htHistory.setStatus(result.getString("Status"));
                htHistory.setNote(result.getString("Note"));
                list.add(htHistory);
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
            return list;
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public List<HTHistory> SelectByStatus(String Status) {

        PreparedStatement prepare = null;
        ResultSet result = null;
        List<HTHistory> list = new ArrayList<>();

        try {

            String sql = "SELECT Date, HT_History.ID, Merek, Tipe, SN, Status, Note "
                    + "FROM HT_History, HT_List, HT_Merek "
                    + "WHERE HT_History.ID=HT_List.ID AND "
                    + "HT_List.IDRadio=HT_Merek.ID AND "
                    + "HT_History.Status=? ORDER BY HT_History.ID;";

            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, Status);
            result = prepare.executeQuery();
            while (result.next()) {
                HTHistory htHistory = new HTHistory();
                htHistory.setDate(result.getString("Date"));
                htHistory.setID(result.getString("ID"));
                htHistory.setMerek(result.getString("Merek"));
                htHistory.setTipe(result.getString("Tipe"));
                htHistory.setSN(result.getString("SN"));
                htHistory.setStatus(result.getString("Status"));
                htHistory.setNote(result.getString("Note"));
                list.add(htHistory);
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
            return list;
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public List<HTHistory> SelectAll(JSONObject jo) {
        PreparedStatement prepare = null;
        ResultSet result = null;
        List<HTHistory> list = new ArrayList<>();
        String sql = null;

        switch (jo.getString("var")) {
            case "All":
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Current=1 ORDER BY ID;";
                break;
            case "SN":
                String SN = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Current=1 AND SN like '%" + SN + "%';";
                break;
            case "NIK":
                String NIK = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Current=1 AND HT_History.NIK like '%" + NIK + "%';";
                break;
            case "Name":
                String Name = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Current=1 AND Fullname like '%" + Name + "%';";
                break;
            case "Department":
                String Department = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Current=1 AND Department like '%" + Department + "%';";
                break;
            case "Status":
                String Status = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Current=1 AND HT_History.Status like '%" + Status + "%';";
                break;
            case "Note":
                String Note = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Current=1 AND Note like '%" + Note + "%';";
                break;

            default:
                return null;
        }
        try {
            prepare = koneksi.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                HTHistory htHistory = new HTHistory();
                htHistory.setDate(result.getString("Date"));
                htHistory.setID(result.getString("ID"));
                htHistory.setMerek(result.getString("Merek"));
                htHistory.setTipe(result.getString("Tipe"));
                htHistory.setSN(result.getString("SN"));
                htHistory.setFullname(result.getString("Fullname"));
                htHistory.setDepartment(result.getString("Department"));
                htHistory.setStatus(result.getString("Status"));
                htHistory.setNote(result.getString("Note"));
                list.add(htHistory);
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
            return list;
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public List<HTHistory> SelectAllHistory(JSONObject jo) {
        PreparedStatement prepare = null;
        ResultSet result = null;
        List<HTHistory> list = new ArrayList<>();
        String sql = null;

        switch (jo.getString("var")) {
            case "SN":
                String SN = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "SN like '%" + SN + "%' ORDER BY Date;";
                break;
            case "Name":
                String Name = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Fullname like '%" + Name + "%' ORDER BY Date;";
                break;
            case "Department":
                String Department = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Department like '%" + Department + "%' ORDER BY Date;";
                break;
            case "Status":
                String Status = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "HT_History.Status like '%" + Status + "%' ORDER BY Date;";
                break;
            case "Note":
                String Note = jo.getString("value");
                sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                        + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                        + "WHERE HT_History.ID=HT_List.ID AND "
                        + "HT_List.IDRadio=HT_Merek.ID AND "
                        + "HT_History.NIK=PTMaju.TK.NIK AND "
                        + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                        + "Note like '%" + Note + "%' ORDER BY Date;";
                break;

            default:
                return null;
        }
        try {
            prepare = koneksi.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                HTHistory htHistory = new HTHistory();
                htHistory.setDate(result.getString("Date"));
                htHistory.setID(result.getString("ID"));
                htHistory.setMerek(result.getString("Merek"));
                htHistory.setTipe(result.getString("Tipe"));
                htHistory.setSN(result.getString("SN"));
                htHistory.setFullname(result.getString("Fullname"));
                htHistory.setDepartment(result.getString("Department"));
                htHistory.setStatus(result.getString("Status"));
                htHistory.setNote(result.getString("Note"));
                list.add(htHistory);
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
            return list;
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }    

    public List<HTHistory> SelectAll() {

        PreparedStatement prepare = null;
        ResultSet result = null;
        List<HTHistory> list = new ArrayList<>();

        try {
            String sql;
            sql = " SELECT Date, HT_History.ID, Merek,Tipe, SN, Fullname, Department, HT_History.Status, Note "
                    + "FROM HT_History, HT_List, HT_Merek, PTMaju.TK, PTMaju.Department "
                    + "WHERE HT_History.ID=HT_List.ID AND "
                    + "HT_List.IDRadio=HT_Merek.ID AND "
                    + "HT_History.NIK=PTMaju.TK.NIK AND "
                    + "PTMaju.TK.IDDepartment=PTMaju.Department.id AND "
                    + "Current=1 ORDER BY ID;";

            prepare = koneksi.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                HTHistory htHistory = new HTHistory();
                htHistory.setDate(result.getString("Date"));
                htHistory.setID(result.getString("ID"));
                htHistory.setMerek(result.getString("Merek"));
                htHistory.setTipe(result.getString("Tipe"));
                htHistory.setSN(result.getString("SN"));
                htHistory.setFullname(result.getString("Fullname"));
                htHistory.setDepartment(result.getString("Department"));
                htHistory.setStatus(result.getString("Status"));
                htHistory.setNote(result.getString("Note"));
                list.add(htHistory);
            }
            return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
            return list;
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
