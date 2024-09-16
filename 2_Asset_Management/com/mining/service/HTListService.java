/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.service;

import com.mining.entity.HTList;
import com.mining.resources.RStrings;
import com.mining.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author yudi
 */
public class HTListService {

    private Connection koneksi;
    private final static String table = "HT_List";

    public HTListService() {
        koneksi = DatabaseUtil.getKoneksi();

        if (koneksi == null) {
            System.exit(1);
        }
    }

    public Object[] getHeader() {
        Object[] header = {"ID", "IDRadio", "SAP", "SN"};
        return header;
    }

    public boolean insert(HTList htList) {

        boolean insert = false;
        
        PreparedStatement prepare = null;
        try {

            String sql = "INSERT INTO " + table + " (ID, IDRadio, SAP, SN) "
                    + "VALUES (?, ?, ?, ?)";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, htList.getID());
            prepare.setShort(2, htList.getIDRadio());
            prepare.setString(3, htList.getSAP());
            prepare.setString(4, htList.getSN());
            prepare.executeUpdate();
            insert = true;
            return insert;
        } catch (SQLException ex) {
            //System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_INSERT_ERROR, JOptionPane.ERROR_MESSAGE);
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return insert;
        }
    }

    public void update(HTList htList) {
        PreparedStatement prepare = null;
        try {

            String sql = "UPDATE " + table + " SET IDRadio = ?, SAP = ?, SN = ? "
                    + "WHERE ID = ?";
            prepare = koneksi.prepareStatement(sql);
            prepare.setShort(1, htList.getIDRadio());
            prepare.setString(2, htList.getSAP());
            prepare.setString(3, htList.getSN());
            prepare.setString(4, htList.getID());
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

    public void delete(String id) {
        PreparedStatement prepare = null;
        try {

            String sql = "DELETE FROM " + table + " WHERE ID = ?";
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

    public int countAll() {
        PreparedStatement prepare = null;
        ResultSet result = null;
        int count = 0;
        try {

            String sql = "SELECT count(*) AS Count FROM " + table;
            prepare = koneksi.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                count = result.getInt("Count");
            }
            return count;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
            return count;
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

    public HTList selectById(String id) {
        PreparedStatement prepare = null;
        ResultSet result = null;

        HTList htList = null;

        try {

            String sql = "SELECT * FROM " + table + " WHERE ID = ?";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, id);
            result = prepare.executeQuery();

            if (result.next()) {
                htList = new HTList();
                htList.setID(result.getString("ID"));
                htList.setIDRadio(result.getShort("IDRadio"));
                htList.setSAP(result.getString("SAP"));
                htList.setSN(result.getString("SN"));
            }
            return htList;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
            return htList;
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

    public List<HTList> selectAll() {

        PreparedStatement prepare = null;
        ResultSet result = null;

        List<HTList> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM " + table;
            prepare = koneksi.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                HTList htList = new HTList();
                htList.setID(result.getString("ID"));
                htList.setIDRadio(result.getShort("IDRadio"));
                htList.setSAP(result.getString("SAP"));
                htList.setSN(result.getString("SN"));
                list.add(htList);
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
