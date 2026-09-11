import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent, IonButton
} from '@ionic/angular';
import {AuthService} from "../core/services/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: true,
  imports: [IonHeader, IonToolbar, IonTitle, IonContent, IonButton],
})
export class HomePage {

  private authService= inject(AuthService);
  private router= inject(Router);

  constructor() {}

  async onLogout(){
    await this.authService.logout();
    this.router.navigate(['/login']);
  }
}
