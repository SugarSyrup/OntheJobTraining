package com.example.monitoring.repository;

import com.example.monitoring.domain.SensorValueVO;
import com.example.monitoring.domain.SensorStaticsValueVO;
import lombok.Cleanup;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaticsRepository implements IStaticsRepository{
    private final DataSource dataSource;

    public StaticsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<SensorStaticsValueVO> getTemperatureStatics(String location, String name, String startDate, String endDate) throws Exception {
        String sql = "SELECT * FROM temperature_statics LEFT JOIN equipment ON temperature_statics.temperature_equipment_no = equipment.equipment_no WHERE date >= ? AND date <= ? AND name LIKE ? AND location LIKE ?";

        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement pstmt = null;
        @Cleanup ResultSet rs = null;

         conn = getConnection();
         pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, startDate.equals("") ? "2000-01-01" : startDate);
        pstmt.setString(2, endDate.equals("") ? "2030-12-31" : endDate);
         pstmt.setString(3, "%" + name + "%");
         pstmt.setString(4, location.equals("NONE") ? "%%" : "%" + location + "%");

         rs = pstmt.executeQuery();

         List<SensorStaticsValueVO> sensorStaticsValueVOList = new ArrayList<SensorStaticsValueVO>();
         while(rs.next()) {
             SensorStaticsValueVO temperatureStatic = new SensorStaticsValueVO(
                     rs.getString("name"),
                     Float.parseFloat(String.format("%.2f",rs.getFloat("AVG"))),
                     rs.getFloat("MAX"),
                     rs.getFloat("MIN"),
                     rs.getString("DATE"),
                     rs.getString("location")
             );
             sensorStaticsValueVOList.add(temperatureStatic);
         }

        return sensorStaticsValueVOList;
    }

    public List<SensorStaticsValueVO> getHumidityStatics(String location, String name, String startDate, String endDate) throws Exception {
        String sql = "SELECT * FROM humidity_statics LEFT JOIN equipment ON humidity_statics.humidity_equipment_no = equipment.equipment_no WHERE date >= ? AND date <= ? AND name LIKE ? AND location LIKE ?";

        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement pstmt = null;
        @Cleanup ResultSet rs = null;

        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, startDate.equals("") ? "2000-01-01" : startDate);
        pstmt.setString(2, endDate.equals("") ? "2030-12-31" : endDate);
        pstmt.setString(3, "%" + name + "%");
        pstmt.setString(4, location.equals("NONE") ? "%%" : "%" + location + "%");

        rs = pstmt.executeQuery();

        List<SensorStaticsValueVO> sensorStaticsValueVOList = new ArrayList<SensorStaticsValueVO>();
        while(rs.next()) {
            SensorStaticsValueVO temperatureStatic = new SensorStaticsValueVO(
                    rs.getString("name"),
                    Float.parseFloat(String.format("%.2f",rs.getFloat("AVG"))),
                    rs.getFloat("MAX"),
                    rs.getFloat("MIN"),
                    rs.getString("DATE"),
                    rs.getString("location")
            );
            sensorStaticsValueVOList.add(temperatureStatic);
        }

        return sensorStaticsValueVOList;
    }


    public List<String> getTemperatureEquipmentsList(String location, String name, String startDate, String endDate) throws Exception{
        String sql = "SELECT DISTINCT equipment.name FROM temperature_statics LEFT JOIN equipment ON temperature_statics.temperature_equipment_no = equipment.equipment_no WHERE date >= ? AND date <= ? AND name LIKE ? AND location LIKE ?";

        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement pstmt = null;
        @Cleanup ResultSet rs = null;

        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, startDate.equals("") ? "2000-01-01" : startDate);
        pstmt.setString(2, endDate.equals("") ? "2030-12-31" : endDate);
        pstmt.setString(3, "%" + name + "%");
        pstmt.setString(4, location.equals("NONE") ? "%%" : "%" + location + "%");

        rs = pstmt.executeQuery();

        List<String> equipments = new ArrayList<String>();
        while(rs.next()) {
            equipments.add(rs.getString("name"));
        }

        return equipments;

    }

    public List<String> getHumidityEquipmentsList(String location, String name, String startDate, String endDate) throws Exception{
        String sql = "SELECT DISTINCT equipment.name FROM humidity_statics LEFT JOIN equipment ON humidity_statics.humidity_equipment_no = equipment.equipment_no WHERE date >= ? AND date <= ? AND name LIKE ? AND location LIKE ?";

        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement pstmt = null;
        @Cleanup ResultSet rs = null;

        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, startDate.equals("") ? "2000-01-01" : startDate);
        pstmt.setString(2, endDate.equals("") ? "2030-12-31" : endDate);
        pstmt.setString(3, "%" + name + "%");
        pstmt.setString(4, location.equals("NONE") ? "%%" : "%" + location + "%");

        rs = pstmt.executeQuery();

        List<String> equipments = new ArrayList<String>();
        while(rs.next()) {
            equipments.add(rs.getString("name"));
        }

        return equipments;
    }


    public List<SensorValueVO> getDateTemperatures(String date, String name) throws Exception{
        String sql = "SELECT * FROM temperature LEFT JOIN equipment ON temperature.equipment_no = equipment.equipment_no WHERE date LIKE ? AND equipment.name LIKE ?";

        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement pstmt = null;
        @Cleanup ResultSet rs = null;

        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + date + "%");
        pstmt.setString(2, "%" + name + "%");

        rs = pstmt.executeQuery();

        List<SensorValueVO> sensorValueVOS = new ArrayList<SensorValueVO>();
        while(rs.next()) {
            SensorValueVO sensorValueVO = new SensorValueVO(
                    rs.getInt("temperature_no"),
                    rs.getInt("equipment_no"),
                    rs.getFloat("value"),
                    rs.getString("date")
            );
            sensorValueVOS.add(sensorValueVO);
        }

        return sensorValueVOS;
    }

    public List<SensorValueVO> getDateHumidities(String date, String name) throws Exception{
        String sql = "SELECT * FROM humidity LEFT JOIN equipment ON humidity.equipment_no = equipment.equipment_no WHERE date LIKE ? AND equipment.name LIKE ?";

        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement pstmt = null;
        @Cleanup ResultSet rs = null;

        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + date + "%");
        pstmt.setString(2, "%" + name + "%");

        rs = pstmt.executeQuery();

        List<SensorValueVO> sensorValueVOS = new ArrayList<SensorValueVO>();
        while(rs.next()) {
            SensorValueVO sensorValueVO = new SensorValueVO(
                    rs.getInt("humidity_no"),
                    rs.getInt("equipment_no"),
                    rs.getFloat("value"),
                    rs.getString("date")
            );
            sensorValueVOS.add(sensorValueVO);
        }

        return sensorValueVOS;
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
