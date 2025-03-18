package com.grupo4.demo.model;

import com.grupo4.demo.model.db.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO {
    protected String CREATE_TABLE_SQL;

    public void createTableIfNotExists() {
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(CREATE_TABLE_SQL);
            System.out.println("Tabla creada o ya existente");

        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
