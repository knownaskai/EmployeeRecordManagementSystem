package com.erms.model;

import java.time.LocalDate;

/**
 * Employee - Domain model representing an employee record.
 */
public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private String position;
    private double salary;
    private LocalDate hireDate;
    private String status;   // ACTIVE | INACTIVE

    // ── Constructors ─────────────────────────────────────────────────────

    public Employee() {}

    public Employee(int id, String firstName, String lastName, String email,
                    String phone, String department, String position,
                    double salary, LocalDate hireDate, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.status = status;
    }

    // ── Convenience ───────────────────────────────────────────────────────

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name=" + getFullName() +
               ", dept=" + department + ", status=" + status + "}";
    }
}
