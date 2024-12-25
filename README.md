# School Management System


**Année Universitaire**: 2024/2025  
**Encadré par**: Prof. Mohamed EL HADDAD  
**Réalisé par**: [Salmane Koraichi](#)

---

## 🎯 Project Description

This project is a robust **School Management System** designed to efficiently manage students, professors, modules, and enrollments. Developed using **Java**, **JavaFX**, and **JDBC**, it provides a user-friendly interface and ensures seamless database interactions.

---

## 🛠️ Features

### 1. **Student Management**
- Add, update, and delete student records.
- Search students by name, matriculation number, or promotion.
- Display student information, including:
  - Matricule, name, surname, date of birth, email, and promotion.
- View the modules a student is enrolled in.

### 2. **Professor Management**
- Add, update, and delete professor records.
- Search professors by name or specialty.
- Display professor details, such as:
  - ID, name, surname, specialty, and assigned modules.

### 3. **Module Management**
- Add, update, and delete modules.
- Assign professors to modules.
- Enroll students in modules.

### 4. **Enrollment Management**
- Enroll students in modules.
- Cancel enrollments.
- Display enrolled students in a module.

### 5. **Dashboard**
- View statistics, such as:
  - Total number of students, professors, and modules.
  - Most popular modules.
  - Professors teaching the most modules.

### 6. **User Management and Security**
- Login system with roles:
  - **Administrator**: Full access.
  - **Secretary**: Manage students and enrollments.
  - **Professor**: View modules and enrolled students.

---

## 💻 Technical Details

### **Database Structure**

- Tables include:
  - **Students**: ID, matricule, name, surname, date_of_birth, email, promotion.
  - **Professors**: ID, name, surname, specialty.
  - **Modules**: ID, name_module, code_module, professor_id.
  - **Enrollments**: ID, student_id, module_id, date_of_enrollment.
  - **Users**: ID, username, password, role.

### **Technologies Used**

- **Java**: Main programming language.
- **JavaFX**: User interface.
- **JDBC**: Database connection.
- **MySQL/PostgreSQL**: Database management.
- **Maven**: Dependency management.

---

## 🎨 User Interface (JavaFX)

### Main Screens
1. **Login Screen**:
   - Fields for username and password.
   - Redirect users based on roles.

2. **Dashboard**:
   - Display general statistics.
   - Access features via sidebar or tabs.

3. **Specific Screens**:
   - **Students**:
     - Add/update forms.
     - List of students using TableView.
   - **Professors**:
     - Manage details and assigned modules.
   - **Modules**:
     - Manage modules and related enrollments.
   - **Enrollments**:
     - Interface to link students to modules.
     - Display students enrolled in a module.

4. **Advanced Search**:
   - Multi-criteria search for students and professors.

5. **Data Export**:
   - Export lists of students, professors, or enrollments to CSV or PDF.

6. **Notifications**:
   - Alerts for unregistered students.
   - Reminders for important module dates.

7. **Multilingual Support**:
   - Switch between languages using resource files (e.g., English, French).

8. **REST API Integration**:
   - Exchange data with external systems.

---

## 🏗️ Architecture

### **MVC Design Pattern**

- **Model**:
  - Classes representing entities (Student, Professor, Module, etc.).
  - DAO (Data Access Object) for database interactions.
- **View**:
  - FXML files for defining UI.
  - Components like TableView, Forms, and Charts (for dashboard).
- **Controller**:
  - Handles user interactions and updates the model/view.

---

## 📅 Project Phases

1. **Design**:
   - UML diagrams for classes and database.
   - Define main interactions.
2. **Database Setup**:
   - Create necessary tables and relations (SQL).
   - Insert initial data.
3. **User Interface Development**:
   - Build FXML screens for each section.
   - Link views to controllers.
4. **Integration with JDBC**:
   - Implement DAOs for CRUD operations.
   - Connect controllers to DAOs.
5. **Finalization**:
   - Test all features.

---

## ✨ Deliverables

- A comprehensive application that manages core school operations.
- Enables students to:
  - Apply advanced relational data management.
  - Develop dynamic user interfaces.
  - Utilize professional tools (DAO, MVC).

---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/username/school-management-system.git
   ```
2. Import the project into your IDE.
3. Set up the database and update connection details in the configuration file.
4. Run the project.

---

## 🤝 Contribution

Contributions are welcome! Please fork the repository and submit a pull request.

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

## 📧 Contact

For inquiries, feel free to contact Salmane Koraichi: [Email](mailto:salmane@example.com).

---

_"Système de Gestion d'une École" — Simplify, Optimize, Achieve._
