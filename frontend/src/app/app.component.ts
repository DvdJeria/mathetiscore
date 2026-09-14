import { Component, inject, computed } from '@angular/core';
import { UserService } from './core/services/user.service'
import { Role } from './core/models/roles.enum';
import {
  IonApp,
  IonMenu,
  IonContent,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonList,
  IonMenuToggle,
  IonItem,
  IonRouterOutlet,
  IonButton,
  IonLabel
} from '@ionic/angular';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  imports: [
    IonApp,
    IonMenu,
    IonContent,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonList,
    IonMenuToggle,
    IonItem,
    IonRouterOutlet,
    IonLabel,
    RouterLink
  ],
})
export class AppComponent {
  //constructor() {}

  private userService = inject(UserService);

  //Exponemos el enum al template para comparaciones seguras
  readonly Role = Role;

  //Signal computada reactiva con el rol del usuario
  readonly userRole = computed(() => this.userService.userProfile()?.rolNombre as Role);
}
