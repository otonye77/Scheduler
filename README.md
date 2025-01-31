# Peerless Bank Transfer Service

## Overview
The Peerless Bank Transfer Service is a robust microservice that enables users to schedule, retrieve, and cancel fund transfers. It is built using **Spring Boot**, **Spring Data JPA**, and **Lombok**, ensuring efficient handling of scheduled transfers while maintaining high code maintainability.

## Features
- Schedule a fund transfer for a future date
- Retrieve all scheduled transfers for a specific sender
- Cancel a scheduled transfer before it is processed
- Automatically process due transfers

## Technologies Used
- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **H2/PostgreSQL** (for database operations)
- **Lombok** (to reduce boilerplate code)
- **Maven** (for dependency management)

## Project Structure
```
peerless_bank/
│── src/
│   ├── main/
│   │   ├── java/com/peerless/peerless_bank/
│   │   │   ├── Controller/TransferController.java
│   │   │   ├── Entities/Transfer.java
│   │   │   ├── Repository/TransferRepository.java
│   │   │   ├── Service/ITransferService.java
│   │   │   ├── Service/TransferService.java
│   ├── resources/
│   │   ├── application.properties
│── pom.xml
│── README.md
```

## Endpoints

### 1. Schedule a Transfer
**POST** `/api/transfers/schedule`
#### Request Body
```json
{
  "senderAccountId": 123456,
  "recipientAccountId": 789012,
  "transferAmount": 150.00,
  "transferDate": "2024-12-01T10:00:00"
}
```
#### Response
```json
"Transfer scheduled successfully"
```

### 2. Get Scheduled Transfers
**GET** `/api/transfers/scheduled/{senderAccountId}`
#### Response
```json
[
  {
    "transferId": 1,
    "senderAccountId": 123456,
    "recipientAccountId": 789012,
    "transferAmount": 150.00,
    "transferDate": "2024-12-01T10:00:00"
  }
]
```

### 3. Cancel a Scheduled Transfer
**DELETE** `/api/transfers/cancel/{transferId}`
#### Response
```json
"Transfer cancelled successfully"
```

## Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/peerless-bank.git
   cd peerless-bank
   ```
2. Configure your database in `application.properties` (default is H2 for testing, use PostgreSQL for production):
   ```properties
   spring.datasource.url=jdbc:h2:mem:peerless_bank
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   ```
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Access the API via Postman or any HTTP client.

## Future Enhancements
- Implement authentication and authorization using **Spring Security**
- Add real-time notifications for transfer status
- Support external API integration for executing real bank transfers

## License
This project is licensed under the MIT License.

---

### Author
**Peerless Bank Development Team**

