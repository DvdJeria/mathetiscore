import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonContent,
  IonHeader,
  IonTitle,
  IonToolbar,
  IonMenuButton,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent,
  IonCardSubtitle,
  IonButton,
  IonIcon,
  IonButtons
} from '@ionic/angular';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-gestion-usuarios',
  templateUrl: './gestion-usuarios.page.html',
  styleUrls: ['./gestion-usuarios.page.scss'],
  imports: [
    RouterLink,
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    CommonModule,
    FormsModule,
    IonMenuButton,
    IonCard,
    IonCardHeader,
    IonCardTitle,
    IonCardContent,
    IonCardSubtitle,
    IonButton,
    IonIcon,
    IonButtons
  ]
})
export class GestionUsuariosPage {

  constructor() { }

}
