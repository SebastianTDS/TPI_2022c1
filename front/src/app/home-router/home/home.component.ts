import {Component, OnInit} from '@angular/core';
import import_grupos from '../../home-router/perfil-router/mis-grupos/mis-grupos-mock.json'
import {ApiRestService} from "../../services/api-rest.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public grupos = import_grupos;
  public misGrupos: any = [];
  public loading: boolean;

  constructor(private apiRest: ApiRestService) {
    this.loading = true
    this.getMisGrupos()
  }

  ngOnInit(): void {
    this.grupos;

  }

  private getMisGrupos(): void {
    this.loading = true
    this.apiRest.misGrupos().subscribe(
      data => {
        this.misGrupos = data;
        if (data)
          this.loading = false
      },
      error => {
        console.log("Error al cargar mis grupos")
        this.loading = false
      }
    );
  }
}


