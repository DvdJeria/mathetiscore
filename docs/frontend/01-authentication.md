# Módulo de Autenticación (Sub-Task 1)

Especificación técnica y arquitectura del flujo de inicio de sesión, manejo de sesión y protección de rutas.

---

## 🔐 Configuración de Autenticación y Entorno

Para ejecutar la aplicación localmente, debes configurar las credenciales de Supabase:

1. Duplica el archivo de plantilla ubicado en `src/environments/`:
   `cp src/environments/environment.example.ts src/environments/environment.ts`
2. Reemplaza `YOUR_SUPABASE_URL_HERE` y `YOUR_SUPABASE_ANON_KEY_HERE` con las llaves del proyecto en `environment.ts`.

> **Nota de Seguridad:** El archivo `environment.ts` está incluido en `.gitignore` para prevenir la exposición de credenciales en el repositorio.

---

## 🏗️ Arquitectura del Módulo

* **`AuthService` (`src/app/core/services/auth.service.ts`)**: Servicio Singleton inyectable que encapsula la interacción directa con la SDK de Supabase (`login`, `logout` y `getSession`).
* **`authGuard` (`src/app/core/guards/auth.guard.ts`)**: Guard funcional (`CanActivateFn`) encargado de la protección de rutas (`/home`) validando la sesión activa del usuario.
* **`LoginPage` (`src/app/pages/login/`)**: Componente standalone construido con componentes nativos de Ionic (`ion-input`, `ion-button`, `ion-spinner`) y control flow moderno (`@if`).

---

## 🛠️ Comandos de Ejecución

* **Servidor de desarrollo:** `npx ionic serve`
* **Verificación de linter:** `npx eslint .`