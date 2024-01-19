
# User Management Service

The User Management Service is a RESTful API that facilitates Implement a RESTful API with the following endpoints:
Create a new user
Retrieve user details by ID
Update user details by ID
Delete user by ID

## Overview

This service is built using Java and Spring Boot, providing a robust and scalable solution for user management. It utilizes MySQL as the relational database and Spring Data JPA for data access.

## MySQL Configuration

This project uses MySQL as the relational database. Below are the details of the MySQL configuration:

### Connection Details

- **Host:** `localhost`
- **Port:** `3306`
- **Database:** `your_database_name`
- **Username:** `your_database_username`
- **Password:** `your_database_password`

### Hibernate Configuration

Hibernate is used as the JPA provider with the following settings:

- **DDL Auto:** `update`
- **Show SQL:** `true`
- **Database Platform:** `org.hibernate.dialect.MySQLDialect`

## Spring Data JPA

Spring Data JPA is utilized for simplified data access using the Repository pattern. Entity classes and corresponding repositories are used for interacting with the database.

## Redis Configuration

This project also uses Redis for caching purposes. Below are the details of the Redis configuration:

### Connection Details

- **Host:** `localhost`
- **Port:** `6379`
- **Password:** [Your Redis Password, if applicable]

### Redis Client Configuration

The Redis client is configured with the following options:

- **Disconnected Behavior:** `REJECT_COMMANDS`
- **Command Timeout:** `2 seconds` (adjustable)

### Redis Template

A custom RedisTemplate named "customRedisTemplate" is created with the following settings:

- **Key Serialization:** String
- **Value Serialization:** GenericToStringSerializer for Objects

## API Reference

### Save User

Create a new user by providing user information.

- **Endpoint:** `POST /api/users`
- **Request Body:**
  {
    "name": "John Doe",
    "email": "john.doe@example.com"
  }
  ```
- **Response:**
  - Success (HTTP Status Code: 201 Created)
   
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com"
    }

  - Invalid User Data (HTTP Status Code: 400 Bad Request)
    
    {
      "message": "Invalid user data"
    }
   
  - Internal Server Error (HTTP Status Code: 500 Internal Server Error)
 
    {
      "message": "Error processing the request"
    }
  

### Get User by ID

Retrieve user details by providing the user ID.

- **Endpoint:** `GET /api/users/{id}`
- **Path Parameter:**
  - `id` (Long): User ID
- **Response:**
  - Success (HTTP Status Code: 200 OK)
   
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
  
  - User Not Found (HTTP Status Code: 404 Not Found)

    {
      "message": "User not found with ID: {id}"
    }
 

### Update User

Update user details by providing the user ID and updated information.

- **Endpoint:** `PUT /api/users/{id}`
- **Path Parameter:**
  - `id` (Long): User ID
- **Request Body:**

  {
    "name": "Updated Name",
    "email": "updated.email@example.com"
  }
  ```
- **Response:**
  - Success (HTTP Status Code: 200 OK)
    
    {
      "id": 1,
      "name": "Updated Name",
      "email": "updated.email@example.com"
    }
    ```
  - User Not Found (HTTP Status Code: 404 Not Found)
   
    {
      "message": "User not found with ID: {id}"
    }
    ```
  - Invalid User Data (HTTP Status Code: 400 Bad Request)
 
    {
      "message": "Invalid user data"
    }
    ```
  - Internal Server Error (HTTP Status Code: 500 Internal Server Error)
   
    {
      "message": "Internal Server Error"
    }
    ```

### Delete User

Delete a user by providing the user ID.

- **Endpoint:** `DELETE /api/users/{id}`
- **Path Parameter:**
  - `id` (Long): User ID
- **Response:**
  - Success (HTTP Status Code: 204 No Content)
  - User Not Found (HTTP Status Code: 404 Not Found)
   
    {
      "message": "User not found with ID: {id}"
    }
   

