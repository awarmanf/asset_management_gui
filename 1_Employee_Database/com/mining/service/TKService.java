/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.service;

import com.mining.entity.TK;
import com.mining.resources.RStrings;
import com.mining.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONObject;
import java.util.List;
import javax.swing.JOptionPane;

public class TKService {

    private Connection koneksi;
    private final static String table = "TK";

    public TKService() {
        koneksi = DatabaseUtil.getKoneksi();
        
        if (koneksi == null) {
            System.exit(1);
        }
    }

    public Object[] getHeader() {
        Object[] header = {"NIK", "Fullname", "Position", "Department"};
        return header;
    }

    public void insert(TK tk) {

        PreparedStatement prepare = null;
        try {

            String sql = "INSERT INTO " + table + " (NIK, Fullname, IDPosition, IDDepartment, SID) "
                    + "VALUES (?, ?, ?, ?, ?)";

            prepare = koneksi.prepareStatement(sql);

            prepare.setInt(1, tk.getNIK());
            prepare.setString(2, tk.getFullname());
            prepare.setShort(3, tk.getIDPosition());
            prepare.setShort(4, tk.getIDDepartment());
            prepare.setString(5, tk.getSID());

            prepare.executeUpdate();

            JOptionPane.showMessageDialog(null, RStrings.dlgInsertOK);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.dlgInsertError, JOptionPane.ERROR_MESSAGE);
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    /*
       Only update Fullname, Position, Department or Status for TK
     */
    public void update(TK tk) {
        PreparedStatement prepare = null;
        try {

            String sql = "UPDATE " + table + " SET Fullname = ?, IDPosition = ?, "
                    + "IDDepartment = ?, Status = ? "
                    + "WHERE NIK = ?";

            prepare = koneksi.prepareStatement(sql);

            prepare.setString(1, tk.getFullname());
            prepare.setShort(2, tk.getIDPosition());
            prepare.setShort(3, tk.getIDDepartment());
            prepare.setString(4, tk.getStatus());
            prepare.setInt(5, tk.getNIK());

            prepare.executeUpdate();

            JOptionPane.showMessageDialog(null, RStrings.dlgUpdateOK);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.dlgUpdateError, JOptionPane.ERROR_MESSAGE);
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public void delete(int nik) {
        PreparedStatement prepare = null;
        try {

            String sql = "DELETE FROM " + table + " WHERE NIK = ?";

            prepare = koneksi.prepareStatement(sql);

            prepare.setInt(1, nik);

            prepare.executeUpdate();

            JOptionPane.showMessageDialog(null, RStrings.dlgDeleteOK);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), RStrings.dlgDeleteError, JOptionPane.ERROR_MESSAGE);
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    /*
    public TK selectById(int nik) {
        PreparedStatement prepare = null;
        ResultSet result = null;

        TK tk = null;

        try {

            String sql = "SELECT * FROM " + table + " WHERE NIK = ?";

            prepare = koneksi.prepareStatement(sql);

            prepare.setInt(1, nik);

            result = prepare.executeQuery();

            if (result.next()) {
                tk = new TK();
                tk.setNIK(result.getInt("NIK"));
                tk.setFullname(result.getString("Fullname"));
                tk.setIDPosition(result.getShort("IDPosition"));
                tk.setIDDepartment(result.getShort("IDDepartment"));
                tk.setSID(result.getString("SID"));
                tk.setStatus(result.getString("Status"));
            }

            return tk;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return tk;
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
*/

    public TK selectByNIK(int nik) {
        PreparedStatement prepare = null;
        ResultSet result = null;

        TK tk = null;

        try {

            String sql = "SELECT NIK,Fullname,Position,Department,SID,Staff,Status FROM " + table
                    + ",Position,Department WHERE IDPosition=Position.id AND "
                    + "IDDepartment=Department.id AND NIK = ?";

            prepare = koneksi.prepareStatement(sql);

            prepare.setInt(1, nik);

            result = prepare.executeQuery();

            if (result.next()) {
                tk = new TK();
                tk.setNIK(result.getInt("NIK"));
                tk.setFullname(result.getString("Fullname"));
                tk.setPosition(result.getString("Position"));
                tk.setDepartment(result.getString("Department"));
                tk.setSID(result.getString("SID"));
                tk.setStaff(result.getBoolean("Staff"));
                tk.setStatus(result.getString("Status"));
            }

            return tk;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return tk;
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    
    public List<TK> selectAll() {

        PreparedStatement prepare = null;
        ResultSet result = null;

        List<TK> list = new ArrayList<>();

        try {

            String sql = "SELECT NIK, Fullname, Position, Department "
                    + "FROM TK, Position, Department "
                    + "WHERE IDPosition=Position.id and IDDepartment=Department.id "
                    + "      and STATUS='hired';";

            prepare = koneksi.prepareStatement(sql);

            result = prepare.executeQuery();

            while (result.next()) {
                TK tk = new TK();

                tk.setNIK(result.getInt("NIK"));
                tk.setFullname(result.getString("Fullname"));
                tk.setPosition(result.getString("Position"));
                tk.setDepartment(result.getString("Department"));
                list.add(tk);
            }

            return list;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return list;
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
    
    public List<TK> selectAll(JSONObject jo) {

        PreparedStatement prepare = null;
        ResultSet result = null;

        List<TK> list = new ArrayList<>();

        String sql = null;

        switch (jo.getString("var")) {
            case "All":
                sql = "SELECT NIK, Fullname, Position, Department "
                    + "FROM TK, Position, Department "
                    + "WHERE IDPosition=Position.id and IDDepartment=Department.id "
                    + "      and STATUS='hired';";
                break;
            case "NIK":
                int nik = jo.getInt("value");
                sql = "SELECT NIK, Fullname, Position, Department "
                        + "FROM TK, Position, Department "
                        + "WHERE IDPosition=Position.id and IDDepartment=Department.id "
                        + "  AND NIK like '%" + nik + "%' "
                        + "  AND STATUS='hired' ;";
                break;
            case "Name":
                String name = jo.getString("value");
                sql = "SELECT NIK, Fullname, Position, Department "
                        + "FROM TK, Position, Department "
                        + "WHERE IDPosition=Position.id and IDDepartment=Department.id "
                        + "  AND Fullname like '%" + name + "%' "
                        + "  AND STATUS='hired' ;";
                break;
            case "Department":
                String department = jo.getString("value");
                sql = "SELECT NIK, Fullname, Position, Department "
                        + "FROM TK, Position, Department "
                        + "WHERE IDPosition=Position.id and IDDepartment=Department.id "
                        + "  AND Department like '%" + department + "%' "
                        + "  AND STATUS='hired' ;";
                break;
            default: return null;
        }

        try {

            prepare = koneksi.prepareStatement(sql);

            result = prepare.executeQuery();

            while (result.next()) {
                TK tk = new TK();

                tk.setNIK(result.getInt("NIK"));
                tk.setFullname(result.getString("Fullname"));
                tk.setPosition(result.getString("Position"));
                tk.setDepartment(result.getString("Department"));
                
                list.add(tk);
            }
            return list;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return list;
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
}
