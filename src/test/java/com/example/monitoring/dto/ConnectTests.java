package com.example.monitoring.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectTests {
    @Test
    public void testConnection() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/monitoring",
                "root",
                "l2h5g9z0!"
        );
        Assertions.assertNotNull(connection);

        connection.close();
    }
}
