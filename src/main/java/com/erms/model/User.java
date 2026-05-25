package com.erms.model;

/**
 * User - Domain model for application users (login accounts).
 * Passwords are stored as BCrypt hashes — never plain text.
 */
public class User {

    private int id;
    private String username;
    private String passwordHash;  // BCrypt hash, never the raw password
    private String fullName;
    private String email;
    private String role;          // ADMIN | USER
    private boolean active;

    // ── Constructors ─────────────────────────────────────────────────────

    public User() {}

    public User(int id, String username, String passwordHash,
                String fullName, String email, String role, boolean active) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.active = active;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
