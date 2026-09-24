# Documentación Técnica: Módulo de Registro de Alumnos (Arquitectura Hexagonal)

## 1. Resumen y Contexto
Este documento registra la arquitectura, el flujo transaccional y la estructura implementada para aislar la creación de alumnos del módulo monolítico de usuarios (`UserService`), estableciendo un flujo secuencial robusto basado en **Arquitectura Hexagonal** y análogo al diseño utilizado para los profesores.

---

## 2. Flujo Secuencial de Creación (`AlumnoService`)
El registro de un alumno se ejecuta de manera transaccional (`@Transactional`) y estrictamente secuencial para respetar la jerarquía de llaves foráneas entre la autenticación, la tabla base de usuarios y el perfil específico:

1. **Creación del Usuario Base (Auth + Tabla `usuario`):**
   * Se invoca a `UserRegistrationHelper` para registrar las credenciales en Supabase Auth y persistir el perfil base en la tabla `usuario` utilizando el UUID generado.
2. **Registro del Perfil de Alumno:**
   * Se crea la entidad `Alumno` vinculada al `usu_id` del usuario recién creado (`usuario_usu_id`), persistiendo a través de su respectivo puerto y adaptador de repositorio (`AlumnoRepositoryPort`).

---

## 3. Componentes Principales

### Capa de Presentación / Aplicación
* **`AlumnoController`**: Expone el endpoint POST para la creación de alumnos (ej. `/api/v1/alumno`).
* **`AlumnoService`**: Orquesta el flujo de negocio transaccional y la llamada al helper de registro.

### Capa de Infraestructura (Adaptadores y Persistencia)
* **`AlumnoRepositoryAdapter`**: Implementa `AlumnoRepositoryPort`, encargándose de interactuar con `AlumnoSpringDataRepository` para persistir el perfil del estudiante en la tabla `alumnos`.

```java
@Component
@RequiredArgsConstructor
public class AlumnoRepositoryAdapter implements AlumnoRepositoryPort {

    private final AlumnoSpringDataRepository repository;

    @Override
    public Alumno save(Alumno dominio) {
        AlumnoEntity entity = AlumnoEntity.builder()
                .descripcion(dominio.getDescripcion())
                .usuarioId(dominio.getUsuarioId()) // Vinculación directa con el ID de usuario
                .build();

        AlumnoEntity saved = repository.save(entity);
        dominio.setId(saved.getId());
        return dominio;
    }
}