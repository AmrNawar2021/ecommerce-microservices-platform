# E-Commerce Microservices Platform

## 📦 Overview

This project is a **real-world e-commerce backend** built using a microservices architecture. It demonstrates clean design, separation of concerns, role-based access control, and inter-service communication using REST and Kafka.

## 🏗️ Architecture

```
[User Service] <--> [Order Service] <--> [Product Service]
       |                                      |
       v                                      v
   PostgreSQL                             PostgreSQL
       |                                      |
       ------------------> Kafka <-------------
```

Each service runs in its own Docker container and communicates over a shared Docker network.

---

## ⚙️ Tech Stack

| Layer            | Technology                  |
| ---------------- | --------------------------- |
| Language         | Java 17                     |
| Framework        | Spring Boot                 |
| Security         | Spring Security + JWT       |
| Communication    | REST + WebClient            |
| Messaging Queue  | Apache Kafka                |
| Database         | PostgreSQL                  |
| Containerization | Docker + Docker Compose     |
| Documentation    | Springdoc OpenAPI (Swagger) |

---

## 🧠 Core Features

### User Service

* Register and login users
* Assign roles: `ROLE_ADMIN`, `ROLE_SELLER`, `ROLE_USER`
* JWT generation and validation

### Product Service

* Add, update, delete, and fetch products
* Product data protected by roles

### Order Service

* Create, update, delete, and retrieve orders
* Role-based order access:

  * `ROLE_USER` can manage only their orders
  * `ROLE_SELLER` can view all orders
  * `ROLE_ADMIN` has full access
* Integrates with Product Service to validate product stock

---

## 🔐 JWT Role Permissions

| Endpoint                  | USER | SELLER | ADMIN |
| ------------------------- | ---- | ------ | ----- |
| `POST /api/orders`        | ✅    | ✅      | ✅     |
| `GET /api/orders`         | ❌    | ✅      | ✅     |
| `GET /api/orders/{id}`    | ✅    | ✅      | ✅     |
| `PUT /api/orders/{id}`    | ❌    | ✅      | ✅     |
| `DELETE /api/orders/{id}` | ❌    | ❌      | ✅     |

> Note: `ROLE_USER` can only access their own orders.

---

## 🛠️ How to Run

### Prerequisites:

* Java 17+
* Docker + Docker Compose

### Steps:

```bash
git clone https://github.com/your-username/ecommerce-microservices-platform.git
cd ecommerce-microservices-platform
docker-compose up --build
```

### Access Swagger UI:

* Order Service: [http://localhost:8092/swagger-ui.html](http://localhost:8092/swagger-ui.html)
* Product Service: [http://localhost:8091/swagger-ui.html](http://localhost:8091/swagger-ui.html)
* User Service: [http://localhost:8090/swagger-ui.html](http://localhost:8090/swagger-ui.html)

---

## 🧪 Testing With Postman

1. Register/Login from User Service (`/api/auth/register`, `/api/auth/login`)
2. Copy the `accessToken`
3. In Postman, set Header:

   ```
   Authorization: Bearer <your_token>
   ```
4. Call any protected endpoints.

---

## ✍️ Author

* **Amr Fawzi**
* Java Backend Developer

---

## ✅ Future Enhancements

* Add Gateway Service with routing and centralized auth
* Add Notification Service via Kafka
* Enable Service Discovery (Eureka)
* Add Rate Limiting & Monitoring

---

> Built with ❤️ by Amr to demonstrate real-world microservices design using Spring Boot
