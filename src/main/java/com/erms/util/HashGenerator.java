package com.erms.util;

/**
 * HashGenerator - One-time utility to generate BCrypt hashes for the SQL seed script.
 *
 * HOW TO USE:
 *   1. Run this class: mvn exec:java -Dexec.mainClass="com.erms.util.HashGenerator"
 *   2. Copy the printed hashes.
 *   3. Replace the password_hash values in sql/02_sample_data.sql with the copied hashes.
 *
 * You only need to run this ONCE during initial setup.
 */
public class HashGenerator {

    public static void main(String[] args) {
        // Define the plain-text passwords you want to hash
        String[][] accounts = {
            {"admin",  "Admin@1234"},
            {"jsmith", "User@1234"}
        };

        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║             BCrypt Hash Generator - ERMS                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        for (String[] acc : accounts) {
            String username  = acc[0];
            String plainText = acc[1];
            String hash      = PasswordUtil.hashPassword(plainText);

            System.out.println("Username  : " + username);
            System.out.println("Password  : " + plainText);
            System.out.println("BCrypt    : " + hash);
            System.out.println();
        }

        System.out.println("Copy the BCrypt values above into sql/02_sample_data.sql");
        System.out.println("Replace the password_hash column values for each user.");
    }
}
