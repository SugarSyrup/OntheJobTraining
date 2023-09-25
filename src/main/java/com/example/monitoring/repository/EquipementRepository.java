package com.example.monitoring.repository;

import com.example.monitoring.domain.Equipment;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipementRepository implements IEquipementRepository{
    private final DataSource dataSource;

    public EquipementRepository(DataSource dataSource) { this.dataSource = dataSource;}

    @Override
    public List<Equipment> findAll() {
        String sql = "SELECT * FROM equipment";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Equipment> equipments = new ArrayList<Equipment>();
            while(rs.next()) {
                Equipment equipment = new Equipment();
                equipment.setName(rs.getString("name"));
                equipment.setDivision(rs.getString("division"));
                equipment.setLocation(rs.getString("location"));
                equipment.setRegist_date(rs.getString("regist_date"));

                equipments.add(equipment);
            }

            return equipments;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    public List<Equipment> findAllByConditions(Equipment equipment){
        String sql = "SELECT * FROM equipment WHERE name LIKE ? AND location LIKE ? AND division LIKE ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%" + equipment.getName() + "%");
            pstmt.setString(2,"%" + (equipment.getLocation().equals("NONE") ? "" : equipment.getLocation()) + "%");
            pstmt.setString(3,"%" + (equipment.getDivision().equals("NONE") ? "" : equipment.getDivision()) + "%");

            rs = pstmt.executeQuery();

            List<Equipment> equipments = new ArrayList<Equipment>();
            while(rs.next()) {
                Equipment _equipment = new Equipment();
                _equipment.setName(rs.getString("name"));
                _equipment.setDivision(rs.getString("division"));
                _equipment.setLocation(rs.getString("location"));
                _equipment.setRegist_date(rs.getString("regist_date"));

                equipments.add(_equipment);
            }

            return equipments;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    public Equipment findEquipmentById(String key) {
        String sql = "SELECT * FROM equipment WHERE name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,key);
            rs = pstmt.executeQuery();

            Equipment equipment = new Equipment();
            rs.next();

            equipment.setName(rs.getString("name"));
            equipment.setDivision(rs.getString("division"));
            equipment.setLocation(rs.getString("location"));

            return equipment;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<String> findLocations() {
        String sql = "SELECT DISTINCT location FROM equipment ORDER BY location ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<String> locations = new ArrayList<String>();
            while(rs.next()) {
                locations.add(rs.getString("location"));
            }

            return locations;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<String> findLocations(String division) {
        String sql = "SELECT DISTINCT location FROM equipment WHERE division LIKE ? ORDER BY location ASC";


        if(division.equals("기온")) {
            sql = "SELECT DISTINCT location FROM temperature_statics LEFT JOIN equipment ON temperature_statics.temperature_equipment_no = equipment.equipment_no WHERE division LIKE ? ORDER BY location ASC";
        } else {
            sql = "SELECT DISTINCT location FROM humidity_statics LEFT JOIN equipment ON humidity_statics.humidity_equipment_no = equipment.equipment_no WHERE division LIKE ? ORDER BY location ASC";
        }


        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + division + "%");

            rs = pstmt.executeQuery();

            List<String> locations = new ArrayList<String>();
            while(rs.next()) {
                locations.add(rs.getString("location"));
            }

            return locations;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public boolean findDuplicatedName(String name) {
        String sql = "SELECT COUNT(*) FROM equipment WHERE name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            rs.next();
            if(rs.getInt(1) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void saveEquipment(Equipment equipment) {
        String sql = "INSERT INTO equipment(name, location, division) VALUES (?, ?, ?);";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, equipment.getName());
            pstmt.setString(2, equipment.getLocation());
            pstmt.setString(3, equipment.getDivision());

            pstmt.executeQuery();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    public void updateEquipment(Equipment equipment) {
        String sql = "UPDATE equipment SET name=?, location=?, division=? WHERE name=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, equipment.getName());
            pstmt.setString(2, equipment.getLocation());
            pstmt.setString(3, equipment.getDivision());
            pstmt.setString(4, equipment.getId());

            pstmt.executeQuery();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }



    public void deleteEquipmentByName(String name) {
        String sql = "DELETE FROM equipment WHERE name=? ";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.executeQuery();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                {
                    rs.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn, PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

}

