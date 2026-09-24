# Documentación Técnica: Módulo de Registro de Profesores (Arquitectura Hexagonal)

## 1. Resumen y Contexto
Este documento registra la arquitectura, el flujo transaccional y la solución implementada para aislar la creación de profesores del módulo monolítico de usuarios (`UserService`), estableciendo un flujo secuencial robusto basado en **Arquitectura Hexagonal**.

---

## 2. Flujo Secuencial de Creación (`ProfesorService`)
El registro de un profesor se ejecuta de manera transaccional (`@Transactional`) y estrictamente secuencial para respetar la jerarquía de llaves foráneas en la base de datos:

1. **Creación del Usuario Base (Auth + Tabla `usuario`):**
   * Se invoca a `UserRegistrationHelper` para registrar las credenciales en Supabase Auth y persistir el perfil base en la tabla `usuario` utilizando el UUID generado.
2. **Registro del Perfil de Profesor:**
   * Se crea la entidad `Profesor` vinculada al `usu_id` del usuario recién creado y a su respectiva sede (`ased_id`), persistiendo a través de `ProfesorRepositoryPort`.
3. **Registro de Antecedentes / Títulos Docentes:**
   * Si el request incluye datos de titulación (`tma_id != null`), se persiste en la tabla `titulos_profesor` utilizando de manera segura el `prof_id` obtenido en el paso anterior.

---

## 3. Componentes Principales

### Capa de Presentación / Aplicación
* **`ProfesorController`**: Expone el endpoint POST en `/api/v1/profesor`.
* **`ProfesorService`**: Orquesta el flujo de negocio y la secuencia transaccional.

### Capa de Infraestructura (Adaptadores y Persistencia)
* **`TituloProfesorRepositoryAdapter`**: Implementa `TituloProfesorRepositoryPort`. Utiliza `EntityManager.getReference()` para asociar las entidades relacionadas (`ProfesorEntity` y `TituloMaestroEntity`) como referencias gestionadas por Hibernate, evitando inserciones duplicadas no deseadas.

```java
@Component
@RequiredArgsConstructor
public class TituloProfesorRepositoryAdapter implements TituloProfesorRepositoryPort {

    private final TituloProfesorSpringDataRepository repository;
    private final EntityManager entityManager;

    @Override
    public TituloProfesor save(TituloProfesor dominio) {
        ProfesorEntity profesorEntity = entityManager.getReference(ProfesorEntity.class, dominio.getProfesorId());
        TituloMaestroEntity maestroEntity = entityManager.getReference(TituloMaestroEntity.class, dominio.getTmaId());

        TituloProfesorEntity entity = TituloProfesorEntity.builder()
                .institutoEgreso(dominio.getInstitutoEgreso())
                .annoTitulacion(dominio.getAnnoTitulacion())
                .urlDocumento(dominio.getUrlDocumento())
                .descripcion(dominio.getDescripcion())
                .profesor(profesorEntity)
                .tituloMaestroEntity(maestroEntity)
                .build();

        TituloProfesorEntity saved = repository.save(entity);
        dominio.setId(saved.getId());
        return dominio;
    }
}