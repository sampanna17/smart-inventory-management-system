# Smart Inventory and Sales Management System

A **Smart Inventory and Sales Management System (SIMS)** built using **Spring Boot** that helps businesses efficiently manage inventory, sales, purchases, suppliers, customers, and users. The system provides real-time inventory tracking, role-based access control, business analytics, and secure authentication.

---

## ✨ Features

### 👥 User Management
#### Admin
- Create, update, and delete staff accounts
- Assign roles and permissions
- Activate/Deactivate user accounts
- Reset staff passwords

#### Staff
- Secure Login & Logout
- Update profile
- Change password

---

### 📦 Product Management
#### Admin
- Add, update, and delete products
- Manage product categories
- Set product prices
- Upload product images

#### Staff
- View product information
- Update stock quantity (if permitted)

---

### 📊 Inventory Management
#### Admin
- Monitor inventory levels
- Configure minimum stock threshold
- View stock movement history
- Generate inventory reports

#### Staff
- Record stock received
- Record stock issued
- Update inventory quantities

---

### 💰 Sales Management
#### Admin
- View sales transactions
- Generate sales reports
- Analyze sales performance

#### Staff
- Create sales transactions
- Generate invoices
- Process customer purchases

---

### 🛒 Purchase Management
#### Admin
- Manage suppliers
- Create purchase orders
- Approve purchases
- View purchase history

#### Staff
- Record received stock
- View purchase orders

---

### 🚚 Supplier Management
#### Admin
- Add suppliers
- Update supplier information
- Delete suppliers
- View supplier history

#### Staff
- View supplier information

---

### 👤 Customer Management
#### Admin
- View customer database
- Generate customer reports

#### Staff
- Add customers
- Search customers
- View purchase history

---

### 📈 Reports & Analytics
- Daily Sales Report
- Monthly Sales Report
- Revenue Report
- Inventory Report
- Low Stock Report
- Best Selling Products Report
- Staff Activity Report

---

### 🔔 Notifications
- Low Stock Alerts
- Out of Stock Alerts
- Product Expiry Alerts *(Optional)*
- Sales Confirmation Notifications

---

### 📊 Dashboard

#### Admin Dashboard
- Total Products
- Total Sales
- Revenue Overview
- Low Stock Products
- Recent Transactions
- Top Selling Products

#### Staff Dashboard
- Today's Sales
- Recent Transactions
- Available Stock Summary

---

### 🔒 Security
- JWT Authentication
- Role-Based Access Control (RBAC)
- Session Management
- Audit Logs
- Password Encryption
- Backup & Restore

---

## 🛠️ Tech Stack

| Technology | Description |
|------------|-------------|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | ORM |
| Hibernate | Database ORM |
| MySQL | Database |
| Maven | Dependency Management |
| JWT | Authentication |
| Lombok | Boilerplate Reduction |
| Git & GitHub | Version Control |

---

## 📂 Project Structure

```text
src
├── main
│   ├── java
│   │   └── com
│   │       └── fonepay
│   │           └── smartinventory
│   │               ├── config
│   │               ├── controller
│   │               ├── dto
│   │               ├── entity
│   │               ├── exception
│   │               ├── repository
│   │               ├── security
│   │               ├── service
│   │               │   └── impl
│   │               ├── util
│   │               └── SmartInventoryApplication.java
│   └── resources
│       ├── application.properties
│       └── static
└── test
```

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/smart-inventory-system.git
```

### 2. Navigate to the project

```bash
cd smart-inventory-system
```

### 3. Configure MySQL

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_inventory
spring.datasource.username=root
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Build the project

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

---

## 📌 Functional Requirements

- Secure user authentication
- Role-based authorization
- Product management
- Inventory tracking
- Sales processing
- Invoice generation
- Purchase management
- Supplier management
- Customer management
- Reporting & analytics
- Low stock notifications
- Audit logging

---

## 🚀 Future Enhancements

- Barcode/QR Code Scanner
- Email Notifications
- SMS Alerts
- Product Expiry Management
- Multi-Branch Inventory
- Mobile Application
- AI-Based Demand Forecasting
- Cloud Deployment
- Real-Time Dashboard using WebSockets

---

## 📄 License

This project is developed for educational and learning purposes.

---

## 👨‍💻 Author

**Sampanna Piya**

Spring Boot Developer | Java Developer
