package com.grupo4.demo.model;

import com.grupo4.demo.Util;
import com.grupo4.demo.model.db.ConnectionPool;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.grupo4.demo.Util.getConenctionId;

@Component
public class MessageDAO extends DAO {

    // Script SQL para crear la tabla
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS messages (
                id SERIAL PRIMARY KEY,
                userid INT NOT NULL,
                message VARCHAR(100) NOT NULL,
                createddate TIMESTAMPTZ NOT NULL DEFAULT NOW()
            );""";

    // Consultas CRUD
    private static final String INSERT_SQL = "INSERT INTO messages (userid, message) VALUES (?, ?) RETURNING id";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM messages WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT pg_sleep(3),* FROM messages";
    private static final String UPDATE_SQL = "UPDATE messages SET message = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM messages WHERE id = ?";

    public Message create(Message message) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            int userId = ThreadLocalRandom.current().nextInt(1, 4);
            ps.setLong(1, userId);
            ps.setString(2, message.getMessage());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    message.setId(rs.getLong(1));
                    return message;
                } else {
                    throw new SQLException("Error al obtener ID generado");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al crear mensaje: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Message> findMessage() {
        List<Message> messages = new ArrayList<>();

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            // TEST
            int connectionId = Util.getConenctionId(conn);
            System.out.println("connectionId: "+ connectionId);
            // END TEST

            while (rs.next()) {
                messages.add(new Message(
                        rs.getLong("id"),
                        rs.getLong("userid"),
                        rs.getString("message"),
                        rs.getObject("createddate", OffsetDateTime.class)
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar mensajes: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return messages;
    }


    public int getConenctionId(Connection conn) {
        int connectionId = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT pg_backend_pid()");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                connectionId = rs.getInt(1);
                System.out.println("ID de la conexión (PID): " + connectionId);
            }
        } catch (SQLException e) {
            System.err.println("getConnectionId error: "+e);
        }
        return connectionId;
    }
}
