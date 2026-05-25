package com.erms.database;

import com.erms.config.AppConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DatabaseConnection - Manages a HikariCP connection pool.
 *
 * Usage:
 *   try (Connection conn = DatabaseConnection.getConnection()) { ... }
 *
 * SECURITY: Credentials come from AppConfig (environment variables), never hardcoded.
 */
public class DatabaseConnection {

    private static HikariDataSource dataSource;

    static {
        initPool();
    }

    private static void initPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(AppConfig.getJdbcUrl());
        config.setUsername(AppConfig.getDbUser());
        config.setPassword(AppConfig.getDbPassword());
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Pool tuning
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30_000);   // 30 s
        config.setIdleTimeout(600_000);        // 10 min
        config.setMaxLifetime(1_800_000);      // 30 min
        config.setPoolName("ERMS-Pool");

        // Validation
        config.setConnectionTestQuery("SELECT 1");

        dataSource = new HikariDataSource(config);
    }

    /**
     * Returns a connection from the pool. Callers must close it (try-with-resources).
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes the pool cleanly. Call on application shutdown if needed.
     */
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
