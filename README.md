# 📱 Mobile Banking API - Spring Boot + Spring Data JPA

A backend RESTful API for a mobile banking application, built using **Spring Boot**, **Spring Data JPA**, and **PostgresSQL**.

---

## 🚀 Project Overview

This API allows you to manage:

- ✅ Customers
- ✅ Account Types (e.g., Saving, Current)
- ✅ Bank Accounts
- ⏳ Transactions (Coming Soon)
- ⏳ Transaction Types (Coming Soon)

---

## 🛠 Technologies Used

- Java 21
- Spring Boot 3+
- Spring Data JPA
- PostgresSQl
- MapStruct (for DTO mapping)
- Jakarta Bean Validation
- Lombok

---


---

## ⚙️ API Endpoints

### 🔹 Customer (`/api/v1/customer`)

| Method | Endpoint                            | Description                         |
|--------|-------------------------------------|-------------------------------------|
| GET    | `/api/v1/customer`                  | Get all customers                   |
| GET    | `/api/v1/customer/r?phone=xxx`      | Find customer by phone number       |
| POST   | `/api/v1/customer`                  | Create a new customer               |
| PATCH  | `/api/v1/customer/update?phone=xxx`  | Update customer by phone number      |
| DELETE | `/api/v1/customer/delete?phone=xxx`  | Soft delete customer by phone number |

---

### 🔹 Account (`/api/v1/account`)

| Method | Endpoint                                      | Description                             |
|--------|-----------------------------------------------|-----------------------------------------|
| POST   | `/api/v1/account`                             | Create a new account                    |
| GET    | `/api/v1/account`                             | Get all active accounts                 |
| GET    | `/api/v1/account/accountNumber/{actNo}`       | Find account by account number          |
| GET    | `/api/v1/account/customerId/{custId}`         | Find accounts by customer ID            |
| PUT    | `/api/v1/account/update/{accountNumber}`      | Update account by account number        |
| DELETE | `/api/v1/account/delete/{accountNumber}`      | Soft delete account by account number   |
| PATCH  | `/api/v1/account/disable/{accountNumber}`     | Disable account (set `isDeleted = true`) |

---

### 🔹 Account Type (`/api/v1/accountTyp`)

| Method | Endpoint              | Description            |
|--------|-----------------------|------------------------|
| GET    | `/api/v1/accountType` | Get all account types  |

---

### 🔹 KYC (`/api/v1/kyc`)

| Method | Endpoint                   | Description            |
|--------|----------------------------|------------------------|
| GET    | `/api/v1/kyc/{customerId}` | verifyKycByCustomerId  |

---


