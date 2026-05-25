package com.erms.controller;

import com.erms.MainApp;
import com.erms.dao.EmployeeDAO;
import com.erms.util.AlertUtil;
import com.erms.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

/**
 * DashboardController - shows summary statistics and navigation buttons.
 */
public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label totalEmployeesLabel;
    @FXML private Label departmentSummaryLabel;

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    public void initialize() {
        // Greet logged-in user
        String name = SessionManager.getInstance().getCurrentUser().getFullName();
        welcomeLabel.setText("Welcome back, " + name + "!");

        loadStats();
    }

    private void loadStats() {
        try {
            int total = employeeDAO.countAll();
            totalEmployeesLabel.setText(String.valueOf(total));

            List<String[]> depts = employeeDAO.countByDepartment();
            StringBuilder sb = new StringBuilder();
            for (String[] row : depts) {
                sb.append(row[0]).append(": ").append(row[1]).append("\n");
            }
            departmentSummaryLabel.setText(sb.toString().trim());

        } catch (Exception ex) {
            AlertUtil.showError("Dashboard Error", ex.getMessage());
        }
    }

    @FXML
    private void goToEmployeeRecords() {
        try {
            MainApp.showEmployeeRecords();
        } catch (Exception ex) {
            AlertUtil.showError("Navigation Error", ex.getMessage());
        }
    }

    @FXML
    private void goToProfile() {
        try {
            MainApp.showProfile();
        } catch (Exception ex) {
            AlertUtil.showError("Navigation Error", ex.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        boolean confirmed = AlertUtil.showConfirm("Logout", "Are you sure you want to log out?");
        if (confirmed) {
            SessionManager.getInstance().logout();
            try {
                MainApp.showLogin();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
