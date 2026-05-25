package com.erms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * MainApp - Entry point for the Employee Record Management System.
 * Loads the Login screen on startup.
 */
public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showLogin();
        stage.setTitle("Employee Record Management System");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Navigate to the Login screen.
     */
    public static void showLogin() throws Exception {
        Parent root = FXMLLoader.load(
            MainApp.class.getResource("/fxml/LoginView.fxml")
        );
        Scene scene = new Scene(root, 480, 520);
        scene.getStylesheets().add(
            MainApp.class.getResource("/css/styles.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.setTitle("ERMS - Login");
    }

    /**
     * Navigate to the Dashboard screen.
     */
    public static void showDashboard() throws Exception {
        Parent root = FXMLLoader.load(
            MainApp.class.getResource("/fxml/DashboardView.fxml")
        );
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(
            MainApp.class.getResource("/css/styles.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.setTitle("ERMS - Dashboard");
        primaryStage.setResizable(true);
    }

    /**
     * Navigate to the Employee Records screen.
     */
    public static void showEmployeeRecords() throws Exception {
        Parent root = FXMLLoader.load(
            MainApp.class.getResource("/fxml/EmployeeRecordsView.fxml")
        );
        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(
            MainApp.class.getResource("/css/styles.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.setTitle("ERMS - Employee Records");
        primaryStage.setResizable(true);
    }

    /**
     * Navigate to the Profile / Settings screen.
     */
    public static void showProfile() throws Exception {
        Parent root = FXMLLoader.load(
            MainApp.class.getResource("/fxml/ProfileView.fxml")
        );
        Scene scene = new Scene(root, 700, 520);
        scene.getStylesheets().add(
            MainApp.class.getResource("/css/styles.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.setTitle("ERMS - My Profile");
        primaryStage.setResizable(false);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
