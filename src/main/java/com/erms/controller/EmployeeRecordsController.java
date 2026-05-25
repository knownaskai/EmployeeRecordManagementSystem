package com.erms.controller;

import com.erms.MainApp;
import com.erms.dao.EmployeeDAO;
import com.erms.model.Employee;
import com.erms.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * EmployeeRecordsController - manages the employee list and the add/edit form panel.
 */
public class EmployeeRecordsController {

    // ── Table ─────────────────────────────────────────────────────────────
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer>   colId;
    @FXML private TableColumn<Employee, String>    colFirstName;
    @FXML private TableColumn<Employee, String>    colLastName;
    @FXML private TableColumn<Employee, String>    colDepartment;
    @FXML private TableColumn<Employee, String>    colPosition;
    @FXML private TableColumn<Employee, Double>    colSalary;
    @FXML private TableColumn<Employee, String>    colStatus;
    @FXML private TextField searchField;

    // ── Form ──────────────────────────────────────────────────────────────
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> departmentBox;
    @FXML private TextField positionField;
    @FXML private TextField salaryField;
    @FXML private DatePicker hireDatePicker;
    @FXML private ComboBox<String> statusBox;
    @FXML private Label formTitleLabel;
    @FXML private Button saveButton;

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private Employee selectedEmployee = null;  // null = adding new

    @FXML
    public void initialize() {
        setupColumns();
        setupComboBoxes();
        loadEmployees();

        // When a row is selected, populate the form
        employeeTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> populateForm(newVal)
        );
    }

    // ── Setup ─────────────────────────────────────────────────────────────

    private void setupColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        employeeTable.setItems(employeeData);
    }

    private void setupComboBoxes() {
        departmentBox.setItems(FXCollections.observableArrayList(
            "Engineering", "Marketing", "Human Resources",
            "Finance", "Sales", "Operations", "IT", "Legal"
        ));
        statusBox.setItems(FXCollections.observableArrayList("ACTIVE", "INACTIVE"));
        statusBox.setValue("ACTIVE");
    }

    // ── Data loading ──────────────────────────────────────────────────────

    private void loadEmployees() {
        try {
            List<Employee> list = employeeDAO.findAll();
            employeeData.setAll(list);
        } catch (Exception ex) {
            AlertUtil.showError("Load Error", ex.getMessage());
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().trim();
        try {
            List<Employee> results = keyword.isEmpty()
                ? employeeDAO.findAll()
                : employeeDAO.search(keyword);
            employeeData.setAll(results);
        } catch (Exception ex) {
            AlertUtil.showError("Search Error", ex.getMessage());
        }
    }

    // ── Form actions ──────────────────────────────────────────────────────

    /** Called when the Save button is clicked. Routes to create or update. */
    @FXML
    private void handleSave() {
        if (!validateForm()) return;

        try {
            Employee e = buildEmployeeFromForm();
            if (selectedEmployee == null) {
                employeeDAO.create(e);
                AlertUtil.showInfo("Success", "Employee added successfully.");
            } else {
                e.setId(selectedEmployee.getId());
                employeeDAO.update(e);
                AlertUtil.showInfo("Success", "Employee updated successfully.");
            }
            clearForm();
            loadEmployees();
        } catch (Exception ex) {
            AlertUtil.showError("Save Error", ex.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showWarning("No Selection", "Please select an employee to delete.");
            return;
        }
        boolean confirmed = AlertUtil.showConfirm("Delete Employee",
            "Delete " + selected.getFullName() + "? This cannot be undone.");
        if (confirmed) {
            try {
                employeeDAO.delete(selected.getId());
                loadEmployees();
                clearForm();
                AlertUtil.showInfo("Deleted", "Employee deleted successfully.");
            } catch (Exception ex) {
                AlertUtil.showError("Delete Error", ex.getMessage());
            }
        }
    }

    @FXML
    private void handleNewEmployee() {
        clearForm();
        formTitleLabel.setText("Add New Employee");
        firstNameField.requestFocus();
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    // ── Navigation ────────────────────────────────────────────────────────

    @FXML
    private void goToDashboard() {
        try {
            MainApp.showDashboard();
        } catch (Exception ex) {
            AlertUtil.showError("Navigation Error", ex.getMessage());
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    private void populateForm(Employee e) {
        if (e == null) return;
        selectedEmployee = e;
        formTitleLabel.setText("Edit Employee #" + e.getId());
        firstNameField.setText(e.getFirstName());
        lastNameField.setText(e.getLastName());
        emailField.setText(e.getEmail());
        phoneField.setText(e.getPhone());
        departmentBox.setValue(e.getDepartment());
        positionField.setText(e.getPosition());
        salaryField.setText(String.valueOf(e.getSalary()));
        hireDatePicker.setValue(e.getHireDate());
        statusBox.setValue(e.getStatus());
    }

    private void clearForm() {
        selectedEmployee = null;
        formTitleLabel.setText("Add New Employee");
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        departmentBox.setValue(null);
        positionField.clear();
        salaryField.clear();
        hireDatePicker.setValue(null);
        statusBox.setValue("ACTIVE");
        employeeTable.getSelectionModel().clearSelection();
    }

    private Employee buildEmployeeFromForm() {
        Employee e = new Employee();
        e.setFirstName(firstNameField.getText().trim());
        e.setLastName(lastNameField.getText().trim());
        e.setEmail(emailField.getText().trim());
        e.setPhone(phoneField.getText().trim());
        e.setDepartment(departmentBox.getValue());
        e.setPosition(positionField.getText().trim());
        e.setSalary(Double.parseDouble(salaryField.getText().trim()));
        e.setHireDate(hireDatePicker.getValue() != null
            ? hireDatePicker.getValue() : LocalDate.now());
        e.setStatus(statusBox.getValue());
        return e;
    }

    private boolean validateForm() {
        if (firstNameField.getText().trim().isEmpty()) {
            AlertUtil.showWarning("Validation", "First name is required.");
            return false;
        }
        if (lastNameField.getText().trim().isEmpty()) {
            AlertUtil.showWarning("Validation", "Last name is required.");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            AlertUtil.showWarning("Validation", "Email is required.");
            return false;
        }
        if (departmentBox.getValue() == null) {
            AlertUtil.showWarning("Validation", "Please select a department.");
            return false;
        }
        try {
            double salary = Double.parseDouble(salaryField.getText().trim());
            if (salary < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            AlertUtil.showWarning("Validation", "Salary must be a valid positive number.");
            return false;
        }
        return true;
    }
}
