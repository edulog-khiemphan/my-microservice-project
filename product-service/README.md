# Product Service

This is a Spring Boot microservice responsible for managing products.

## Features Implemented

- Product entity with ID, name and price
- Repository layer for database operations
- DTO (Data Transfer Object) for product responses
- Service layer with mock data implementation
- REST Controller with GET endpoint `/api/products/{id}`

## API Endpoints

### Get Product by ID
```
GET /api/products/{id}
```

**Response:**
```json
{
    "id": 1,
    "name": "Laptop",
    "price": 1200.0
}
```

**Example request:**
```
GET /api/products/1
```

**Example response:**
```json
{
    "id": 1,
    "name": "Laptop",
    "price": 1200.0
}
```

## Mock Data

The service includes mock data for demonstration purposes:
- Product 1: Laptop (1200.0)
- Product 2: Smartphone (800.0)
- Product 3: Tablet (500.0)

## Running the Service

To run this service, execute:
```bash
mvn spring-boot:run -pl product-service
```

The service will be available at http://localhost:8081

## Database

This service uses an in-memory H2 database for demonstration purposes. The H2 console is available at:
http://localhost:8081/h2-console