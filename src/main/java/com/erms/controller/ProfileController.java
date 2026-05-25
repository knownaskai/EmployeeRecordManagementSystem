package com.erms.controller;

import com.erms.MainApp;
import com.erms.dao.UserDAO;
import com.erms.model.User;
import com.erms.util.AlertUtil;
import com.erms.util.PasswordUtil;
import com.erms.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * ProfileController - allows the logged-in user to view and update their
 * profile information and change their password.
 */
public class ProfileController {

    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;

    // Change password fields
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;

    private final UserDAO userDAO = new UserDAO();
    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = SessionManager.getInstance().getCurrentUser();
        usernameLabel.setText(currentUser.getUsername());
        roleLabel.setText(currentUser.getRole());
        fullNameField.setText(currentUser.getFullName());
        emailField.setText(currentUser.getEmail());
    }

    @FXML
    private void handleSaveProfile() {
        String fullName = fullNameField.getText().trim();
        String email    = emailField.getText().trim();

        if (fullName.isEmpty() || email.isEmpty()) {
            AlertUtil.showWarning("Validation", "Full name and email cannot be empty.");
            return;
        }

        try {
            userDAO.updateProfile(currentUser.getId(), fullName, email);
            // Update in-memory session object too
            currentUser.setFullName(fullName);
            currentUser.setEmail(email);
            AlertUtil.showInfo("Success", "Profile updated successfully.");
        } catch (Exception ex) {
            AlertUtil.showError("Update Error", ex.getMessage());
        }
    }

    @FXML
    private void handleChangePassword() {
        String current = currentPasswordField.getText();
        String newPwd  = newPasswordField.getText();
        String confirm = confirmPasswordField.getText();

        if (!PasswordUtil.checkPassword(current, currentUser.getPasswordHash())) {
            AlertUtil.showError("Error", "Current password is incorrect.");
            return;
        }
        if (newPwd.length() < 8) {
            AlertUtil.showWarning("Validation", "New password must be at least 8 characters.");
            return;
        }
        if (!newPwd.equals(confirm)) {
            AlertUtil.showWarning("Validation", "New passwords do not match.");
            return;
        }

        try {
            String newHash = PasswordUtil.hashPassword(newPwd);
            userDAO.updatePasswordHash(currentUser.getId(), newHash);
            // Update in-memory session hash so re-check in same session works
            currentUser.setPasswordHash(newHash);
            currentPasswordField.clear();
            newPasswordField.clear();
            confirmPasswordField.clear();
            AlertUtil.showInfo("Success", "Password changed successfully.");
        } catch (Exception ex) {
            AlertUtil.showError("Password Error", ex.getMessage());
        }
    }

    @FXML
    private void goToDashboard() {
        try {
            MainApp.showDashboard();
        } catch (Exception ex) {
            AlertUtil.showError("Navigation Error", ex.getMessage());
        }
    }
}
