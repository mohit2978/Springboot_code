# 05exceptionHandling

This project demonstrates comprehensive and production-ready exception handling patterns in Spring Boot 3.

## Key Concepts Demonstrated

1. **Global Exception Handling (`@RestControllerAdvice` + `@ExceptionHandler`)**: Centralizes exception processing and converts uncaught exceptions into clean, uniform HTTP error responses.
2. **Standard API Error Response (`ApiErrorResponse`)**: Standardized JSON structure across the entire application containing `timestamp`, `status`, `error`, `message`, `path`, and optional `validationErrors`.
3. **Bean Validation Handling (`@Valid` / `MethodArgumentNotValidException`)**: Automatically captures field validation failures (e.g. `@NotBlank`, `@Email`, `@Min`, `@Size`) and formats field-level error messages.
4. **Custom Domain Exceptions**:
   - `ResourceNotFoundException` -> `404 Not Found`
   - `DuplicateResourceException` -> `409 Conflict`
   - `InvalidOperationException` -> `400 Bad Request`
5. **Spring MVC Standard Exceptions**:
   - Type mismatch on query parameters or path variables (`400 Bad Request`)
   - Malformed JSON payload (`400 Bad Request`)
   - Unsupported HTTP method (`405 Method Not Allowed`)
   - Catch-all fallback (`500 Internal Server Error`) preventing sensitive stack traces from leaking to clients.
6. **Controller-level `@ExceptionHandler`**: Demonstrates that exception handlers inside a `@RestController` override global advice for that controller.
7. **Class-level `@ResponseStatus`**: Custom exception annotated directly with `@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)`.

---

## API Endpoints & Test Scenarios

| Method | Endpoint | Description | Expected Status |
|---|---|---|---|
| `GET` | `/api/users` | List all users | `200 OK` |
| `GET` | `/api/users/1` | Get user by valid ID | `200 OK` |
| `GET` | `/api/users/999` | Get non-existent user | `404 Not Found` (`ResourceNotFoundException`) |
| `POST` | `/api/users` | Create user with valid payload | `201 Created` |
| `POST` | `/api/users` | Create user with invalid fields | `400 Bad Request` (`MethodArgumentNotValidException`) |
| `POST` | `/api/users` | Create user with duplicate email | `409 Conflict` (`DuplicateResourceException`) |
| `GET` | `/api/users/test/invalid-operation` | Triggers invalid business operation | `400 Bad Request` (`InvalidOperationException`) |
| `GET` | `/api/users/test/type-mismatch?id=abc` | Triggers parameter type mismatch | `400 Bad Request` (`MethodArgumentTypeMismatchException`) |
| `GET` | `/api/users/test/server-error` | Triggers unhandled server exception | `500 Internal Server Error` (fallback handler) |
| `GET` | `/api/users/test/controller-handled` | Handled by `@ExceptionHandler` inside controller | `418 I'm a teapot` |
| `GET` | `/api/users/test/response-status` | Handled by `@ResponseStatus` annotation | `402 Payment Required` |

---

## Standard Error Response Format

```json
{
  "timestamp": "2026-08-30T19:20:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for one or more fields",
  "path": "/api/users",
  "validationErrors": [
    {
      "field": "name",
      "rejectedValue": "",
      "message": "Name cannot be blank"
    },
    {
      "field": "email",
      "rejectedValue": "invalid-email",
      "message": "Email must be a valid email address"
    },
    {
      "field": "age",
      "rejectedValue": 12,
      "message": "Age must be at least 18"
    }
  ]
}
```

---

## Running the Project

```powershell
cd .\05exceptionHandling\
mvn spring-boot:run
```

## Running the Tests

```powershell
cd .\05exceptionHandling\
mvn test
```

## Example Requests (PowerShell)

### 1. Trigger 404 Not Found
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/users/999" -Method GET
```

### 2. Trigger 400 Validation Error
```powershell
$body = @{
    name = ""
    email = "invalid-email"
    age = 15
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method POST -Body $body -ContentType "application/json"
```

### 3. Trigger 409 Conflict (Duplicate Email)
```powershell
$body = @{
    name = "Duplicate Alice"
    email = "alice@example.com"
    age = 30
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method POST -Body $body -ContentType "application/json"
```

### 4. Trigger 400 Parameter Type Mismatch
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/users/test/type-mismatch?id=abc" -Method GET
```

### 5. Trigger Controller-level Handler
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/users/test/controller-handled" -Method GET
```
