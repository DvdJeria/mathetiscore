# Módulo de Autenticación, Roles y Navegación Dinámica (RBAC)

## Descripción General
Este documento detalla la implementación del sistema de autenticación, control de acceso basado en roles (RBAC), guards de enrutamiento y la navegación dinámica adaptativa en la aplicación frontend (Angular/Ionic). El objetivo de este módulo es proteger las rutas del sistema según los privilegios del usuario autenticado y proveer una interfaz adaptativa en el menú lateral.

---

## 1. Arquitectura de Roles y Modelos
Los roles del sistema están definidos de manera centralizada en un Enumerador para asegurar consistencia entre el backend y el frontend.

### Definición de Roles (`src/app/core/models/roles.enum.ts`)
```typescript
export enum Role {
  SUPER_ADMIN = 'SUPER_ADMIN',
  ADMIN_SEDE = 'ADMIN_SEDE',
  DOCENTE = 'DOCENTE',
  ESTUDIANTE = 'ESTUDIANTE',
  APODERADO = 'APODERADO'
}
```

---

## 2. Servicio de Usuario y Gestión de Sesión
El estado del perfil del usuario se administra de forma reactiva utilizando **Angular Signals** dentro de un servicio central (`UserService`), el cual recupera la información tras una autenticación exitosa.

---

## 3. Protección de Rutas (Guards)

### A. Autenticación Básica (`authGuard`)
Verifica que exista una sesión activa o un token válido antes de permitir el acceso a cualquier ruta protegida del sistema.

### B. Control de Acceso por Roles (`rolGuard`)
El guard de roles evalúa el perfil del usuario utilizando un enfoque estricto, implementando además un **pase libre de auditoría** para el rol `SUPER_ADMIN`, permitiéndole visualizar todos los módulos del sistema sin restricciones.

**Implementación (`src/app/core/guards/role.guards.ts`):**
```typescript
import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { UserService } from "../services/user.service";
import { Role } from "../models/roles.enum";

export const rolGuard = (allowedRoles: Role[]): CanActivateFn => {
  return () => {
    const userService = inject(UserService);
    const router = inject(Router);

    const user = userService.userProfile();
    const userRole = user?.rolNombre as Role;

    // Paso 1: Verificar si existe el perfil cargado
    if (!userRole) {
      const hasToken = !!localStorage.getItem('token'); 
      if (!hasToken) {
        router.navigate(['/login']);
        return false;
      }
    }

    // Pase libre de auditoría para SuperAdmin
    if (userRole === Role.SUPER_ADMIN) {
      return true;
    }

    // Paso 2: Verificar si el rol del usuario está permitido
    if (allowedRoles.includes(userRole)) {
      return true;
    }

    // Paso 3: Redirección según rol en caso de intento no autorizado
    console.warn(`Acceso no autorizado intentado por rol: ${userRole}`);

    switch (userRole) {
      case Role.DOCENTE:
        router.navigate(['/docente/dashboard']);
        break;
      case Role.ESTUDIANTE:
        router.navigate(['/estudiante/dashboard']);
        break;
      case Role.ADMIN_SEDE:
        router.navigate(['/admin/gestion-usuarios']);
        break;
      default:
        router.navigate(['/home']);
    }

    return false;
  };
};
```

---

## 4. Configuración de Enrutamiento (`app.routes.ts`)
Las rutas se estructuran jerárquicamente utilizando componentes *Standalone* y aplicando múltiples guards en secuencia (`authGuard` + `rolGuard`).

**Estructura de Rutas (`src/app/app.routes.ts`):**
```typescript
import { Routes } from '@angular/router';
import { rolGuard } from "./core/guards/role.guards";
import { authGuard } from "./core/guards/auth-guard";
import { Role } from "./core/models/roles.enum";

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.page').then(m => m.LoginPage),
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home.page').then(m => m.HomePage),
    canActivate: [authGuard]
  },
  {
    path: 'admin/gestion-usuarios',
    loadComponent: () => import('./pages/admin/gestion-usuarios/gestion-usuarios.page').then(m => m.GestionUsuariosPage),
    canActivate: [authGuard, rolGuard([Role.SUPER_ADMIN, Role.ADMIN_SEDE])]
  },
  {
    path: 'docente',
    redirectTo: 'docente/dashboard',
    pathMatch: 'full',
  },
  {
    path: 'docente/dashboard',
    loadComponent: () => import('./pages/docente/dashboard/dashboard.page').then(m => m.DashboardPage),
    canActivate: [authGuard, rolGuard([Role.DOCENTE])]
  },
  {
    path: 'docente/tomar-asistencia',
    loadComponent: () => import('./pages/docente/tomar-asistencia/tomar-asistencia.page').then(m => m.TomarAsistenciaPage),
    canActivate: [authGuard, rolGuard([Role.DOCENTE])]
  },
  {
    path: 'estudiante',
    redirectTo: 'estudiante/dashboard',
    pathMatch: 'full',
  },
  {
    path: 'estudiante/dashboard',
    loadComponent: () => import('./pages/estudiante/dashboard/dashboard.page').then(m => m.DashboardPage),
    canActivate: [authGuard, rolGuard([Role.ESTUDIANTE, Role.APODERADO])]
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];
```

---

## 5. Interfaz Adaptativa y Navegación Lateral (`app.component.html` / `.ts`)
El menú lateral reacciona de forma dinámica al rol del usuario utilizando estructuras de control condicional de Angular (`@if`), permitiendo al `SUPER_ADMIN` visualizar todas las opciones y restringiendo los módulos específicos a sus roles correspondientes.

### Plantilla del Menú (`app.component.html`)
```html
<ion-menu contentId="main-content">
  <ion-header>
    <ion-toolbar color="primary">
      <ion-title>Menú Principal</ion-title>
    </ion-toolbar>
  </ion-header>

  <ion-content>
    <ion-list>
      <ion-menu-toggle auto-hide="true">
        <!-- Inicio -->
        <ion-item [button]="true" routerLink="/home" routerDirection="root">
          <ion-label>Inicio</ion-label>
        </ion-item>

        <!-- Módulo de Administración -->
        @if (userRole() === Role.SUPER_ADMIN || userRole() === Role.ADMIN_SEDE) {
          <ion-item [button]="true" routerLink="/admin/gestion-usuarios" routerDirection="root">
            <ion-label>Gestión de Usuarios</ion-label>
          </ion-item>
        }

        <!-- Módulo Docente -->
        @if (userRole() === Role.DOCENTE || userRole() === Role.SUPER_ADMIN) {
          <ion-item [button]="true" routerLink="/docente/dashboard" routerDirection="root">
            <ion-label>Panel Docente</ion-label>
          </ion-item>
          <ion-item [button]="true" routerLink="/docente/tomar-asistencia" routerDirection="root">
            <ion-label>Tomar Asistencia</ion-label>
          </ion-item>
        }

        <!-- Módulo Estudiante / Apoderado -->
        @if (userRole() === Role.ESTUDIANTE || userRole() === Role.APODERADO || userRole() === Role.SUPER_ADMIN) {
          <ion-item [button]="true" routerLink="/estudiante/dashboard" routerDirection="root">
            <ion-label>Mi Dashboard</ion-label>
          </ion-item>
        }
      </ion-menu-toggle>
    </ion-list>
  </ion-content>
</ion-menu>

<div class="ion-page" id="main-content">
  <ion-router-outlet></ion-router-outlet>
</div>
```