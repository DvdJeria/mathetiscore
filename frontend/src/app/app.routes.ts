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

  // --- MÓDULO ADMINISTRACIÓN ---
  {
    path: 'admin/gestion-usuarios',
    loadComponent: () => import('./pages/admin/gestion-usuarios/gestion-usuarios.page').then(m => m.GestionUsuariosPage),
    canActivate: [authGuard, rolGuard([Role.SUPER_ADMIN, Role.ADMIN_SEDE])]
  },

  // --- MÓDULO DOCENTE ---
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

  // --- MÓDULO ESTUDIANTE / APODERADO ---
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

  // --- RUTA COMODÍN PARA RUSTAS NO ENCONTRADAS ---
  {
    path: '**',
    redirectTo: 'login'
  }
];
