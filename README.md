# PhaseZero Catalogue Service 

A Spring Boot REST API for managing products. Built with clean code, proper error handling, and validation.

## Quick Start

### Prerequisites
- Java 17+
- Maven 4.0.0
- H2 in-memory database

### Why H2 Database?
- This project uses the H2 in-memory database because it is lightweight, fast, and requires no installation.
- It allows the application to run immediately without setting up an external database.
- H2 works seamlessly with Spring Boot and JPA, making it easy to test, develop, and verify APIs using the built-in H2 console.
- Since the assignment requires an in-memory storage option, H2 provides a clean and realistic way to persist data during runtime.

### Schema Management
- The schema is automatically created and updated by Hibernate based on the JPA entity definitions.
- No schema.sql file is needed, as Spring Boot generates the tables using the entity annotations at startup.

### View Database (Optional)
H2 Console: `http://localhost:8080/h2-console`
- URL: `jdbc:h2:mem:catalogdb`
- User: `sa`
- Password: (empty)

## API Endpoints

All endpoints use `/api/v1.0/products` prefix.

### 1. Create Product
**POST** `/api/v1.0/products`

```json
{
  "partNumber": "PART-001",
  "partName": "Hydraulic Filter",
  "category": "Filters",
  "price": 29.99,
  "stock": 100
}
```

**Returns:** 201 Created | 400 Bad Request | 409 Conflict (duplicate)

---

### 2. List All Products
**GET** `/api/v1.0/products?page=1&size=10&sort=id&desc=false`

**Returns:** 200 OK (array of products)

---

### 3. Search by Name
**GET** `/api/v1.0/products/search/filter`

Searches `partName` (case-insensitive, partial match).

**Returns:** 200 OK | 404 Not Found

---

### 4. Filter by Category
**GET** `/api/v1.0/products/filter/Filters`

**Returns:** 200 OK | 404 Not Found

---

### 5. Sort by Price
**GET** `/api/v1.0/products/sort/price`

Returns products sorted by price (ascending).

**Returns:** 200 OK | 404 Not Found

---

### 6. Total Inventory Value
**GET** `/api/v1.0/products/inventory/value`

Returns sum of (price × stock) for all products.

**Returns:** 200 OK (number)

## Project Structure

```
src/main/java/com/phaseZero/catalog_service/
├── Entity/
│   └── Products.java                         # Product model
├── productController/
│   └── productController.java                # REST endpoints
├── productService/
│   ├── productService.java                   # Service interface
│   └── productServiceImplementation.java     # Business logic
├── productRepository/
│   └── productRepository.java                # Data access (JPA)
└── exception/
    ├── GlobalExceptionHandler.java           # Error handling
    ├── ApiError.java                         # Error response format
    ├── DuplicateResourceException.java       # 409 error
    ├── InvalidInputException.java            # 400 error
    └── ResourceNotFoundException.java        # 404 error
```

## Data Model

| Field | Type | Rules |
|-------|------|-------|
| `id` | Long | Auto-generated |
| `partNumber` | String | Unique, required |
| `partName` | String | Required, stored as lowercase |
| `category` | String | Required |
| `price` | double | ≥ 0 |
| `stock` | int | ≥ 0 |

## Key Features

✅ **Validation** – Required fields, uniqueness, positive values  
✅ **Error Handling** – Centralized with typed exceptions  
✅ **Database** – H2 in-memory (auto-configured)  
✅ **Search** – Case-insensitive partial text matching  
✅ **Pagination** – With sorting support  
✅ **Clean Code** – Layered architecture, clear naming  

## Error Responses

All errors return JSON with `timestamp`, `status`, `error`, `message`, `errors` fields.

| HTTP Code | When |
|-----------|------|
| 200 | Success |
| 201 | Created |
| 400 | Invalid input |
| 404 | Not found |
| 409 | Duplicate |
| 500 | Server error |

## Future Enhancements

- Add update/delete endpoints
- Implement Spring Security
- Use persistent database (PostgreSQL)
- Add logging & monitoring
- Add unit & integration tests
- API documentation (Swagger)
