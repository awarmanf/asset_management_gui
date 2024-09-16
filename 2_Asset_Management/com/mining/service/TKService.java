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
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author yudi
 */
public class TKService {

    private Connection koneksi;
    private final static String tblTK = "PTMaju.TK";
    private final static String tblPosition = "PTMaju.Position";
    private final static String tblDepartment = "PTMaju.Department";

    public TKService() {
        koneksi = DatabaseUtil.getKoneksi();
        if (koneksi == null) {
            System.exit(1);
        }
    }

    public Object[] getHeader() {
        Object[] header = {"NIK", "Fullname", "Status", "Position", "Department"};
        return header;
    }

    public List<TK> selectAll(String NIK) {

        PreparedStatement prepare = null;
        ResultSet result = null;

        List<TK> list = new ArrayList<>();

        String sql = "SELECT Fullname, Position, Department "
                + " FROM " + tblTK + "," + tblPosition + "," + tblDepartment
                + " WHERE " + tblTK + ".IDPosition = " + tblPosition + ".id AND "
                + tblTK + ".IDDepartment = " + tblDepartment + ".id AND " + tblTK + ".NIK = ?;";
        
        try {
            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, NIK);
            result = prepare.executeQuery();
            while (result.next()) {
                TK tk = new TK();
                tk.setFullname(result.getString("Fullname"));
                tk.setPosition(result.getString("Position"));
                tk.setDepartment(result.getString("Department"));
                list.add(tk);
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

    public static void main(String[] args) {

        String NIK = "91102226";

        TKService service = new TKService();
        List<TK> list = service.selectAll(NIK);

        for (TK tk : list) {
            System.out.println(tk.getFullname());
            System.out.println(tk.getPosition());
            System.out.println(tk.getDepartment());
        }
    }

}
