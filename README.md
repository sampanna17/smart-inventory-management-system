<div align="center">
  <img src="https://raw.githubusercontent.com/sampanna17/smart-inventory-management-system/main/smart-inventory-system-frontend/public/SIMS_LOGO_ICON.png" alt="SIMS Logo" width="90"/>
  <h1>📦 Smart Inventory Management System (SIMS)</h1>
  <p>An enterprise-grade, full-stack inventory, sales, and supply chain management platform built with <strong>Spring Boot 3</strong> and <strong>Angular 21</strong>.</p>

  <p>
    <!-- Backend Badges -->
    <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
    <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" />
    <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security" />
    <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
    <img src="https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT" />
    <!-- Frontend Badges -->
    <img src="https://img.shields.io/badge/Angular-21.2-DD0031?style=for-the-badge&logo=angular&logoColor=white" alt="Angular" />
    <img src="https://img.shields.io/badge/Tailwind_CSS-4.1-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white" alt="Tailwind CSS" />
    <img src="https://img.shields.io/badge/TypeScript-5.9-3178C6?style=for-the-badge&logo=typescript&logoColor=white" alt="TypeScript" />
    <img src="https://img.shields.io/badge/Vitest-4.0-729B1B?style=for-the-badge&logo=vitest&logoColor=white" alt="Vitest" />
  </p>
</div>

---

## 📌 Table of Contents

- [✨ Overview](#-overview)
- [🏛 Architecture Overview](#-architecture-overview)
- [🚀 Key Features](#-key-features)
- [🛠️ Tech Stack](#️-tech-stack)
- [📂 Project Monorepo Structure](#-project-monorepo-structure)
- [🏃‍♂️ Getting Started](#️-getting-started)
  - [Prerequisites](#prerequisites)
  - [1. Backend Setup (Spring Boot)](#1-backend-setup-spring-boot)
  - [2. Frontend Setup (Angular)](#2-frontend-setup-angular)
- [🔐 Security & Role-Based Access Control (RBAC)](#-security--role-based-access-control-rbac)
- [📊 Analytics & Reporting](#-analytics--reporting)
- [🧪 Testing & Quality Assurance](#-testing--quality-assurance)
- [🤝 Contributing](#-contributing)
- [📄 License & Author](#-license--author)

---

## ✨ Overview

**Smart Inventory Management System (SIMS)** is a full-stack, enterprise-ready web application engineered to streamline end-to-end inventory, stock movement, purchase orders, sales transactions, customer management, and supplier relationships.

Built with a **Spring Boot 3 RESTful backend** and an **Angular 21 + Tailwind CSS v4 reactive frontend**, SIMS guarantees high performance, data consistency, robust security, and an intuitive user experience.

---

## 🏛 Architecture Overview

```mermaid
graph TD
    Client["Client Browser (Angular 21 + Tailwind CSS v4)"]
    API_Gateway["REST API / Reverse Proxy (Port 8080)"]
    AuthFilter["JWT Authentication & RBAC Filter"]
    Controllers["Spring Boot Controllers"]
    Services["Business Service Layer"]
    Repositories["Spring Data JPA / Hibernate"]
    Database[("MySQL Database")]

    Client -->|HTTP / JSON (Port 4200)| API_Gateway
    API_Gateway --> AuthFilter
    AuthFilter --> Controllers
    Controllers --> Services
    Services --> Repositories
    Repositories --> Database
```

---

## 🚀 Key Features

### 📊 1. Real-time Dashboard & Analytics
- **Admin Dashboard:** Overview of total products, net revenue, low stock alerts, top-selling items, and recent transactions.
- **Staff Dashboard:** Daily sales metrics, fast order processing, and available stock summaries.

### 📦 2. Product & Inventory Management
- Full CRUD capabilities for products, categories, SKU codes, and pricing tiers.
- Real-time stock level monitoring with custom minimum threshold alerts.
- Complete stock movement history (Stock In, Stock Out, Adjustments).

### 💳 3. Sales & Billing
- Fast sales transaction processing and automated invoice generation.
- Customer purchase history tracking and credit limit checks.

### 🚚 4. Purchase & Supplier Management
- Supplier catalog with contact details, order histories, and payment statuses.
- End-to-end purchase order lifecycle (Draft $\rightarrow$ Pending Approval $\rightarrow$ Received).

### 👥 5. Customer & User Management
- Dedicated customer profiles and ledger tracking.
- Role-based staff account management with instant password reset, activation, and permission assignments.

### 🔔 6. Notifications & Alerts
- Real-time alerts for low stock levels, out-of-stock items, and order status updates.

---

## 🛠️ Tech Stack

| Layer | Technology | Details |
|---|---|---|
| **Backend Framework** | Java 21 / Spring Boot 3.x | REST API, Spring Data JPA, Spring Security, Hibernate |
| **Backend Utilities** | Lombok, MapStruct, Maven | Clean code, type-safe entity-DTO mapping, dependency management |
| **Frontend Framework**| Angular 21 (v21.2) | Signals, Standalone Components, SSR / Pre-rendering support |
| **Frontend Styling**  | Tailwind CSS v4, @ng-icons | Utility-first styling, Heroicons, responsive design |
| **Database**          | MySQL 8.0+ | Relational data persistence with foreign keys & indexing |
| **Authentication**    | JWT (JSON Web Tokens) | Stateless auth, Bearer tokens, Role-Based Access Control |
| **Testing**           | JUnit 5, Mockito, Vitest | Comprehensive backend unit/integration & frontend unit tests |

---

## 📂 Project Monorepo Structure

```text
SIMS/
├── .gitignore                                 # Root Git ignore rules
├── README.md                                  # Unified Monorepo Documentation
│
├── smart-inventory-system-backend/            # Spring Boot REST API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/smartinventorysystem/
│   │   │   │   ├── config/                    # Security, CORS, OpenAPI configs
│   │   │   │   ├── modules/                   # Domain modules (auth, user, product,
│   │   │   │   │                              # inventory, sales, purchase, report)
│   │   │   │   │   ├── controller/            # REST API endpoints
│   │   │   │   │   ├── dto/                   # Request & Response DTOs
│   │   │   │   │   ├── entity/                # JPA Database Entities
│   │   │   │   │   ├── repository/            # Spring Data JPA Repositories
│   │   │   │   │   └── service/               # Business logic interfaces & impls
│   │   │   │   └── SmartInventoryApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties     # Database & App configurations
│   │   └── test/                              # Backend Unit & Integration tests
│   ├── pom.xml                                # Maven build configuration
│   └── mvnw / mvnw.cmd                        # Maven Wrapper
│
└── smart-inventory-system-frontend/           # Angular 21 Single Page Application
    ├── src/
    │   ├── app/
    │   │   ├── core/                          # Guards, HTTP Interceptors, Auth services
    │   │   ├── shared/                        # UI components, modals, pipes, models
    │   │   ├── features/                      # Feature views (dashboard, inventory,
    │   │   │                                  # sales, purchases, reports, suppliers)
    │   │   ├── app.config.ts                  # App routing & providers
    │   │   └── app.component.ts               # Root component
    │   ├── public/                            # Static assets (logos, icons)
    │   └── styles.css                         # Tailwind CSS imports & global styles
    ├── angular.json                           # Angular CLI configuration
    ├── package.json                           # Node dependencies & npm scripts
    └── tsconfig.json                          # TypeScript configuration
```

---

## 🏃‍♂️ Getting Started

### Prerequisites

Make sure you have the following installed on your machine:
- **Java Development Kit (JDK):** Version 21
- **Maven:** Version 3.8+ (or use the included `./mvnw`)
- **Node.js:** Version 20.x or higher
- **Package Manager:** `npm` or `yarn`
- **Database:** MySQL Server 8.0+

---

### 1. Backend Setup (Spring Boot)

1. **Navigate to the backend directory:**
   ```bash
   cd smart-inventory-system-backend
   ```

2. **Configure Database Connection:**
   Open `src/main/resources/application.properties` (or your local `.env`) and adjust your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/smart_inventory?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Build the project:**
   ```bash
   # Windows
   mvnw.cmd clean install -DskipTests

   # macOS / Linux
   ./mvnw clean install -DskipTests
   ```

4. **Run the backend server:**
   ```bash
   # Windows
   mvnw.cmd spring-boot:run

   # macOS / Linux
   ./mvnw spring-boot:run
   ```
   > 🚀 The backend API will be live at: **`http://localhost:8080`**

---

### 2. Frontend Setup (Angular)

1. **Navigate to the frontend directory:**
   ```bash
   cd smart-inventory-system-frontend
   ```

2. **Install frontend dependencies:**
   ```bash
   yarn install
   # or
   npm install
   ```

3. **Start the development server:**
   ```bash
   yarn start
   # or
   npm start
   # or
   ng serve
   ```
   > 🌐 The frontend application will be live at: **`http://localhost:4200`**

---

## 🔐 Security & Role-Based Access Control (RBAC)

The system utilizes stateless **JWT (JSON Web Token)** authentication with fine-grained role authorization:

| Role | Permissions & Access Scope |
|---|---|
| **ADMIN** | Full administrative access: user management, role assignment, system configurations, supplier controls, audit logs, and complete financial reports. |
| **STAFF** | Operational access: record daily sales, issue invoices, update received inventory, check stock quantities, and view assigned customer details. |

---

## 📊 Analytics & Reporting

SIMS includes built-in reporting and data export capabilities:
- 📈 **Daily & Monthly Sales Reports**
- 💰 **Revenue Breakdown & Margin Analytics**
- 📦 **Stock Valuation & Inventory Movement Reports**
- ⚠️ **Low Stock & Out-of-Stock Summary Reports**
- 🏆 **Best-Selling Products & Customer Activity Summaries**

---

## Testing & Quality Assurance

### Backend Tests (JUnit 5 & Mockito)
```bash
cd smart-inventory-system-backend
mvn test
```

### Frontend Unit Tests (Vitest)
```bash
cd smart-inventory-system-frontend
yarn test
# or
npm test
```

### Frontend Production Build
```bash
cd smart-inventory-system-frontend
yarn build
```

---

## Contributing

Contributions are welcome! Please follow these steps:

1. **Fork** the repository.
2. **Create** a feature branch (`git checkout -b feature/YourFeatureName`).
3. **Commit** your changes with clear messages (`git commit -m 'feat: Add YourFeatureName'`).
4. **Push** to your branch (`git push origin feature/YourFeatureName`).
5. **Open** a Pull Request against `main`.

---

## 📄 License & Author

- **Author:** [Sampanna Piya](https://github.com/sampanna17) — *Spring Boot & Angular Full-Stack Developer*
- **License:** Distributed under the **MIT License**. See `LICENSE` for details.

---

<div align="center">
  <sub>Built for modern, seamless inventory operations.</sub>
</div>
