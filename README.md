# Ticket System API (Spring Boot)

---

## Descripción

Backend desarrollado en Java con Spring Boot que expone una API REST para la gestión de tickets.
El proyecto es una evolución de una aplicación en Java puro hacia una arquitectura moderna basada en servicios REST.


## Evolción del proyecto

### v1.0 - Console
- CRUD de tickets en memoria
- Uso de ArrayList
- Menú interactivo
- Roles básicos

### v1.0-jdbc Persistence
- Migración a Maven
- Integración con MySQL
- CRUD persistence con JDBC
- Eliminación lógica de registros

### v1.1 - Application Layer
- Implementación de arquitectura por capas
- Introducción de Use Case Pattern
- Separación de responsabilidades (UI / Application / Domain)
- Inyección de dependencias manual

### v2.0 - Spring Boot API
- Migración completa a Spring Boot
- Exposición de endPoints REST (GET / POST)
- Integración con JDBC + Datasource
- Manejo global de excepciones (@RestControllerAdvice)
- Estructura base para backend escalable

### v2.1 - API Standardization & Validation
- Implementación de DTOs (Request / Response)
- Introducción de `ResponseEntity` con códigos HTTP correctos (200, 201, 204, 400, 403)
- Estandarización de respuesta con:
    - `ApiResponse` (respuestas exitosas)
    - `ErrorResponse` (errores de negocio)
    - `ValidationErrorResponse` (errores de validación por campo)
- Validaciones con Bean Validation (`@Valid`. `@NotBlank`)
- Manejo estructurado de errores de entrada (400 Bad Request)
- Simulación de autorización basada en roles mediante headers (`X-User-Role`)
- Mejora del contrato API para integración con frontend

---

## Tecnologías

- Java 17+
- Spring Boot
- JDBC
- MySQL
- Maven

## Endpoints

- GET /tickets
- GET /tickets/{id}
- POST /tickets
- DELETE /tickets/{id} - Solo ADMIN puede ejecutar el proceso

---
### Ejemplo de respuesta API

<img width="492" height="249" alt="image" src="https://github.com/user-attachments/assets/8fdbd1a8-7a56-4148-91ec-859d6b9e7ab8" />


### Ejemplo de error de validacion API

<img width="503" height="238" alt="image" src="https://github.com/user-attachments/assets/bb51e68f-14a3-4a37-8a5f-919944ed4ba8" />




---

# Sección: Notas técnicas (esto te diferencia)

```md
##  Notas técnicas

- La API sigue principios REST utilizando códigos HTTP adecuados.
- Se implementó un contrato de respuesta uniforme para facilitar la integración con frontend.
- Las validaciones se manejan a nivel de DTO usando Bean Validation.
- La lógica de negocio permanece desacoplada de la capa de presentación.
- La autorización actual es simulada y preparada para migrar a JWT.




