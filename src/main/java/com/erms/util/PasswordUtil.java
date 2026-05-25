package com.erms.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * PasswordUtil - Wrapper around BCrypt for password hashing and verification.
 *
 * SECURITY:
 *  - Uses BCrypt with work factor 12 (strong, ~300 ms per hash on modern hardware).
 *  - hashPassword() is used when creating/updating passwords in the database.
 *  - checkPassword() is used during login — it is timing-safe by BCrypt design.
 */
public class PasswordUtil {

    /** Work factor for BCrypt. 12 is a good balance of security vs. speed. */
    private static final int WORK_FACTOR = 12;

    /**
     * Hashes a plain-text password using BCrypt.
     * @param plainText the raw password
     * @return the BCrypt hash to store in the database
     */
    public static String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(WORK_FACTOR));
    }

    /**
     * Verifies a plain-text password against a stored BCrypt hash.
     * @param plainText  the raw password the user typed
     * @param storedHash the hash retrieved from the database
     * @return true if the password matches
     */
    public static boolean checkPassword(String plainText, String storedHash) {
        if (plainText == null || storedHash == null) return false;
        return BCrypt.checkpw(plainText, storedHash);
    }
}
