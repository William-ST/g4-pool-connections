package com.grupo4.demo.controller;

import com.grupo4.demo.model.TestRequest;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    public TestController() {

    }

    @PostMapping
    public ResponseEntity<String> test(@RequestBody TestRequest request) {
        System.out.println(">>> Test is called request: "+request);
        // Configuración de HikariCP
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(request.getJdbcUrl());  // Cambia 'db' por el nombre de tu contenedor de PostgreSQL
        config.setUsername(request.getUsername());
        config.setPassword(request.getPassword());
        config.setMaximumPoolSize(10);  // Configura el tamaño máximo del pool de conexiones

        // Crear un DataSource
        HikariDataSource dataSource = new HikariDataSource(config);
        String result = "";
        System.out.println(">>> Test result 1: "+result);
        // Probar la conexión
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null) {
                result = "¡Conexión exitosa a la base de datos PostgreSQL!";
            } else {
                result = "No se pudo conectar a la base de datos.";
            }
            System.out.println(">>> Test result 2: "+result);
        } catch (SQLException e) {
            result = "Error al intentar conectar a la base de datos: " + e.getMessage();
        } finally {
            dataSource.close();
        }
        System.out.println(">>> Test result 3: "+result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


}
