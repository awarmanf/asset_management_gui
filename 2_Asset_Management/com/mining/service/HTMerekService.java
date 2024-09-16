/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.service;

import com.mining.entity.HTMerek;
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
public class HTMerekService {

    private Connection koneksi;
    private final static String table = "HT_Merek";

    public HTMerekService() {
        koneksi = DatabaseUtil.getKoneksi();
        if (koneksi == null) {
            System.exit(1);
        }
    }

    public Object[] getHeader() {
        Object[] header = {"ID", "Merek", "Tipe", "Frekuensi"};
        return header;
    }

    public void insert(HTMerek htMerek) {

        PreparedStatement prepare = null;
        try {

            String sql = "INSERT INTO " + table + " (Merek, Tipe, Frekuensi) "
                    + "VALUES (?, ?, ?)";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, htMerek.getMerek());
            prepare.setString(2, htMerek.getTipe());
            prepare.setString(3, htMerek.getFrekuensi());
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

    public void update(HTMerek htMerek) {
        PreparedStatement prepare = null;
        try {

            String sql = "UPDATE " + table + " SET Merek = ?, Tipe = ?, Frekuensi = ? "
                    + "WHERE ID = ?";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, htMerek.getMerek());
            prepare.setString(2, htMerek.getTipe());
            prepare.setString(3, htMerek.getFrekuensi());
            prepare.setShort(4, htMerek.getID());
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

    public void delete(short id) {
        PreparedStatement prepare = null;
        try {

            String sql = "DELETE FROM " + table + " WHERE ID = ?";
            prepare = koneksi.prepareStatement(sql);
            prepare.setInt(1, id);
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

    public short getID(String Merek, String Tipe) {

        PreparedStatement prepare = null;
        ResultSet result = null;
        Short ID = 0;

        try {

            String sql = "SELECT ID FROM HT_Merek where Merek=? and Tipe=?";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, Merek);
            prepare.setString(2, Tipe);
            result = prepare.executeQuery();

            if (result.next()) {
                ID = result.getShort("ID");
            }
            return ID;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
            return ID;
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

    public HTMerek selectById(short id) {
        PreparedStatement prepare = null;
        ResultSet result = null;
        HTMerek htMerek = null;

        try {

            String sql = "SELECT * FROM " + table + " WHERE ID = ?";
            prepare = koneksi.prepareStatement(sql);
            prepare.setInt(1, id);
            result = prepare.executeQuery();

            if (result.next()) {
                htMerek = new HTMerek();
                htMerek.setID(result.getShort("ID"));
                htMerek.setMerek(result.getString("Merek"));
                htMerek.setTipe(result.getString("Tipe"));
                htMerek.setFrekuensi(result.getString("Frekuensi"));
            }
            return htMerek;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.DLG_PREPARE_ERROR, JOptionPane.ERROR_MESSAGE);
            return htMerek;
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

    public List<String> getMereks() {

        PreparedStatement prepare = null;
        ResultSet result = null;
        List<String> list = new ArrayList<>();

        try {

            String sql = "SELECT distinct(Merek) FROM " + table;
            prepare = koneksi.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                list.add(result.getString("Merek"));
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

    public List<String> getTipe(String Merek) {

        PreparedStatement prepare = null;
        ResultSet result = null;
        List<String> list = new ArrayList<>();

        try {

            String sql = "SELECT Tipe FROM " + table + " WHERE Merek = ? ";
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, Merek);
            result = prepare.executeQuery();

            while (result.next()) {
                list.add(result.getString("Tipe"));
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

    public List<HTMerek> selectAll() {

        PreparedStatement prepare = null;
        ResultSet result = null;
        List<HTMerek> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM " + table;
            prepare = koneksi.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                HTMerek htMerek = new HTMerek();
                htMerek.setID(result.getShort("ID"));
                htMerek.setMerek(result.getString("Merek"));
                htMerek.setTipe(result.getString("Tipe"));
                htMerek.setFrekuensi(result.getString("Frekuensi"));
                list.add(htMerek);
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
