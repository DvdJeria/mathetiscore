import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import {UserService} from "../services/user.service";
import {Role} from "../models/roles.enum"

export const rolGuard = (allowedRoles: Role[]): CanActivateFn => {
  return () => {
    const userService = inject(UserService);
    const router = inject(Router);

    const user = userService.userProfile();
    const userRole = user?.rolNombre as Role;

    //Paso uno: Es verificar si existe el perfil cargado
    if (!userRole) {
      const hasToken = !!localStorage.getItem('token');
      if (!hasToken) {
        router.navigate(['/login']);
        return false;
      }
    }

    if (userRole === Role.SUPER_ADMIN) {
      return true;
    }

    //Paso 2: Verificar si el rol del usuario está dentro de los permitidos para esta ruta
    if(allowedRoles.includes(userRole)){
      return true;
    }

    //Paso 3: Si intenta accedes a una ruta no autorizada, se redirigirá a la correspondiente al rol
    console.warn(`Acceso no autorizado intentado por rol: ${userRole}`);

    switch (userRole){
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
  }
}

