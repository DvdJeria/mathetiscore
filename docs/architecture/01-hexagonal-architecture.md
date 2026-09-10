# 01. Arquitectura Hexagonal y Estructura Base

Este documento describe la arquitectura técnica, las responsabilidades de cada capa, el flujo de datos y la configuración inicial de la aplicación backend.

## 1. Patrón Arquitectónico

El proyecto implementa la **Arquitectura Hexagonal (Puertos y Adaptadores)** combinada con principios de **Domain-Driven Design (DDD)**.

El objetivo principal es aislar las reglas de negocio en el núcleo de la aplicación, evitando dependencias directas de frameworks, bases de datos o servicios externos.

### Arquitectura

```text
                  ┌──────────────────────────────────────────────────┐
                  │                 INFRAESTRUCTURA                  │
                  │  ┌────────────────────────────────────────────┐  │
                  │  │                  DOMINIO                   │  │
                  │  │  ┌──────────────────────────────────────┐  │  │
                  │  │  │                                      │  │  │
REST / HTTP ──────┼─>│──┼─>  Puertos Entrada  ─>  Casos Uso    │  │  │
(Input Adapter)   │  │  │         (Input)       (Entidades)    │  │  │
                  │  │  │                                      │  │  │
                  │  │  │  Puertos Salida   <─   Servicios     │  │  │
                  │  │  │        (Output)                      │  │  │
                  │  │  └──────────────────────────────────────┘  │  │
                  │  │                                            │  │
PostgreSQL <──────┼─<│──┼────── Adaptadores de Salida (Output)    │  │
(Output Adapter)  │  │  └────────────────────────────────────────────┘  │
                  │  └──────────────────────────────────────────────────┘
                  └──────────────────────────────────────────────────────┘
```

## 2. Responsabilidades por Capa

Estructura de paquetes bajo la ruta base `com.mathetiscore.api`:

### 2.1. Capa de Dominio (`/domain`)

Es el núcleo del sistema. No contiene dependencias de Spring, JPA ni ninguna otra librería de terceros.

**`model/`**

Entidades y Objetos de Valor (Value Objects) que encapsulan las reglas del negocio.

**`ports/input/`**

Interfaces que definen las operaciones que la aplicación expone (Casos de Uso).

**`ports/output/`**

Interfaces que definen los contratos para interactuar con sistemas externos (Repositorios, Notificaciones, etc.).

### 2.2. Capa de Aplicación (`/application`)

Actúa como orquestador entre el mundo exterior y el dominio.

**`usecase/`**

Implementación de las interfaces de entrada (`ports/input`). Ejecutan la lógica del negocio coordinando las entidades del dominio y los puertos de salida.

### 2.3. Capa de Infraestructura (`/infrastructure`)

Contiene los detalles técnicos e implementaciones concretas.

**`adapters/input/rest/`**

Controladores Spring MVC (`@RestController`) que procesan peticiones HTTP, convierten DTOs e invocan casos de uso.

**`adapters/input/rest/exception/`**

Manejo centralizado de excepciones con `@RestControllerAdvice` y respuesta estandarizada `ApiErrorResponse`.

**`adapters/output/persistence/`**

Adaptadores de persistencia mediante Spring Data JPA, repositorios e instancias de mapeo entre entidades JPA y objetos de dominio.

**`config/`**

Configuraciones del framework (CORS vía `WebConfig`, Beans de Spring, etc.).

## 3. Flujo de Datos

**Cliente (Angular / Ionic / Mobile):** Realiza una petición HTTP hacia un endpoint de la API (`/api/...`).

**CORS Filter (`WebConfig`):** Verifica que el origen de la petición esté permitido.

**Rest Controller (Input Adapter):** Recibe la petición, valida la entrada DTO y transfiere el control al Puerto de Entrada.

**Use Case (Application):** Ejecuta la regla de negocio. Si requiere persistencia o servicios externos, consulta un Puerto de Salida.

**Persistence Adapter (Output Adapter):** Implementa la interfaz del Puerto de Salida, consulta la base de datos PostgreSQL con Spring Data JPA y mapea las entidades JPA a modelos de Dominio.

**Manejo de Errores:** En caso de fallos, `GlobalExceptionHandler` captura la excepción y retorna un JSON unificado `ApiErrorResponse`.

## 4. Componentes Implementados

### 4.1. Manejo Global de Excepciones

Garantiza un formato estandarizado de respuesta de error en toda la API mediante `ApiErrorResponse`:

```json
{
  "timestamp": "2026-09-10T14:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Ha ocurrido un error inesperado.",
  "path": "/api/v1/ejemplo"
}
```

`GlobalExceptionHandler`: Intercepta excepciones de tipo `Exception` (código HTTP 500) y de validación/negocio personalizadas.

### 4.2. Configuración de CORS (`WebConfig`)

Configurado para permitir comunicación con clientes web y móviles desarrollados en Angular / Ionic / Capacitor:

| Origen Permitido        | Uso / Entorno            |
| ----------------------- | ------------------------ |
| `http://localhost:4200` | Desarrollo Web (Angular) |
| `http://localhost:8100` | Live Reload (Ionic CLI)  |
| `http://localhost`      | Android (Capacitor)      |
| `capacitor://localhost` | iOS (Capacitor)          |

## 5. Configuración del Entorno (`application.yml`)

Variables de entorno predeterminadas para ejecución local:

| Variable                     | Descripción                  | Valor Local                                        |
| ---------------------------- | ---------------------------- | -------------------------------------------------- |
| `SPRING_DATASOURCE_URL`      | URL de conexión a PostgreSQL | `jdbc:postgresql://localhost:5432/mathetiscore_db` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de BD                | `postgres`                                         |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de BD             | `postgres`                                         |
| `SERVER_PORT`                | Puerto HTTP del backend      | `8080`                                             |

## 6. Estrategia de Pruebas

Las pruebas de integración de la capa web utilizan un enfoque desacoplado:

**Pruebas de Controladores / Configuración:** Implementadas con `@WebMvcTest` y `@AutoConfigureMockMvc` usando `MockMvc` para simular peticiones HTTP sin levantar un servidor Tomcat real.

**Pruebas del Exception Handler:** Validadas mediante controladores sintéticos (`TestExceptionController`) que simulan errores reales dentro del contexto de Spring Test.
