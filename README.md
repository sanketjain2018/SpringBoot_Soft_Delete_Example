# 🗑️ Spring Boot Soft Delete Example

A focused, real-world **Spring Boot MVC** application demonstrating **advanced soft-delete implementation** using **Hibernate 6** — built to show how enterprise applications handle "deletion" without ever losing data.

Rather than a basic CRUD demo, this project dives into the specific mechanics of soft delete: automatic filtering, restore workflows, and clean layered architecture around it.

![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Hibernate](https://img.shields.io/badge/Hibernate-6-59666C)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## 📋 Table of Contents

- [Why Soft Delete?](#-why-soft-delete)
- [Key Features](#-key-features)
- [Tech Stack](#-tech-stack)
- [How It Works](#-how-it-works)
- [Getting Started](#️-getting-started)
- [Screenshots](#-screenshots)
- [Learning Outcomes](#-learning-outcomes)
- [Roadmap](#-roadmap)

---

## 💡 Why Soft Delete?

In most production systems, permanently deleting a database row is risky — it breaks audit trails, referential integrity, and recovery options. **Soft delete** solves this by marking records as deleted (via a flag/timestamp) instead of physically removing them, while keeping the rest of the application completely unaware of the distinction.

This project demonstrates how to implement that pattern the *right* way in Hibernate 6 — declaratively, not with manual `WHERE deleted = false` clauses scattered across every query.

---

## 🔥 Key Features

- **Declarative soft delete** using Hibernate's `@SQLDelete` — overrides the physical `DELETE` SQL with an `UPDATE` that flips a `deleted` flag instead
- **Automatic query filtering** using `@SQLRestriction` — deleted records are transparently excluded from every query without touching business logic
- **Restore functionality** — deleted records aren't gone; they can be recovered through a dedicated restore action
- **Reusable Thymeleaf layout** — consistent navbar, header, and footer fragments across all pages
- **Custom error pages** — default Spring Whitelabel error page disabled in favor of branded 404/500 views
- **Responsive UI** — built with Bootstrap 5 for clean, mobile-friendly screens

---

## 🛠 Tech Stack

| Layer | Technologies |
|---|---|
| **Backend** | Java 17, Spring Boot 3.x, Spring MVC, Spring Data JPA |
| **Persistence** | Hibernate 6 (`@SQLDelete`, `@SQLRestriction`) |
| **Frontend** | Thymeleaf, Bootstrap 5 |
| **Database** | MySQL |
| **Build** | Maven |

---

## ⚙️ How It Works

```java
@SQLDelete(sql = "UPDATE items SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Entity
public class Item {
    // entity fields
}
```

- `@SQLDelete` intercepts Hibernate's generated `DELETE` statement and replaces it with an `UPDATE` that sets the `deleted` flag — so calling `repository.delete(item)` never actually removes the row.
- `@SQLRestriction` is applied at the entity level, so **every** query against this entity (finders, joins, custom JPQL) automatically excludes soft-deleted rows — no need to remember to filter manually anywhere in the codebase.
- A separate **restore endpoint** flips the flag back, making the record visible again without any data loss.

This keeps the soft-delete concern centralized in the entity definition rather than leaking into every repository/service method — which is the difference between a toy implementation and a production-ready one.

---

## ⚙️ Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.x

### Setup

```bash
# Clone the repository
git clone https://github.com/sanketjain2018/soft-delete-demo-springboot.git
cd soft-delete-demo-springboot

# Create the database
mysql -u root -p -e "CREATE DATABASE softdelete_demo;"

# Configure credentials in src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/softdelete_demo
spring.datasource.username=your_username
spring.datasource.password=your_password

# Build and run
mvn clean install
mvn spring-boot:run
```

The app will be available at `http://localhost:8080`.

---

## 📸 Screenshots

| | |
|---|---|
| ![Screenshot 1](https://github.com/user-attachments/assets/cc18aeed-2daa-4575-a9aa-dc86614d2c1d) | ![Screenshot 2](https://github.com/user-attachments/assets/cb4f5bdf-9002-44c1-9f55-60c835a43bed) |
| ![Screenshot 3](https://github.com/user-attachments/assets/496d8fe6-4605-43ae-913c-e84b541678b1) | ![Screenshot 4](https://github.com/user-attachments/assets/8d463833-7e30-4b12-89fb-0909ac3cc170) |

*(Add a one-line caption under each — e.g. "Item list with active records only," "Restore confirmation view" — captions make screenshots meaningfully scannable instead of just decorative.)*

---

## 📌 Learning Outcomes

Building this project reinforced:

- **Enterprise soft-delete patterns** — implementing deletion semantics that don't touch every layer of the app
- **Hibernate 6 annotation-driven behavior** — controlling generated SQL declaratively instead of overriding repository methods
- **MVC + Thymeleaf best practices** — reusable fragments, clean separation of view logic
- **Layered architecture discipline** — keeping controller/service/repository boundaries clean even in a small, focused project

---

## 🗺 Roadmap

- [ ] Add pagination to the active/deleted record views
- [ ] Add a scheduled hard-delete job for records soft-deleted beyond a retention period
- [ ] Unit tests covering delete/restore behavior with JUnit + Mockito
- [ ] REST API variant alongside the MVC views

---

⭐ **If you found this useful, consider starring the repo — it helps others discover it too.**

🔗 [View on GitHub](https://github.com/sanketjain2018/soft-delete-demo-springboot)
