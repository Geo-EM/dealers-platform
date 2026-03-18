
---

# Dealers Platform — Backend Task

This project implements a **multi-tenant backend platform** for managing dealer inventory and payment transactions.

The system is built using **Spring Boot microservices** with **PostgreSQL** and includes JWT authentication, tenant isolation, filtering capabilities, and simulated payment processing.

---

# Services

The project contains two independent services.

## Inventory Service

Manages:

* Dealers
* Vehicles
* Vehicle filtering and queries
* Admin reporting

Runs on:

```
http://localhost:8081
```

Swagger UI:

```
http://localhost:8081/swagger-ui/index.html
```

---

## Payment Service

Simulates a **payment gateway** for dealer subscription payments.

Features:

* Payment initiation
* Idempotent request handling
* Asynchronous payment completion simulation

Runs on:

```
http://localhost:8082
```

Swagger UI:

```
http://localhost:8082/swagger-ui/index.html
```

---

# Technologies Used

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* Docker
* OpenAPI / Swagger
* Maven

---

# Project Structure

```
.
├── docker-compose.yaml
├── db-init
│   └── init.sql
│
├── inventory-service
│
├── payment-service
│
└── postman
    └── dealers-platform.postman_collection.json
    └── dealers-platform.postman_environment.json
```

---

# Quick Start

### 1. Start PostgreSQL

From the root directory:

```
docker compose up -d
```

PostgreSQL will run on:

```
localhost:5434
```

The following databases are automatically created:

```
inventory_db
payment_db
```

This is handled through:

```
db-init/init.sql
```

---

### 2. Run Inventory Service

```
cd inventory-service
./mvnw spring-boot:run
```

Service will start on:

```
http://localhost:8081
```

---

### 3. Run Payment Service

```
cd payment-service
./mvnw spring-boot:run
```

Service will start on:

```
http://localhost:8082
```

---

# Authentication & Multi-Tenancy

All API requests require:

```
Authorization: Bearer <JWT_TOKEN>
X-Tenant-Id: <tenant-id>
```

Rules enforced by the system:

| Case                            | Result          |
| ------------------------------- | --------------- |
| Missing `X-Tenant-Id`           | 400 Bad Request |
| Accessing another tenant's data | 403 Forbidden   |

---

# Inventory Service API

## Dealers

### Create Dealer

```
POST /dealers
```

Example request:

```json
{
  "name": "Dealer One",
  "email": "dealer@example.com",
  "subscriptionType": "BASIC"
}
```

---

### Get Dealer

```
GET /dealers/{id}
```

---

### List Dealers

```
GET /dealers
```

---

### Update Dealer

```
PATCH /dealers/{id}
```

---

### Delete Dealer

```
DELETE /dealers/{id}
```

---

# Vehicles

### Create Vehicle

```
POST /vehicles
```

Example request:

```json
{
  "dealer": {
    "id": "DEALER_ID"
  },
  "model": "Camry",
  "price": 30000,
  "status": "AVAILABLE"
}
```

---

### Get Vehicle

```
GET /vehicles/{id}
```

---

### Query Vehicles

Supports filtering.

Examples:

```
GET /vehicles?model=Camry
GET /vehicles?status=AVAILABLE
GET /vehicles?priceMin=10000&priceMax=50000
GET /vehicles?subscription=PREMIUM
```

Pagination:

```
?page=0&size=10
```

---

### Update Vehicle

```
PATCH /vehicles/{id}
```

---

### Delete Vehicle

```
DELETE /vehicles/{id}
```

---

# Admin Endpoint

```
GET /admin/dealers/countBySubscription
```

Returns the number of dealers grouped by subscription type.

#### It is per-tenant: X-Tenant-Id required


Example response:

```json
{
  "BASIC": 5,
  "PREMIUM": 3
}
```

Requires role:

```
GLOBAL_ADMIN
```

---

# Payment Service API

## Initiate Payment

```
POST /api/payment/initiate
```

Headers:

```
Idempotency-Key: unique-request-id
```

Example request:

```json
{
  "dealerId": "DEALER_ID",
  "amount": 100,
  "method": "CARD"
}
```

Example response:

```json
{
  "id": "PAYMENT_ID",
  "tenantId": "tenant1",
  "dealerId": "DEALER_ID",
  "amount": 100,
  "method": "CARD",
  "status": "PENDING"
}
```

---

## Get Payment Status

```
GET /api/payment/{id}
```

Example response:

```json
{
  "id": "PAYMENT_ID",
  "status": "SUCCESS"
}
```

---

# Idempotency

Payment initiation supports idempotent requests.

If the same request is sent with the same:

```
Idempotency-Key
```

The existing transaction will be returned instead of creating a new one.

---

# Async Payment Simulation

Payments are initially created with status:

```
PENDING
```

The system simulates payment processing and updates the status asynchronously to:

```
SUCCESS
```

after a short delay.

---

# Postman Collection

A ready-to-use Postman collection is included for testing all endpoints.

Location:

```
postman/dealers-platform.postman_collection.json
```

Import this file into Postman and run the requests to test the APIs.

---

# Author

George EM
Backend Developer — Java / Spring Boot

---

# Submission

This project was created as part of the **Dealer & Vehicle & Payment Gateway Microservices. Backend Developer Task**.

---
