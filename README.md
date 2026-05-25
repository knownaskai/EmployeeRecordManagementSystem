# 🏢 Employee Record Management System (ERMS)

A simple JavaFX desktop application for managing employee records with MySQL database integration.

## ✨ Features

* Login authentication
* Employee CRUD operations
* Search employees
* Dashboard statistics
* Role-based access (Admin/User)
* Secure password hashing with BCrypt

## 🛠 Tech Stack

* Java 17
* JavaFX 21
* MySQL
* Maven
* HikariCP
* BCrypt

## 🚀 Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/EmployeeRecordManagementSystem.git
cd EmployeeRecordManagementSystem
```

### 2. Setup Database

Run:

```sql
SOURCE sql/01_schema.sql;
SOURCE sql/02_sample_data.sql;
```

### 3. Configure `.env`

```env
DB_HOST=localhost
DB_PORT=3306
DB_NAME=erms_db
DB_USER=root
DB_PASSWORD=yourpassword
```

### 4. Run the App

```bash
mvn clean javafx:run
```

## 🔑 Default Login

| Username | Password   |
| -------- | ---------- |
| admin    | Admin@1234 |
| jsmith   | User@1234  |

## 📄 License

MIT License
