package com.grupo4.demo.model.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static HikariDataSource dataSource;

    static {
        try {
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar el pool de conexiones", e);
        }
    }

    private static void initialize() throws IOException {
        Properties props = new Properties();

        try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("No se pudo encontrar application.properties");
            }
            props.load(input);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(props.getProperty("spring.datasource.url"));
        config.setUsername(props.getProperty("spring.datasource.username"));
        config.setPassword(props.getProperty("spring.datasource.password"));
        config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.poolSize", "10")));

        // Configuración adicional
        config.setAutoCommit(true);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000); // 1 segundo

        // Propiedades de PostgreSQL
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    // Método para obtener información del pool para monitoreo
    public static String getPoolStats() {
        return String.format(
                "Pool Stats: Active=%d, Idle=%d, Total=%d, Waiting=%d",
                dataSource.getHikariPoolMXBean().getActiveConnections(),
                dataSource.getHikariPoolMXBean().getIdleConnections(),
                dataSource.getHikariPoolMXBean().getTotalConnections(),
                dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection()
        );
    }
}