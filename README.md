# 🏢 Employee Record Management System (ERMS)

A professional JavaFX desktop application for managing employee records, built with MVC architecture, MySQL database integration, and secure credential handling.

---

## 📋 Features

- **Multi-screen navigation** — Login, Dashboard, Employee Records, and Profile screens
- **Full CRUD operations** — Create, Read, Update, and Delete employee records
- **Search & filter** — Search across name, email, department, and position
- **Secure authentication** — BCrypt password hashing (work factor 12), no plain-text passwords
- **Environment-based config** — All secrets loaded from `.env` file, never hardcoded
- **Connection pooling** — HikariCP for efficient JDBC database connections
- **Role-based access** — ADMIN and USER roles
- **Dashboard statistics** — Total headcount and per-department breakdown

---

## 🛠 Technology Stack

| Layer        | Technology                     |
|--------------|-------------------------------|
| Language     | Java 17                        |
| GUI          | JavaFX 21                      |
| Architecture | MVC (Model-View-Controller)    |
| Database     | MySQL 8.x                      |
| JDBC Pooling | HikariCP 5.x                   |
| Security     | BCrypt (jbcrypt)               |
| Config       | dotenv-java                    |
| Build        | Apache Maven                   |

---

## 📁 Project Structure

```
EmployeeRecordManagementSystem/
├── src/
│   └── main/
│       ├── java/com/erms/
│       │   ├── MainApp.java              # Entry point & navigation
│       │   ├── config/
│       │   │   └── AppConfig.java        # Loads env variables
│       │   ├── database/
│       │   │   └── DatabaseConnection.java  # HikariCP pool
│       │   ├── model/
│       │   │   ├── Employee.java
│       │   │   └── User.java
│       │   ├── dao/
│       │   │   ├── EmployeeDAO.java      # CRUD for employees
│       │   │   └── UserDAO.java          # Auth & profile
│       │   ├── controller/
│       │   │   ├── LoginController.java
│       │   │   ├── DashboardController.java
│       │   │   ├── EmployeeRecordsController.java
│       │   │   └── ProfileController.java
│       │   └── util/
│       │       ├── SessionManager.java   # Logged-in user state
│       │       ├── PasswordUtil.java     # BCrypt helpers
│       │       ├── AlertUtil.java        # Dialog helpers
│       │       └── HashGenerator.java   # One-time hash tool
│       └── resources/
│           ├── fxml/
│           │   ├── LoginView.fxml
│           │   ├── DashboardView.fxml
│           │   ├── EmployeeRecordsView.fxml
│           │   └── ProfileView.fxml
│           └── css/
│               └── styles.css
├── sql/
│   ├── 01_schema.sql     # Database & table creation
│   └── 02_sample_data.sql # Sample users & employees
├── .env.example          # Template for environment variables
├── .gitignore
├── pom.xml
└── README.md
```

---

## ⚙️ Prerequisites

Make sure you have the following installed:

- **Java 17+** — [Download](https://adoptium.net/)
- **Apache Maven 3.8+** — [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.x** — [Download](https://dev.mysql.com/downloads/)
- **Git** — [Download](https://git-scm.com/)

---

## 🚀 Installation & Setup

### Step 1 — Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/EmployeeRecordManagementSystem.git
cd EmployeeRecordManagementSystem
```

### Step 2 — Set Up the Database

Open MySQL and run the scripts in order:

```sql
-- In MySQL Workbench or mysql CLI:
SOURCE sql/01_schema.sql;
SOURCE sql/02_sample_data.sql;
```

### Step 3 — Generate Real BCrypt Password Hashes

The sample data script ships with placeholder hashes. Generate real ones:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.erms.util.HashGenerator"
```

Copy the printed hashes and replace the `password_hash` values in `sql/02_sample_data.sql`, then re-run the data script.

### Step 4 — Configure Environment Variables

```bash
cp .env.example .env
```

Edit `.env` with your actual MySQL credentials:

```dotenv
DB_HOST=localhost
DB_PORT=3306
DB_NAME=erms_db
DB_USER=your_mysql_user
DB_PASSWORD=your_mysql_password
```

> ⚠️ **Never commit `.env` to Git.** It is listed in `.gitignore`.

### Step 5 — Build the Project

```bash
mvn clean package -DskipTests
```

### Step 6 — Run the Application

```bash
mvn javafx:run
```

Or run the packaged JAR:

```bash
java -jar target/EmployeeRecordManagementSystem-1.0.0.jar
```

---

## 🔐 Default Login Credentials

| Username | Password    | Role  |
|----------|-------------|-------|
| admin    | Admin@1234  | ADMIN |
| jsmith   | User@1234   | USER  |

---

## 🔒 Security Architecture

| Concern              | Implementation                                      |
|----------------------|-----------------------------------------------------|
| Password storage     | BCrypt hashes (work factor 12) in the database      |
| Credential loading   | `dotenv-java` reads `.env` at runtime               |
| No hardcoded secrets | `AppConfig.java` never contains literal credentials |
| SQL injection        | All queries use `PreparedStatement`                 |
| .env in Git          | Listed in `.gitignore` — never committed            |

---

## 📸 Screenshots

> *(Add your own screenshots here after running the application)*

| Screen            | Description                          |
|-------------------|--------------------------------------|
| Login             | Username / password authentication   |
| Dashboard         | Headcount stats and quick navigation |
| Employee Records  | Full CRUD table with search          |
| My Profile        | Update profile and change password   |

---

## 🤝 Contributing

Pull requests are welcome. Please open an issue first to discuss major changes.

---

## 📄 License

MIT License — see [LICENSE](LICENSE) for details.
