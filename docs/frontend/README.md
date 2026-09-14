# Front-end - MathetiScore (School Management System)

Aplicación móvil y web desarrollada con **Ionic Standalone**, **Angular** y **Supabase**.

---

## Índice de Documentación Técnica

La documentación del proyecto está estructurada de forma modular para facilitar su mantenimiento. Consulta los detalles de cada sub-tarea en sus archivos correspondientes:

### Configuración y Entorno
* **[Variables de Entorno y Seguridad](docs/architecture/01-authentication.md#-configuración-de-autenticación-y-entorno)**: Guía para configurar `environment.ts` con Supabase.

### Arquitectura por Módulos
1. **[01. Módulo de Autenticación](docs/architecture/01-authentication.md)**
   * Servicio `AuthService` (Singleton Supabase).
   * Protección de rutas mediante `authGuard` (`CanActivateFn`).
   * Interfaz de usuario `LoginPage` y flujo de `logout`.

2. **[02. Módulo de RBAC, Roles y Navegación Dinámica](docs/architecture/02-rbac-navegacion.md)**
   * Arquitectura centralizada de roles (`Role` enum).
   * Control de acceso estricto y pase libre de auditoría para `SUPER_ADMIN` mediante `rolGuard`.
   * Enrutamiento jerárquico con múltiples guards (`authGuard` + `rolGuard`).
   * Menú lateral adaptativo reactivo con componentes Ionic Standalone (`[button]="true"` y directivas `@if`).

---

## Comandos Generales

* **Servidor de desarrollo:** `npx ionic serve`
* **Verificación de linter:** `npx eslint .`
* **Construcción de producción:** `npx ionic build`