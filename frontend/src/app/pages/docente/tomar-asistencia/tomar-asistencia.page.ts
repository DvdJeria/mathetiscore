import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonContent, IonHeader, IonTitle, IonToolbar } from '@ionic/angular';

@Component({
  selector: 'app-tomar-asistencia',
  templateUrl: './tomar-asistencia.page.html',
  styleUrls: ['./tomar-asistencia.page.scss'],
  imports: [IonContent, IonHeader, IonTitle, IonToolbar, CommonModule, FormsModule]
})
export class TomarAsistenciaPage implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
