package com.erms.controller;

import com.erms.MainApp;
import com.erms.dao.UserDAO;
import com.erms.model.User;
import com.erms.util.AlertUtil;
import com.erms.util.PasswordUtil;
import com.erms.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


/**
 * LoginController - handles authentication logic for the Login screen.
 */
public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        errorLabel.setVisible(false);

        // Allow pressing Enter in the password field to trigger login
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) handleLogin();
        });
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

         FXMLLoader loader = new FXMLLoader(
                getClass().getResource("DashboardView.fxml")
        );

        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        try {
            User user = userDAO.findByUsername(username);

            if (user == null || !PasswordUtil.checkPassword(password, user.getPasswordHash())) {
                showError("Invalid username or password.");
                passwordField.clear();
                return;
            }

            // Successful login — save user in session and navigate
            SessionManager.getInstance().setCurrentUser(user);
            MainApp.showDashboard();

        } catch (Exception ex) {
            showError("Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
}
