import {Component, OnInit} from '@angular/core';
import {ApiRestService} from "../../../services/api-rest.service";

@Component({
  selector: 'app-mis-valoraciones',
  templateUrl: './mis-valoraciones.component.html',
  styleUrls: ['./mis-valoraciones.component.css']
})
export class MisValoracionesComponent implements OnInit {
  public valoraciones: any = [];
  public loading: boolean;


  constructor(private apiRestService: ApiRestService) {
    this.getValoration();
  }

  ngOnInit(): void {
  }
  public getValoration(): void {
    this.loading = true
    this.apiRestService.getValoration().subscribe(data => {
        this.valoraciones = data;
        this.loading = false
      },
      err => {
        console.log("Sin valoraciones pendientes" + err)
        this.loading = false
      });
  }
}
