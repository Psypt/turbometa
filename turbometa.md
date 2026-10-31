# TurboMeta
## Technical Solution Proposal

**Document type:** Technical Architecture & Implementation Proposal  
**Project:** TurboMeta Web Application  
**Status:** Proposed Solution  
**Architecture:** Modular Monolith

---

## 1. Executive Summary

TurboMeta is a lightweight web application designed to provide a centralized platform for managing and searching turbocharger information.

The application will provide two distinct areas:

- **Backoffice:** Administrative interface for managing users and turbocharger records.
- **Frontoffice:** User-facing interface allowing authenticated users to search and consult turbocharger information.

The proposed solution prioritizes simplicity, maintainability, security and ease of deployment.

The application will be developed using Java and Spring Boot, with server-side HTML rendering and SQLite as its database.

The architecture deliberately avoids unnecessary infrastructure and technological complexity. The entire solution will be delivered as a single deployable application.

## 2. Project Objectives

The main objectives are:

1. Provide secure user authentication.
2. Implement role-based access control.
3. Allow administrators to manage application users.
4. Allow administrators to manage turbocharger records.
5. Provide an intuitive search interface for authenticated users.
6. Ensure responsive usability across desktop and mobile devices.
7. Keep deployment and operational requirements to a minimum.

## 3. Proposed Technology Stack

The following technologies are proposed:

| Component | Technology | Purpose |
|---|---|---|
| Programming Language | Java 25 | Application development |
| Backend Framework | Spring Boot 4.x | Application framework |
| Web Layer | Spring MVC | HTTP request handling |
| Frontend | Thymeleaf | Server-side HTML rendering |
| UI Framework | Bootstrap 5 | Responsive user interface |
| Database | SQLite | Relational data persistence |
| Data Access | Spring JDBC | Database operations |
| Authentication | Spring Security | Authentication and authorization |
| Password Hashing | BCrypt / Argon2 | Secure password storage |
| Database Migrations | Flyway | Database schema management |
| Build Tool | Maven | Dependency and build management |
| Deployment | Docker | Application packaging and deployment |

### Technology Selection Rationale

**Spring Boot**

Provides a mature application framework, dependency injection, configuration management, validation and security integration.

**Thymeleaf + Bootstrap**

Provides a straightforward approach to building responsive web interfaces without introducing the complexity of a separate JavaScript frontend framework.

**SQLite**

A lightweight relational database that requires no dedicated database server. Data is stored in a single persistent file.

**Spring Security**

Provides established authentication and authorization mechanisms, including session management and role-based access control.

**Spring JDBC**

Provides direct and lightweight database access without requiring a complex ORM layer. This is particularly suitable for the relatively simple data model and SQLite integration.

## 4. Solution Architecture

The proposed architecture follows a **Modular Monolith** approach.

All application components will run within a single Spring Boot application and share the same deployment lifecycle.

### 4.1 Logical Architecture

The application will consist of the following layers:

**Presentation Layer**
- Thymeleaf templates.
- Bootstrap components.
- HTML forms and tables.
- Optional HTMX integration for dynamic search interactions.

**Application Layer**
- Spring MVC controllers.
- Application services.
- Input validation.
- Business rules and authorization checks.

**Security Layer**
- Spring Security.
- Session-based authentication.
- Access control based on user privileges.
- Password hashing.

**Persistence Layer**
- Spring JDBC.
- Repository components.
- SQLite database.
- Flyway database migrations.

### 4.2 Application Modules

The application will be organized into two main functional modules.

**User Management Module**
- User authentication.
- User administration.
- Account activation and deactivation.
- User authorization.

**Turbo Management Module**
- Turbocharger registration.
- Turbocharger editing.
- Turbocharger deletion.
- Turbocharger search.

## 5. Database Design

The application will initially use SQLite with two primary tables.

### 5.1 User Table

Table: `app_user`

| Field | Data Type | Constraints |
|---|---|---|
| id | INTEGER | Primary Key, Auto Increment |
| username | VARCHAR(30) | NOT NULL |
| email | TEXT | UNIQUE, NOT NULL |
| password_hash | TEXT | NOT NULL |
| active | BOOLEAN | NOT NULL |
| is_admin | BOOLEAN | NOT NULL |

**Business Rules:**
- Email addresses must be valid and unique.
- Passwords must be securely hashed before persistence.
- Inactive users must not be allowed to authenticate.
- Administrators have access to the backoffice.
- Regular users have access to the frontoffice.

### 5.2 Turbo Table

Table: `turbo`

| Field | Data Type | Constraints |
|---|---|---|
| ref | TEXT | Primary Key |
| brand | TEXT | NOT NULL |
| model | TEXT | NOT NULL |
| geo | TEXT | Optional |

**Business Rules:**
- Each turbocharger must have a unique reference.
- Brand and model are mandatory.
- Geographic information is optional.
- Turbocharger records can only be modified by administrators.

### 5.3 Database Considerations

The database will be stored in a persistent file outside the application container.

Flyway will be used to version and manage database schema changes, ensuring consistent database initialization and upgrades.

## 6. Functional Requirements

### 6.1 Authentication

The application landing page will be the login screen.

**Features:**
- TurboMeta branding and logo.
- Email and password authentication.
- Input validation.
- Invalid credentials feedback.
- Logout functionality.
- Session management.

After successful authentication, users will be redirected according to their permissions:

- Administrators → Backoffice.
- Regular users → Frontoffice.

### 6.2 Backoffice – Administration

Only authenticated users with administrator privileges (`is_admin = true`) can access the backoffice.

#### 6.2.1 User Management

Administrators will be able to:

| Operation | Description |
|---|---|
| Search | Search users by username or email |
| Insert | Create new application users |
| Edit | Update user information and permissions |
| Remove | Delete user records |
| Activate/Deactivate | Enable or disable user accounts |

Additional considerations:
- Passwords will be hashed before storage.
- Email uniqueness will be enforced.
- Administrators will be able to assign or revoke administrative privileges.
- The application should prevent accidental deletion or deactivation of the last active administrator.

#### 6.2.2 Turbo Management

Administrators will be able to:

| Operation | Description |
|---|---|
| Search | Search by reference, brand or model |
| Insert | Register a new turbocharger |
| Edit | Update existing turbocharger information |
| Remove | Delete turbocharger records |

Search results will be displayed in a structured and paginated table.

### 6.3 Frontoffice – Turbo Search

Authenticated non-administrative users will have access to the turbocharger search functionality.

**Available functionality:**
- Search by reference.
- Search by brand.
- Search by model.
- Display matching turbocharger records.
- View available turbocharger details.

The frontoffice will be read-only. Users will not have permission to create, modify or delete records.

## 7. User Interface

The interface will follow a clean, modern and responsive design.

### 7.1 Login Page

- TurboMeta logo and branding.
- Email and password fields.
- Login action.
- Authentication error messages.

### 7.2 Backoffice

Proposed navigation:

- Dashboard.
- Users.
- Turbos.
- Logout.

The administration screens will provide:
- Search and filtering.
- Tabular data presentation.
- Create and edit forms.
- Confirmation before deletion.
- Clear validation and error messages.

### 7.3 Frontoffice

The main screen will focus on turbocharger search.

Users will be presented with:
- Search input.
- Search filters.
- Search results.
- Turbocharger reference, brand, model and geographic information.

## 8. Security Considerations

Security will be implemented using Spring Security.

The following measures will be included:

1. Password hashing using BCrypt or Argon2.
2. Session-based authentication.
3. Role-based authorization.
4. Protection of administrative endpoints.
5. CSRF protection for form submissions.
6. Server-side input validation.
7. Protection against SQL injection through parameterized database queries.
8. Secure session termination on logout.
9. Restriction of access for inactive accounts.
10. Prevention of unauthorized privilege escalation.

Authorization will always be enforced on the server side, independently of the interface presented to the user.

## 9. Deployment Architecture

The application will be packaged and deployed as a Docker container.

### 9.1 Deployment Model

The proposed deployment consists of:

- One Spring Boot application.
- One Docker container.
- One persistent SQLite database file.
- Optional reverse proxy for HTTPS termination.

### 9.2 Persistence

The SQLite database will be mounted using a Docker volume or persistent host directory.

This ensures that application redeployment or container replacement does not result in database data loss.

### 9.3 Deployment Advantages

- Simple deployment process.
- Minimal infrastructure requirements.
- No dedicated database server.
- Low operational overhead.
- Easy backup and restoration.
- Straightforward application upgrades.

## 10. Non-Functional Requirements

| Category | Requirement |
|---|---|
| Maintainability | Modular and clearly separated application components |
| Security | Authentication and authorization enforced server-side |
| Performance | Efficient database queries and paginated search results |
| Availability | Application availability dependent on hosting infrastructure |
| Usability | Responsive and intuitive user interface |
| Portability | Docker-based deployment |
| Scalability | Architecture allows future migration to PostgreSQL |
| Data Integrity | Database constraints and application-level validation |

SQLite is appropriate for the expected initial workload, particularly where write concurrency is limited. If application usage or concurrent write requirements increase substantially, PostgreSQL can be introduced as an alternative persistence platform.

## 11. Project Scope

### Included in Initial Delivery

- TurboMeta login page and branding.
- User authentication and session management.
- User administration (CRUD).
- Turbocharger administration (CRUD).
- Turbocharger search functionality.
- Role-based access control.
- Responsive user interface.
- SQLite database integration.
- Database migrations.
- Docker packaging and deployment configuration.

### Potential Future Enhancements

The architecture will allow additional functionality to be introduced without requiring a fundamental redesign.

Possible enhancements include:

- Password recovery.
- User registration workflows.
- Import/export of turbocharger records (CSV/Excel).
- Advanced search filters.
- Turbocharger images and attachments.
- Audit logs and administrative activity tracking.
- Additional user roles and permissions.
- API integration with external systems.
- Migration to PostgreSQL.

These enhancements are not included in the initial scope unless explicitly agreed.

## 12. Proposed Deliverables

The project delivery will include:

1. Complete application source code.
2. Maven project configuration.
3. SQLite database schema and Flyway migrations.
4. Authentication and authorization implementation.
5. Backoffice user management.
6. Backoffice turbocharger management.
7. Frontoffice turbocharger search.
8. Responsive user interface.
9. Dockerfile and deployment configuration.
10. Technical documentation covering configuration, deployment and maintenance.

## 13. Conclusion

The proposed TurboMeta solution provides a pragmatic and maintainable approach to delivering the required functionality.

By combining Java, Spring Boot, Thymeleaf, Bootstrap and SQLite, the application can provide all required capabilities without introducing unnecessary architectural or operational complexity.

The Modular Monolith approach offers a balance between clean separation of responsibilities, ease of development and straightforward deployment.

The solution is designed to meet the current requirements while retaining the flexibility to evolve as future business needs emerge.