# Feature: Registro de Profesores (createProfesor)

## Descripción General
Implementación del endpoint especializado para el registro de nuevos profesores bajo **Arquitectura Hexagonal**. El flujo se encarga de orquestar la autenticación externa en **Supabase Auth**, aprovechar los triggers de base de datos para la sincronización inicial de la tabla `usuario`, y realizar la persistencia transaccional dual en las tablas `profesor` y `titulos_profesor`.

---

## Arquitectura y Componentes
El flujo respeta los límites de la arquitectura hexagonal:

1. **Dominio (`Domain`)**:
   - Modelos de dominio (`Profesor`, etc.) y puertos de salida (`AuthPort`, `ProfesorRepositoryPort`).
2. **Aplicación (`Application`)**:
   - Servicios de aplicación que coordinan la lógica de negocio y mapeo hacia los DTOs (`ProfesorRequestDto`, `ProfesorResponseDto`).
3. **Infraestructura (`Infrastructure`)**:
   - **`SupabaseAuthRepositoryAdapter`**: Adaptador REST para comunicarse con la API administrativa de Supabase (`/auth/v1/admin/users`) enviando las cabeceras requeridas (`apikey` y `Authorization: Bearer <secret_key>`).
   - **`ProfesorRepositoryAdapter`**: Adaptador de persistencia JPA encargado del guardado secuencial (primero `ProfesorEntity` para recuperar el ID autogenerado, y posteriormente `TituloProfesorEntity` asegurando la relación no nula).

---

## Endpoint API

* **URL:** `/api/v1/users/profesor`
* **Método:** `POST`
* **Content-Type:** `application/json`

### Payload de Ejemplo (`ProfesorRequestDto`)
```json
{
  "rut": "12345678-9",
  "nombre": "Carlos",
  "apellidoPaterno": "González",
  "apellidoMaterno": "Pérez",
  "email": "carlos.profesor@mathetiscore.com",
  "password": "SecurePassword123*",
  "usuarioDescripcion": "Profesor del departamento de ciencias exactas",
  "profesorDescripcion": "Especialista en álgebra y cálculo superior",
  "asedId": "5e295154-83c3-408c-97e6-4d859f385763",
  "tmaId": 2,
  "institutoEgreso": "Universidad de Chile",
  "annoTitulacion": "2018-12-15",
  "urlDocumento": "[https://tuservidor.com/docs/titulo_carlos.pdf](https://tuservidor.com/docs/titulo_carlos.pdf)",
  "tituloDescripcion": "Título de Profesor de Matemáticas y Computación"
}