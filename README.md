# E-commerce-spring-shop-backend

A comprehensive Java-based e-commerce backend system supporting multi-role user management, product catalog, and transaction processing.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [User Roles](#user-roles)
- [Database Schema](#database-schema)
- [Security](#security)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

## Overview

This e-commerce backend provides a robust foundation for online marketplace applications. It implements a multi-tenant architecture supporting different user roles with appropriate permissions, secure transaction processing, and comprehensive product management capabilities.

## Features

### Core Functionality
- **User Management**: Registration, authentication, and profile management
- **Product Management**: CRUD operations for products with categories, inventory tracking
- **Order Processing**: Complete order lifecycle from cart to fulfillment
- **Payment Integration**: Secure payment processing and transaction management
- **Multi-Role Support**: Buyer, Seller, and Admin role-based access control

## Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controllers   │    │    Services     │    │   Repositories  │
│   (REST API)    │──→ │  (Business      │──→ │   (Data Access) │
│                 │    │   Logic)        │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Security      │    │   DTOs/Models   │    │    Database     │
│   (JWT)         │    │   (Entities)    │    │   (MySQL)       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## Technology Stack

- **Framework**: Spring Boot 3.x
- **Security**: Spring Security with JWT Authentication
- **Database**: MySQL
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven

## Prerequisites

- Java 19
- Maven 3.8+
- MySQL 8.0+

## Installation

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/vuong-ng/E-commerce-spring-shop.git
   cd src
   ```

3. **Configure Environment Variables**
   ```bash
   cp .env.example .env
   # Edit .env with your database credentials
   ```

4. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Configuration

### Application Properties

Key configuration properties in `pom.xml`:

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_USERNAME` | Database username | `ecommerce_user` |
| `DB_PASSWORD` | Database password | `password` |
| `JWT_SECRET` | JWT signing secret | `secret-key` |
| `DB_HOST` | MySQL host | `localhost` |
| `DB_PORT` | MySQL port | `3306` |

## API Documentation

### Authentication Endpoints

```http
POST /api/v1/auth/register
POST /api/v1/auth/login
POST /api/v1/auth/logout
```

### User Management

```http
GET    /api/v1/users/{userId}/user
PUT    /api/v1/users/{userId}/update
DELETE /api/users/{userId}/delete
```

### Product Management

```http
GET    /api/v1/products/all              # List products
POST   /api/v1/products/add              # Create product (Seller/Admin)
GET    /api/v1/products/{id}/product         # Get product details
PUT    /api/v1/products/{id}/update         # Update product (Seller/Admin)
DELETE /api/v1/1products/{id}/delete         # Delete product (Seller/Admin)
```

### Order Management

```http
GET    /api/orders/order?userId={userId}              # List user orders
POST   /api/orders/api/orders/order?userId={userId}   # Place order
```


For complete API documentation, visit codebase.

## User Roles

### Buyer
- Browse and search products
- Add products to cart
- Place and track orders
- Manage profile and payment methods

### Seller
- Manage product inventory
- Track inventory
- Process orders
- View sales analytics
- Manage seller profile

### Admin
- Full system access
- User management
- Order management

## Database Schema

### Core Entities

```sql
Users
├── id (Primary Key)
├── email (Unique)
├── password_hash
├── role (BUYER, SELLER, ADMIN)
├── created_at
└── updated_at

Products
├── id (Primary Key)
├── user_id (Foreign Key)
├── name
├── description
├── price
├── inventory
├── category_id
└── created_at

Orders
├── id (Primary Key)
├── user_id (Foreign Key)
├── total_amount
├── status
├── created_at
└── updated_at

Order_Items
├── id (Primary Key)
├── order_id (Foreign Key)
├── product_id (Foreign Key)
├── quantity
└── unit_price
```

## Security

### Authentication
- JWT-based stateless authentication
- Role-based access control (RBAC)
- Password encryption using BCrypt
- Token-based session management

### JWT Configuration
- Refresh token support
- Secure token signing with HMAC SHA-256
- Token validation on protected endpoints

### Authorization
- Method-level security with `@PreAuthorize`
- Role-based endpoint access
- Resource ownership validation

## Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run integration tests
mvn verify -Pintegration-tests

# Run with coverage
mvn test jacoco:report
```

---
**Maintainer**: Vuong Nguyen (nguyenxuanvuong107003@gmail.com)