package com.mathetiscore.api.infraestructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testDataBaseConnection() throws SQLException {
        assertNotNull(dataSource, "El DataSource no debe ser nulo");

        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "El connection no debe ser nulo");
            assertTrue(connection.isValid(2), "La conexión debe responder en menos de 2 segundos");
            System.out.println("Conexión exitosa a Supabase!");
        }
    }
}
