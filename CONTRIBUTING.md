# Guía de Contribución a MathetisCore

Agradecemos tu interés en contribuir a MathetisCore. Para mantener un estándar de calidad alto y un historial limpio, todos los colaboradores deben seguir las siguientes directrices.

## Workflow de Desarrollo (Git Flow)
1. Queda estrictamente prohibido hacer `push` directo a la rama `main`.
2. Todo desarrollo debe realizarse en una rama secundaria creada desde `main`.
3. Para integrar cambios a `main`, se debe abrir un Pull Request (PR) y obtener la aprobación del equipo.

## Nomenclatura de Ramas
* `feature/nombre-funcionalidad`: Para desarrollo de nuevas características (Ej: `feature/script-sql-supabase`).
* `bugfix/descripcion-error`: Para corrección de errores.
* `docs/tema-documentado`: Para actualizaciones de documentación.

## Formato de Commits
Usamos un formato descriptivo para el historial:
* `feat:` Nueva funcionalidad.
* `fix:` Corrección de un error.
* `docs:` Cambios en la documentación.
* `style:` Formato, punto y coma faltante, etc. (sin cambios de código de producción).
* `refactor:` Refactorización de código.

Ejemplo: `git commit -m "feat: agregar tabla de calificaciones en script DDL"`