# Ticket System API (Spring Boot)

---

## Descripción

Backend desarrollado en Java con Spring Boot que expone una API REST para la gestión de tickets.
El proyecto es una evolución de una aplicación en Java puro hacia una arquitectura moderna basada en servicios REST.


## Evolción del proyecto

### v1.0 - Console
- CRUD en memoria
- Uso de ArrayList

### v1.0-jdbc
- Persistencia con MySQL
- JDBC
- Eliminación lógica

### v1.1 - Arquitectura
- Use Case Pattern
- Separación de capas

### v2.0 - Spring Boot API
- Migración a Spring Boot
- Exposición REST
- Manejo de excepciones global
- DTOs para desacoplar modelo

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

---
### Ejemplo request 

<img width="314" height="124" alt="image" src="https://github.com/user-attachments/assets/f167dec1-fe97-4524-946b-04f83e081714" />

### Ejemplo response

<img width="320" height="139" alt="image" src="https://github.com/user-attachments/assets/c08dd1ee-ab16-40e9-9c08-da49f75afa73" />




