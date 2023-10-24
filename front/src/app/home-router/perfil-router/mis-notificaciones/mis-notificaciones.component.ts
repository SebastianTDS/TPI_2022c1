import {Component, OnInit} from '@angular/core';
import import_notificacion from './notificacion-mock.json'
import {ApiRestService} from "../../../services/api-rest.service";

@Component({
  selector: 'app-mis-notificaciones',
  templateUrl: './mis-notificaciones.component.html',
  styleUrls: ['./mis-notificaciones.component.css']
})
export class MisNotificacionesComponent implements OnInit {
  public notificaciones: any = [];
  public loading: boolean;

  constructor(private apiRestService: ApiRestService) {
    this.loading = true
    this.getNotification();
  }

  ngOnInit(): void {
    this.notificaciones;
  }

  private getNotification() {
    this.loading = true
    this.apiRestService.getNotification().subscribe(data => {
      this.notificaciones = data
      if (data)
        this.loading = false
    }, error => {
      this.loading = false
    })
  }
}
