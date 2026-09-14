import {Injectable, inject} from '@angular/core';
import {createClient, SupabaseClient} from "@supabase/supabase-js";
import {environment} from '../../../environments/environment';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly userService =inject(UserService);
  private supabase: SupabaseClient

  constructor() {
    this.supabase = createClient(environment.supabaseUrl, environment.supabaseKey)

    //Escucha los cambios de estado en la autenticación de Supabase (Login, Logout, AutoLogin)
    this.supabase.auth.onAuthStateChange((event, session) => {
      if(session?.user){
        //Usuario autenticado en Supabase -> Cargar perfil desde Springboot
        this.userService.fetchUserProfile(session.user.id).subscribe({
          error: (err) => console.error('Error al sincronizar perfil con Spring Boot:', err),
        })
      }else {
        //Sesión cerrada, limpiar estado de la memoria
        this.userService.clearProfile();
      }
    });

  }

  /*
  * Autentica un usuario con email y contraseña utilizando Supabase Auth
  * @param email Correo del usuario
  * @param password Contraseña del usuario
  * @returns La respuesta de Supabase con la sesión o el error
  *
  */
  async login(email: string, password:string){
    const responde = await this.supabase.auth.signInWithPassword({
      email: email,
      password: password
    });

    return responde;
  }

  // Retorna la sesión actual del usuario en Supabase
  async getSession(){
    const { data } = await this.supabase.auth.getSession();
    return data.session;
  }

  //Cierra la sesión activa en Supabase
  async logout(){
    const { error } = await this.supabase.auth.signOut();
    if(error){
      console.error('Error al cerrar sesión: ', error.message);
    }
  }

}
