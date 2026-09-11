import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from "@angular/router";
import {
  IonContent,
  IonHeader,
  IonTitle,
  IonToolbar,
  IonItem,
  IonLabel,
  IonInput,
  IonButton,
  IonSpinner
} from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  imports: [
    CommonModule,
    FormsModule,
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonItem,
    IonLabel,
    IonInput,
    IonButton,
    IonSpinner]
})
export class LoginPage {

  //Inyección del AuthService
  private authService = inject(AuthService);
  private router = inject(Router);

  //Variables enlazadas al formulario
  email = '';
  password = '';
  loading = false;
  errorMessage = '';

  async onLogin(){
    if(!this.email || !this.password){
      this.errorMessage = 'Por favor ingrese correo y contraseña';
    }

    this.loading = true;
    this.errorMessage = '';

    try {
      const { data, error } = await this.authService.login(this.email, this.password);

      if(error){
        this.errorMessage = `Error ${error.message}`;
      }else {
        this.router.navigate(['/home']);
        //Limpiar variables del formulario
        this.email = '';
        this.password = '';
      }
    } catch (err){
      this.errorMessage = 'Ocurrió un error inesperado al conectar con el servidor.';
      console.error(err);
    }finally {
      this.loading = false;
    }
  }

}
