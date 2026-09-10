# Visión General

Esta documentación detalla la implementación del backend para el módulo de lectura de perfiles de usuario. La aplicación está construida sobre **Spring Boot 3** utilizando la arquitectura de **Vertical Slicing** alineada con **Arquitectura Hexagonal (Puertos y Adaptadores)**.

## Decisiones de Arquitectura

### Gestión de Identidad y Autenticación

- La gestión de usuarios y autenticación se realiza mediante **Supabase Auth**.
- El backend no gestiona contraseñas. Las credenciales residen en Supabase y la tabla `usuario` local sincroniza el campo `id` usando el UUID autogenerado por Supabase.

### Desacoplamiento de Persistencia

- La capa de **Dominio** no conoce las tecnologías de persistencia (JPA, Hibernate, etc.).
- La comunicación con la base de datos **PostgreSQL** se gestiona estrictamente a través del puerto `UserRepositoryPort` y su adaptador `UserPersistenceAdapter`.

## Estructura de Directorios del Módulo

```text
src/main/java/com/mathetiscore/api/
├── application/
│   ├── dto/
│   │   └── UserResponseDto.java          # Objeto de transferencia de datos de salida
│   └── service/
│       └── UserService.java              # Caso de uso y orquestador de lógica
├── domain/
│   ├── model/
│   │   ├── Role.java                     # Modelo de dominio para Roles
│   │   └── User.java                     # Modelo de dominio para Usuarios
│   └── port/
│       └── UserRepositoryPort.java       # Puerto de salida para acceso a datos
└── infrastructure/
    ├── controller/
    │   └── UserController.java            # Controlador REST (Adaptador de entrada)
    └── repository/
        ├── RoleEntity.java                # Mapeo ORM JPA para roles
        ├── UsuarioEntity.java             # Mapeo ORM JPA para usuarios
        ├── UserPersistenceAdapter.java    # Implementación del puerto de persistencia
        ├── UserPersistenceMapper.java     # Mapeador (Entity <-> Domain)
        └── UserSpringDataRepository.java  # Interfaz JpaRepository
```

## Modelo de Datos (PostgreSQL en Supabase)

### Tabla: `roles`

| Campo | Tipo | Restricciones |
|---|---|---|
| `rol_id` | Long | PK, Auto-increment |
| `rol_nombre` | String | NOT NULL, Unique |
| `rol_descripcion` | String | — |

### Tabla: `usuario`

| Campo | Tipo | Restricciones |
|---|---|---|
| `usu_id` | UUID | PK, mapeado desde Supabase Auth |
| `usu_nombre` | String | NOT NULL |
| `usu_apellido_paterno` | String | NOT NULL |
| `usu_apellido_materno` | String | — |
| `usu_email` | String | NOT NULL, Unique |
| `usu_rut` | String | NOT NULL, Unique |
| `roles_rol_id` | Long | FK → `roles.rol_id` |

## Endpoints REST Expuestos

**Ruta Base:** `/api/v1/users`

### 1. Obtener usuario por UUID

Devuelve la información detallada del perfil de un usuario pasando su identificador único (UUID).

**URL:**

```text
GET /api/v1/users/{id}
```

**Método:** `GET`

#### Parámetros de Ruta

| Parámetro | Tipo | Obligatorio | Ejemplo |
|---|---|---|---|
| `id` | UUID | Sí | `fa2d77e3-48de-49b0-8adf-eaf6339654d2` |

#### Respuestas

- **200 OK:** Devuelve `UserResponseDto`.
- **404 Not Found:** Si el UUID no existe en la base de datos.

#### Ejemplo de Respuesta (200 OK)

```json
{
  "id": "fa2d77e3-48de-49b0-8adf-eaf6339654d2",
  "nombre": "Juan",
  "apellidoPaterno": "Perez",
  "apellidoMaterno": "Gomez",
  "nombreCompleto": "Juan Perez Gomez",
  "email": "juan.perez@escuela.cl",
  "rut": "12345678-9",
  "rolNombre": "DOCENTE"
}
```

### 2. Buscar usuario por Correo Electrónico

Permite la búsqueda directa de un usuario filtrando por su email.

**URL:**

```text
GET /api/v1/users/search
```

**Método:** `GET`

#### Parámetros de Consulta (Query Param)

| Parámetro | Tipo | Obligatorio | Ejemplo |
|---|---|---|---|
| `email` | String | Sí | `juan.perez@escuela.cl` |

#### Respuestas

- **200 OK:** Devuelve `UserResponseDto`.
- **404 Not Found:** Si no existe ningún usuario registrado con dicho correo.

#### Ejemplo de Llamada

```text
GET /api/v1/users/search?email=juan.perez@escuela.cl
```

## Tecnologías y Librerías Utilizadas

- **Java 21**
- **Spring Boot 3.x**
- **Spring Web**
- **Spring Data JPA**
- **PostgreSQL Driver** (Conexión a Supabase)
- **Lombok**