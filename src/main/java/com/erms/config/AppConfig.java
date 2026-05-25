package com.erms.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * AppConfig - Loads all configuration from the .env file or system environment.
 *
 * SECURITY: No credentials are hardcoded here.
 * Sensitive values are read at runtime from environment variables.
 */
public class AppConfig {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()   // won't crash if .env is absent (CI/CD uses real env vars)
            .load();

    // ── Database ──────────────────────────────────────────────────────────
    public static String getDbHost() {
        return get("DB_HOST", "localhost");
    }

    public static String getDbPort() {
        return get("DB_PORT", "3306");
    }

    public static String getDbName() {
        return get("DB_NAME", "erms_db");
    }

    public static String getDbUser() {
        return get("DB_USER", "");
    }

    public static String getDbPassword() {
        return get("DB_PASSWORD", "");
    }

    public static String getJdbcUrl() {
        return String.format(
            "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
            getDbHost(), getDbPort(), getDbName()
        );
    }

    // ── Helper ────────────────────────────────────────────────────────────

    /**
     * Returns the value of the given key from .env or the system environment.
     * System environment variables take precedence over .env values.
     */
    private static String get(String key, String defaultValue) {
        String sysVal = System.getenv(key);
        if (sysVal != null && !sysVal.isBlank()) return sysVal;
        String dotVal = dotenv.get(key, null);
        return (dotVal != null && !dotVal.isBlank()) ? dotVal : defaultValue;
    }
}
