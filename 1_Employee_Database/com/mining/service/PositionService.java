/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.service;

import com.mining.entity.Position;
import com.mining.resources.RStrings;
import com.mining.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionService {

    private Connection koneksi;
    private final static String table = "Position";

    public PositionService() {
        koneksi = DatabaseUtil.getKoneksi();
        
        if (koneksi == null) {
            System.exit(1);
        }
    }

    public Object[] getHeader() {
        Object[] header = {"id", "position", "iddept", "staff"};
        return header;
    }

    public void insert(Position position) {

        PreparedStatement prepare = null;
        try {

            String sql = "INSERT INTO " + table + " (id, position, iddept, staff) "
                    + "VALUES (null, ?, ?, ?)";

            prepare = koneksi.prepareStatement(sql);

            prepare.setString(1, position.getPosition());
            prepare.setShort(2, position.getIddept());
            prepare.setBoolean(3, position.isStaff());

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

    public void update(Position position) {
        PreparedStatement prepare = null;
        try {

            String sql = "UPDATE " + table + " SET position = ?, iddept = ?, staff = ? "
                    + "WHERE id = ?";

            prepare = koneksi.prepareStatement(sql);

            prepare.setString(1, position.getPosition());
            prepare.setShort(2, position.getIddept());
            prepare.setBoolean(3, position.isStaff());
            prepare.setShort(4, position.getId());

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

    public Position selectById(short id) {
        PreparedStatement prepare = null;
        ResultSet result = null;

        Position position = null;

        try {

            String sql = "SELECT * FROM " + table + " WHERE id = ?";

            prepare = koneksi.prepareStatement(sql);

            prepare.setInt(1, id);

            result = prepare.executeQuery();

            if (result.next()) {
                position = new Position();
                position.setId(result.getShort("id"));
                position.setPosition(result.getString("position"));
                position.setIddept(result.getShort("iddept"));
                position.setStaff(result.getBoolean("staff"));
            }

            return position;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return position;
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

    public String getPositionName(int id) {

        PreparedStatement prepare = null;
        ResultSet result = null;

        String positionName = null;

        try {

            String sql = "SELECT position FROM " + table + " WHERE id = ?";

            prepare = koneksi.prepareStatement(sql);
            prepare.setInt(1, id);
            result = prepare.executeQuery();

            if (result.next()) {
                positionName = result.getString("position");
            }
            return positionName;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return positionName;
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

    public short getPositionID(String position) {

        PreparedStatement prepare = null;
        ResultSet result = null;

        short id = 0;

        try {

            String sql = "SELECT id FROM " + table + " WHERE position = ?";

            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, position);
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

    public List<String> getPositionDept(short iddept) {

        PreparedStatement prepare = null;
        ResultSet result = null;

        List<String> list = new ArrayList<>();

        try {

            String sql = "SELECT position FROM " + table + " WHERE iddept = ? order by position";

            prepare = koneksi.prepareStatement(sql);
            prepare.setInt(1, iddept);
            result = prepare.executeQuery();

            while (result.next()) {
                list.add(result.getString("position"));
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

    public List<String> getPositionDept(String department, boolean staff) {

        PreparedStatement prepare = null;
        ResultSet result = null;

        List<String> list = new ArrayList<>();

        try {

            String sql = "SELECT position FROM " + table + ",Department "
                    + " WHERE Position.iddept=Department.id AND department=? AND staff=? ORDER BY position";

            prepare = koneksi.prepareStatement(sql);
            prepare.setString(1, department);
            prepare.setBoolean(2, staff);
            result = prepare.executeQuery();

            while (result.next()) {
                list.add(result.getString("position"));
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

    public Boolean getStaff(int id) {

        PreparedStatement prepare = null;
        ResultSet result = null;

        Boolean Staff = false;

        try {

            String sql = "SELECT staff FROM " + table + " WHERE id = ?";

            prepare = koneksi.prepareStatement(sql);
            prepare.setInt(1, id);
            result = prepare.executeQuery();

            if (result.next()) {
                Staff = result.getBoolean("staff");
            }
            return Staff;
        } catch (SQLException ex) {
            System.out.println(RStrings.prepareError + ": " + ex.getMessage());
            return Staff;
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

    public List<Position> selectAll() {

        PreparedStatement prepare = null;
        ResultSet result = null;

        List<Position> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM " + table;

            prepare = koneksi.prepareStatement(sql);

            result = prepare.executeQuery();

            while (result.next()) {
                Position position = new Position();

                position.setId(result.getShort("id"));
                position.setPosition(result.getString("position"));
                position.setIddept(result.getShort("iddept"));
                position.setStaff(result.getBoolean("staff"));

                list.add(position);
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
