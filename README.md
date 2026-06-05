# 🎫 Ticket System API

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=flat&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-brightgreen?style=flat&logo=springsecurity)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?style=flat&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.x-red?style=flat&logo=apachemaven)
![Version](https://img.shields.io/badge/version-v5.0-blueviolet?style=flat)

Backend empresarial desarrollado en **Java con Spring Boot** para la gestión 
de tickets de soporte técnico. El proyecto simula un sistema real inspirado 
en herramientas como **Odoo**, evolucionando desde una aplicación de consola 
hasta una API REST segura con autenticación JWT, máquina de estados, 
y gestión real de usuarios con BCrypt.

---

## 📋 Tabla de contenidos

- [Evolución del proyecto](#evolución-del-proyecto)
- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Configuración e instalación](#configuración-e-instalación)
- [Seguridad](#seguridad)
- [Endpoints](#endpoints)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Roadmap](#roadmap)

---

## 🚀 Evolución del proyecto

### v1.0 - Console
- CRUD de tickets en memoria con `ArrayList`
- Menú interactivo en consola
- Roles básicos

### v1.0-jdbc - Persistence
- Migración a Maven
- Integración con MySQL
- CRUD persistente con JDBC
- Eliminación lógica de registros

### v1.1 - Application Layer
- Arquitectura por capas
- Use Case Pattern
- Separación de responsabilidades (UI / Application / Domain)
- Inyección de dependencias manual

### v2.0 - Spring Boot API
- Migración completa a Spring Boot
- Endpoints REST (GET / POST)
- Integración JDBC + Datasource
- Manejo global de excepciones (`@RestControllerAdvice`)

### v2.1 - API Standardization & Validation
- DTOs (Request / Response)
- `ResponseEntity` con códigos HTTP correctos
- Respuestas estandarizadas: `ApiResponse`, `ErrorResponse`, `ValidationErrorResponse`
- Validaciones con Bean Validation (`@Valid`, `@NotBlank`)
- Autorización por headers (`X-User-Role`)

### v3.0 - Security (JWT Authentication & Authorization)
- Autenticación stateless con JWT
- Reemplazo de Basic Auth por tokens `Bearer`
- `JwtAuthenticationFilter` para validar cada request
- Autorización por roles con `@PreAuthorize`
- Manejo estructurado de errores de seguridad (401 / 403)
- Configuración externa (`jwt.secret`, `jwt.expiration`)

### v4.0 - State Machine & Domain Rules
- State Machine implementada sin librería externa
- `TicketStateTransitionValidator` a nivel de dominio
- Bloqueo de modificación de prioridad en estado final
- Excepciones de dominio: `InvalidTransitionException`, `InvalidOperationException`
- Use Cases: `UpdateTicketStatusUseCase`, `UpdateTicketPriorityUseCase`
- Endpoints PATCH para estado y prioridad
- Errores de dominio retornan `409 Conflict`

**Business rules:**

- ABIERTO    → EN_PROCESO, CANCELADO
- EN_PROCESO → CERRADO, CANCELADO
- CERRADO    → estado final (irreversible)
- CANCELADO  → estado final (irreversible)
- Prioridad no modificable en estado final

### v5.0 - Real User Management ⬅️ actual
- Tabla `usuarios` con persistencia real en MySQL
- `CustomUserDetailsService` conectado a DB (elimina mock)
- Passwords encriptados con **BCrypt** (rounds=10)
- Endpoint `POST /auth/register` exclusivo para ADMIN
- Admin master crea usuarios con roles `ADMIN` o `USER`
- Usuarios `INACTIVO` bloqueados en autenticación
- Auditoría: `created_by` registra quién creó cada usuario
- Fix: `SecurityConfig` separa `/auth/login` de `/auth/register`
- Fix: `GlobalExceptionHandler` cubre 409 (duplicados) y 400 (enums inválidos)
- Fix: eliminado constructor parcial de `Usuario` y validación de rol redundante

---

## 🏛️ Arquitectura

El proyecto sigue una **arquitectura por capas** con separación clara de responsabilidades:


| Etapa | Proceso |
|---|---|
| Controller Layer | Recibe requests HTTP, valida DTOs |
| Use Case Layer | Orquesta lógica de negocio |
| Service Layer    | Reglas de dominio |
| Repository Layer    | Acceso a datos (JDBC) |
| MySQL DB    | tickets + usuarios |

**Seguridad (transversal a todas las capas):**
- JwtAuthenticationFilter → SecurityConfig → @PreAuthorize

---

## 🛠️ Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17+ | Lenguaje principal |
| Spring Boot | 3.x | Framework base |
| Spring Security | 6.x | Autenticación y autorización |
| JWT (jjwt) | 0.12.x | Tokens stateless |
| JDBC | — | Acceso a datos sin ORM |
| MySQL | 8.x | Base de datos |
| Maven | 3.x | Gestión de dependencias |
| BCrypt | — | Encriptación de passwords |

---

## ⚙️ Configuración e instalación

### Prerrequisitos
- Java 17+
- MySQL 8+
- Maven 3+

### 1. Clonar el repositorio
```bash
git clone https://github.com/jose-luis-dev/ticket-system-api.git
cd ticket-system-api
```

### 2. Configurar base de datos
```sql
CREATE DATABASE ticket_system;
```
Ejecutar los scripts en orden:

```
src/main/resources/db/V1__create_tickets_table.sql
src/main/resources/db/V2__create_usuarios_table.sql

### 3. Configurar `application.yaml`
Copia el archivo de ejemplo y ajusta tus credenciales:
```bash
cp src/main/resources/application-example.yaml \
   src/main/resources/application.yaml
```
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ticket_system
    username: TU_USUARIO
    password: TU_PASSWORD

jwt:
  secret: "tu-secret-key-minimo-32-caracteres"
  expiration: 3600000
```

### 4. Insertar admin master
```sql
-- Password: admin123
INSERT INTO usuarios (username, password, nombre, email, rol, estado, created_by)
VALUES (
    'admin',
    '$2a$10$HASH_GENERADO_CON_TU_APP',
    'Administrador Master',
    'admin@empresa.com',
    'ADMIN', 'ACTIVO', 'SYSTEM'
);
```
> ⚠️ Genera el hash ejecutando `GenerarHash.java` con tu propio `BCryptPasswordEncoder`

### 5. Ejecutar
```bash
mvn spring-boot:run
```

---

## 🔐 Seguridad

### Flujo de autenticación

POST /auth/login { username, password }<br>
↓<br>
CustomUserDetailsService (consulta MySQL)<br>
↓<br>
BCrypt.matches(rawPassword, hashDB)<br>
↓<br>
JwtService.generateToken()<br>
↓<br>
{ token: "eyJhbGc..." }<br>

### Flujo de autorización

Request con Bearer Token<br>
↓<br>
JwtAuthenticationFilter (valida token)<br>
↓<br>
@PreAuthorize("hasRole('ADMIN')")<br>
↓<br>
Use Case → Service → Repository<br>

---

## 📡 Endpoints

### Autenticación
| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| POST | `/auth/login` | Público | Obtiene token JWT |
| POST | `/auth/register` | ADMIN | Crea nuevo usuario |

### Tickets
| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| GET | `/tickets` | Autenticado | Lista todos los tickets |
| GET | `/tickets/{id}` | Autenticado | Obtiene ticket por ID |
| POST | `/tickets` | Autenticado | Crea nuevo ticket |
| DELETE | `/tickets/{id}` | ADMIN | Eliminación lógica |
| PATCH | `/tickets/{id}/estado` | ADMIN, USER | Cambia estado |
| PATCH | `/tickets/{id}/prioridad` | ADMIN, USER | Cambia prioridad |

### Códigos de respuesta
| Código | Significado |
|---|---|
| 200 | OK |
| 201 | Created |
| 204 | No Content |
| 400 | Bad Request — datos inválidos |
| 401 | Unauthorized — sin token |
| 403 | Forbidden — sin permisos |
| 404 | Not Found — recurso no existe |
| 409 | Conflict — duplicado o transición inválida |
| 500 | Internal Server Error |

---

## 📁 Estructura del proyecto

src/main/java/com/ticketSystem/<br>
├── application/<br>
│   ├── validators/<br>
│   │   └── TicketStateTransitionValidator<br>
│   ├── CreateTicketUseCase<br>
│   ├── DeleteTicketUseCase<br>
│   ├── GetTicketByIdUseCase<br>
│   ├── ListTicketUseCase<br>
│   ├── RegisterUserUseCase<br>
│   ├── TicketStatisticsUseCase<br>
│   ├── UpdateTicketPriorityUseCase<br>
│   └── UpdateTicketStatusUseCase<br>
├── config/<br>
│   └── SecurityConfig<br>
├── controller/<br>
│   ├── dto/<br>
│   │   ├── ApiResponse<br>
│   │   ├── AuthResponse<br>
│   │   ├── CreateTicketRequest<br>
│   │   ├── ErrorResponse<br>
│   │   ├── LoginRequest<br>
│   │   ├── LoginResponse<br>
│   │   ├── RegisterUserRequest<br>
│   │   ├── TicketMapper<br>
│   │   ├── TicketResponse<br>
│   │   ├── UpdatePriorityRequest<br>
│   │   ├── UpdateStatusRequest<br>
│   │   ├──UsuarioMapper<br>
│   │   ├──UsuarioResponse<br>
│   │   └── ValidationErrorResponse<br>
│   ├── AuthController<br>
│   └── TicketController<br>
├── enums/<br>
│   ├── EstadoOperacional<br>
│   ├── EstadoRegistro<br>
│   ├── Prioridad<br>
│   └── RolUsuario<br>
├── exception/<br>
│   ├── handler/<br>
│   │   └── GlobalExceptionHandler<br>
│   ├── DatabaseException<br>
│   ├── InvalidOperationException<br>
│   ├── InvalidTransitionException<br>
│   ├── TicketNotFoundException<br>
│   └── UnauthorizedOperacionException<br>
├── model/<br>
│   ├── Ticket<br>
│   └── Usuario<br>
├── repository/<br>
│   ├── ITicketRepository<br>
│   ├── IUsuarioRepository<br>
│   ├── TicketRepository<br>
│   ├── TicketRepositoryJdbc<br>
│   └── UsuarioRepositoryJdbc<br>
├── security/<br>
│   ├── CustomUserDetailsService<br>
│   ├── JwtAuthenticationFilter<br>
│   └── JwtService<br>
└── service/<br>
└── TicketService<br>

---

## 🗺️ Roadmap

| Versión | Descripción | Estado |
|---|---|---|
| v1.0 | Console CRUD | ✅ Completado |
| v2.0 | Spring Boot REST API | ✅ Completado |
| v3.0 | JWT Security | ✅ Completado |
| v4.0 | State Machine | ✅ Completado |
| v5.0 | Real User Management | ✅ Completado |
| v6.0 | Email notifications + ticket assignment | 🔜 Próximo |
| v7.0 | KPI Dashboard & estadísticas por usuario | 🔜 Planeado |

---

## 👨‍💻 Autor -- Luis Alvarado

Desarrollado como proyecto de portafolio backend — evolucionando hacia
un sistema empresarial real de gestión de tickets.
