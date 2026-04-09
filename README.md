# Ticket System API (Spring Boot)

---

## Descripción

Backend desarrollado en Java con Spring Boot que expone una API REST para la gestión de tickets.
El proyecto evoluciona desde una aplicación en consola hasta una arquitectura backend moderna, incorporando buenas prácticas como separación por capas, manejo estructurado de errores, y seguridad basada en JWT.


# Ticket System API (Spring Boot)

---


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

### v3.0 - Security (JWT Authentication & Authorization)

- Implementación de autenticación basada en JWT (stateless)
- Reemplazo de Basic Auth por tokens (`Bearer`)
- Creación de `JwtAuthenticationFilter` para validar cada request
- Integración con `UserDetailsService`
- Autorización por roles usando `@PreAuthorize`
- Eliminación de headers manuales (`X-User-Role`)
- Manejo robusto de errores de seguridad:
  - `401 Unauthorized` (no autenticado)
  - `403 Forbidden` (sin permisos)
- Respuestas JSON consistentes en toda la API
- Configuración externa de seguridad (`jwt.secret`, `jwt.expiration`)
- Logging estructurado para eventos de autenticación

---

## Tecnologías

- Java 17+
- Spring Boot
- Spring Security
- JWT (jjwt)
- JDBC
- MySQL
- Maven


## Seguridad

### Autenticación

<img width="617" height="234" alt="image" src="https://github.com/user-attachments/assets/1a1d8ae1-eea2-4af3-b3aa-2464d76b6ac2" />


<img width="974" height="171" alt="image" src="https://github.com/user-attachments/assets/929da25e-ca1f-408a-b98c-9a62addc598c" />



### Endpoints

- GET /tickets --> Requiere autenticación
- GET /tickets/{id} --> Requiere autenticación
- POST /tickets --> Requiere autenticación
- DELETE /tickets/{id} --> Solo ADMIN puede ejecutar el proceso


### Códigos de respuesta

- 200 --> OK
- 201 --> Created
- 204 --> No Content
- 400 --> Bad Request
- 401 --> Unauthorized
- 403 --> Forbidden
- 500 --> Internal Server Error

---
### Ejemplo de respuesta API

<img width="492" height="249" alt="image" src="https://github.com/user-attachments/assets/8fdbd1a8-7a56-4148-91ec-859d6b9e7ab8" />


### Ejemplo de error de validacion API

<img width="711" height="161" alt="image" src="https://github.com/user-attachments/assets/17510894-399f-4fc3-9eb8-ac640c961f0f" />


<img width="503" height="238" alt="image" src="https://github.com/user-attachments/assets/bb51e68f-14a3-4a37-8a5f-919944ed4ba8" />

---


##  Notas técnicas

- La API sigue principios REST utilizando códigos HTTP adecuados.
- Arquitectura basada en capas (Controller, UserCase, Service, Repository)
- Seguridad implementada con Spring Security y JWT (stateless)
- Separación clara entre:
    - Autenticación (JWT)
    - Autorización (roles)
    - Reglas de negocio (Use Cases)     
- Manejo centralizado de errores para mantener consistencia en la API
- Logging estructurado para monitoreo y debugging
- Preparado para escalar a persistencia real de usuarios y cifrado de contraseñas





