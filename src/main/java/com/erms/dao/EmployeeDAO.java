package com.erms.dao;

import com.erms.database.DatabaseConnection;
import com.erms.model.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeDAO - Data Access Object for Employee CRUD operations.
 * All SQL queries use PreparedStatements to prevent SQL injection.
 */
public class EmployeeDAO {

    // ── CREATE ────────────────────────────────────────────────────────────

    /**
     * Inserts a new employee record and returns the generated ID.
     */
    public int create(Employee e) throws SQLException {
        String sql = """
            INSERT INTO employees
              (first_name, last_name, email, phone, department, position, salary, hire_date, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getPhone());
            ps.setString(5, e.getDepartment());
            ps.setString(6, e.getPosition());
            ps.setDouble(7, e.getSalary());
            ps.setDate(8, Date.valueOf(e.getHireDate()));
            ps.setString(9, e.getStatus());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    // ── READ ──────────────────────────────────────────────────────────────

    /** Returns all employees ordered by last name. */
    public List<Employee> findAll() throws SQLException {
        String sql = "SELECT * FROM employees ORDER BY last_name, first_name";
        List<Employee> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    /** Returns a single employee by ID, or null if not found. */
    public Employee findById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    /**
     * Full-text search across name, email, department, and position.
     * Uses LIKE with parameterised wildcards — still safe from injection.
     */
    public List<Employee> search(String keyword) throws SQLException {
        String sql = """
            SELECT * FROM employees
            WHERE first_name  LIKE ?
               OR last_name   LIKE ?
               OR email       LIKE ?
               OR department  LIKE ?
               OR position    LIKE ?
            ORDER BY last_name, first_name
            """;
        String pattern = "%" + keyword + "%";
        List<Employee> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 5; i++) ps.setString(i, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    /** Returns the total number of employees. */
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    /** Returns the count of employees grouped by department. */
    public List<String[]> countByDepartment() throws SQLException {
        String sql = "SELECT department, COUNT(*) AS cnt FROM employees GROUP BY department ORDER BY cnt DESC";
        List<String[]> result = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new String[]{rs.getString("department"), String.valueOf(rs.getInt("cnt"))});
            }
        }
        return result;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────

    /** Updates all editable fields of an existing employee. */
    public boolean update(Employee e) throws SQLException {
        String sql = """
            UPDATE employees SET
              first_name  = ?,
              last_name   = ?,
              email       = ?,
              phone       = ?,
              department  = ?,
              position    = ?,
              salary      = ?,
              hire_date   = ?,
              status      = ?
            WHERE id = ?
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getPhone());
            ps.setString(5, e.getDepartment());
            ps.setString(6, e.getPosition());
            ps.setDouble(7, e.getSalary());
            ps.setDate(8, Date.valueOf(e.getHireDate()));
            ps.setString(9, e.getStatus());
            ps.setInt(10, e.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────

    /** Hard-deletes an employee by ID. Returns true on success. */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── Mapping ───────────────────────────────────────────────────────────

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId(rs.getInt("id"));
        e.setFirstName(rs.getString("first_name"));
        e.setLastName(rs.getString("last_name"));
        e.setEmail(rs.getString("email"));
        e.setPhone(rs.getString("phone"));
        e.setDepartment(rs.getString("department"));
        e.setPosition(rs.getString("position"));
        e.setSalary(rs.getDouble("salary"));
        Date d = rs.getDate("hire_date");
        if (d != null) e.setHireDate(d.toLocalDate());
        e.setStatus(rs.getString("status"));
        return e;
    }
}
