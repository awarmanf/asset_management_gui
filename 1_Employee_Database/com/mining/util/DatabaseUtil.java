/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.util;

import com.mysql.jdbc.Driver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author yudi
 */
public class DatabaseUtil {

    private static Connection koneksi;
    private final static String CONFIG = "config/app.config";

    public static Connection getKoneksi() {

        Properties prop = new Properties();
        InputStream is = null;
        String url = "", user = "", password = "";

        try {
            is = new FileInputStream(CONFIG);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            prop.load(is);
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        if (koneksi == null) {
            try {
                Driver driver = new Driver();
                DriverManager.registerDriver(driver);
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi sukses.");
            } catch (SQLException ex) {
                System.out.println("Koneksi gagal.");
            }
        }
        return koneksi;
    }
}
