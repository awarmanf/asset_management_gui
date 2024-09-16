/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.service;

import com.mining.entity.Department;
import com.mining.resources.RStrings;
import com.mining.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private Connection koneksi;
    private final static String table = "Department";

    public DepartmentService() {
        koneksi = DatabaseUtil.getKoneksi();
        
        if (koneksi == null) {
            System.exit(1);
        }
    }

    public Object[] getHeader() {
        Object[] header = {"id", "department"};
        return header;
    }

    public void insert(Department department) {

        PreparedStatement prepare = null;
        try {

            String sql = "INSERT INTO " + table + " (id, department) "
                    + "VALUES (null, ?)";

            prepare = koneksi.prepareStatement(sql);

            prepare.setString(1, department.getDepartment());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public void update(Department department) {
        PreparedStatement prepare = null;
        try {

            String sql = "UPDATE " + table + " SET department = ?, "
                    + "WHERE id = ?";

            prepare = koneksi.prepareStatement(sql);

            prepare.setString(1, department.getDepartment());
            prepare.setShort(2, department.getId());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public void delete(short id) {
        PreparedStatement prepare = null;
        try {

            String sql = "DELETE FROM " + table + " WHERE id = ?";

            prepare = koneksi.prepareStatement(sql);

            prepare.setInt(1, id);

            prepare.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
        } finally {
            if (prepare != null) {
                try {
                    prepare.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public Department selectById(short id) {
        PreparedStatement prepare = null;
        ResultSet result = null;

        Department department = null;

        try {

            String sql = "SELECT * FROM " + table + " WHERE id = ?";

            prepare = koneksi.prepareStatement(sql);

            prepare.setInt(1, id);

            result = prepare.executeQuery();

            if (result.next()) {
                department = new Department();
                department.setId(result.getShort("id"));
                department.setDepartment(result.getString("department"));
            }

            return department;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return department;
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

    public String getDepartmentName(int id) {

        PreparedStatement prepare = null;
        ResultSet result = null;

        String departmentName = null;

        try {

            String sql = "SELECT department FROM " + table + " WHERE id = ?";

            prepare = koneksi.prepareStatement(sql);
            prepare.setInt(1, id);
            result = prepare.executeQuery();

            if (result.next()) {
                departmentName = result.getString("department");
            }
            return departmentName;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return departmentName;
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

    public short getDepartmentID(String department) {

        PreparedStatement prepare = null;
        ResultSet result = null;

        short id = 0;

        try {

            String sql = "SELECT id FROM " + table + " WHERE department = ?";

            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, department);
            result = prepare.executeQuery();

            if (result.next()) {
                id = result.getShort("id");
            }
            return id;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return id;
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

    public List<Department> selectAll() {

        PreparedStatement prepare = null;
        ResultSet result = null;

        List<Department> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM " + table;

            prepare = koneksi.prepareStatement(sql);

            result = prepare.executeQuery();

            while (result.next()) {
                Department department = new Department();

                department.setId(result.getShort("id"));
                department.setDepartment(result.getString("department"));
                list.add(department);
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

    public List<String> getDepartments() {

        PreparedStatement prepare = null;
        ResultSet result = null;

        List<String> list = new ArrayList<>();

        try {

            String sql = "SELECT department FROM " + table + " order by department";

            prepare = koneksi.prepareStatement(sql);

            result = prepare.executeQuery();

            while (result.next()) {
                list.add(result.getString("department"));
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
