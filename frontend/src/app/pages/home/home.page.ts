import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonButton,
  IonButtons,
  IonMenuButton,
  IonIcon,
  IonSpinner
} from '@ionic/angular';
import { addIcons } from "ionicons";
import { logOutOutline, menuOutline} from "ionicons/icons";
import {AuthService} from "../../core/services/auth.service";
import { UserService } from "../../core/services/user.service";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: true,
  imports: [
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonButton,
    IonButtons,
    IonMenuButton,
    IonIcon,
    IonSpinner
  ],
})
export class HomePage {

  private authService= inject(AuthService);
  private router= inject(Router);
  private userService= inject(UserService);

  readonly userProfile = this.userService.userProfile;

  constructor() {
    addIcons({ logOutOutline, menuOutline });
  }

  get nombreCompleto(): string {
    const u = this.userProfile();
    if (!u) return '';
    return `${u.nombre} ${u.apellidoPaterno} ${u.apellidoMaterno || ''}`.trim();
  }

  async onLogout(){
    await this.authService.logout();
    this.router.navigate(['/login']);
  }
}
