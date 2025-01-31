
Peerless Bank Transfer Service
Overview
The Peerless Bank Transfer Service is a RESTful API built with Spring Boot for scheduling, retrieving, processing, and canceling bank transfers. It interacts with a MySQL database using JPA for persistence and provides endpoints for managing scheduled transfers.

Features
Schedule a transfer between accounts

Retrieve scheduled transfers for a sender account

Process due transfers

Cancel a scheduled transfer

Persistence using MySQL and JPA

Technologies Used
Java 17+

Spring Boot 3+

Spring Data JPA

MySQL

Lombok

Hibernate

Jakarta Persistence (JPA)

Installation & Setup
Prerequisites
Java 17+

Maven 3+

MySQL database

Clone the Repository

Configure the Database

Update the .env file with your database credentials:

DB_URL=jdbc:mysql://localhost:3306/peerless_bank
DB_USERNAME=root
DB_PASSWORD=yourpassword

Alternatively, update the application.properties file directly:
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

API Endpoints

Schedule a Transfer

Endpoint: POST /api/transfers/schedule
Request Body:

{
"senderAccountId": 1,
"recipientAccountId": 2,
"transferAmount": 500.00,
"transferDate": "2024-12-01T10:00:00"
}

Response:

{
"message": "Transfer scheduled successfully",
"transferId": 101
}

Retrieve Scheduled Transfers

Endpoint: GET /api/transfers/scheduled/{senderAccountId}
Response:

[
   {
   "transferId": 101,
   "senderAccountId": 1,
   "recipientAccountId": 2,
   "transferAmount": 500.00,
    "transferDate": "2024-12-01T10:00:00"
   }
]

Cancel a Scheduled Transfer

Endpoint: DELETE /api/transfers/cancel/{transferId}
Response:

{
"message": "Transfer cancelled successfully"
}

Process Due Transfers (Automatic Background Processing)

A scheduled job runs periodically to process due transfers based on transferDate.

