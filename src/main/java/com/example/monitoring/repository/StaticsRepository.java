package com.example.monitoring.repository;

import com.example.monitoring.domain.Temperature;
import com.example.monitoring.domain.TemperatureStaticsVO;
import lombok.Cleanup;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import javax.xml.transform.Result;
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

    public List<TemperatureStaticsVO> getTemperatureStatics(String location, String name, String startDate, String endDate) throws Exception {
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

         System.out.println(pstmt);

         rs = pstmt.executeQuery();

         List<TemperatureStaticsVO> temperatureStaticsVOList = new ArrayList<TemperatureStaticsVO>();
         while(rs.next()) {
             TemperatureStaticsVO temperatureStatic = new TemperatureStaticsVO(
                     rs.getString("name"),
                     Float.parseFloat(String.format("%.2f",rs.getFloat("AVG"))),
                     rs.getFloat("MAX"),
                     rs.getFloat("MIN"),
                     rs.getString("DATE"),
                     rs.getString("location")
             );
             temperatureStaticsVOList.add(temperatureStatic);
         }

        return temperatureStaticsVOList;
    }

    public List<TemperatureStaticsVO> getHumidityStatics(String location, String name, String startDate, String endDate) throws Exception {
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

        System.out.println(pstmt);

        rs = pstmt.executeQuery();

        List<TemperatureStaticsVO> temperatureStaticsVOList = new ArrayList<TemperatureStaticsVO>();
        while(rs.next()) {
            TemperatureStaticsVO temperatureStatic = new TemperatureStaticsVO(
                    rs.getString("name"),
                    Float.parseFloat(String.format("%.2f",rs.getFloat("AVG"))),
                    rs.getFloat("MAX"),
                    rs.getFloat("MIN"),
                    rs.getString("DATE"),
                    rs.getString("location")
            );
            temperatureStaticsVOList.add(temperatureStatic);
        }

        return temperatureStaticsVOList;
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

        System.out.println(pstmt);

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

        System.out.println(pstmt);

        rs = pstmt.executeQuery();

        List<String> equipments = new ArrayList<String>();
        while(rs.next()) {
            equipments.add(rs.getString("name"));
        }

        return equipments;
    }


    public List<Temperature> getDateTemperatures(String date) throws Exception{
        String sql = "SELECT * FROM temperature LEFT JOIN equipment ON temperature.equipment_no = equipment.equipment_no WHERE date LIKE ?";

        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement pstmt = null;
        @Cleanup ResultSet rs = null;

        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + date + "%");

        System.out.println(pstmt);

        rs = pstmt.executeQuery();

        List<Temperature> temperatures = new ArrayList<Temperature>();
        while(rs.next()) {
            Temperature temperature = new Temperature(
                    rs.getInt("temperature_no"),
                    rs.getInt("equipment_no"),
                    rs.getFloat("value"),
                    rs.getString("date")
            );
            temperatures.add(temperature);
        }

        return temperatures;
    }

    public List<Temperature> getDateHumidities(String date) throws Exception{
        String sql = "SELECT * FROM humidity LEFT JOIN equipment ON humidity.equipment_no = equipment.equipment_no WHERE date LIKE ?";

        @Cleanup Connection conn = null;
        @Cleanup PreparedStatement pstmt = null;
        @Cleanup ResultSet rs = null;

        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + date + "%");

        System.out.println(pstmt);

        rs = pstmt.executeQuery();

        List<Temperature> temperatures = new ArrayList<Temperature>();
        while(rs.next()) {
            Temperature temperature = new Temperature(
                    rs.getInt("humidity_no"),
                    rs.getInt("equipment_no"),
                    rs.getFloat("value"),
                    rs.getString("date")
            );
            temperatures.add(temperature);
        }

        return temperatures;
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
