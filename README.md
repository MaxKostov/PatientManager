# PatientManager Repository Description

**PatientManager** is a robust application for managing patient care in a sanatorium. It allows users to register patients, discharge them, prescribe medications, and schedule procedures. The system supports role-based access, ensuring secure interactions, and includes a pharmacy module to efficiently track medication inventory.

## Features

1. **Patient Management**
    - Register new patients in the sanatorium.
    - Discharge patients when treatment is complete.
    - Maintain detailed patient records, including personal details and medical history.

2. **Treatment Management**
    - Prescribe medications tailored to individual patient needs.
    - Schedule and manage medical procedures efficiently.

3. **Role-Based Access Control**
    - Multiple user roles (e.g., doctors, nurses, and administrators) interact with patients in specific ways.
    - Role-specific permissions ensure secure and structured access to the system.

4. **Pharmacy Module**
    - Dedicated pharmacy management to track medication inventory.
    - Automatically updates stock levels after prescriptions.

5. **Database Migration**
    - Integrated with **Flyway** for seamless and versioned database migrations.

## Technology Stack
- **Backend**: Spring Boot
- **Database**: PostgreSQL
- **Security**: Spring Security for secure authentication and role-based access control
- **Database Migration Tool**: Flyway

## Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/YourUsername/PatientManager.git
   ```
2. Set up the environment variables for database connection and security configuration.
3. Apply database migrations using Flyway:
   ```bash
   ./mvnw flyway:migrate
   ```
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
5. Access the application at `http://localhost:8080`.
