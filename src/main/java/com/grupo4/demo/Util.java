package com.grupo4.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Util {

    public static void showConenctionId(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT pg_backend_pid()");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int connectionId = rs.getInt(1);
                System.out.println("ID de la conexión (PID): " + connectionId);
            }
        } catch (SQLException e) {
            System.err.println("getConnectionId error: "+e);
        }
    }
}
