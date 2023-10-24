import { Component, OnInit } from '@angular/core';
import import_grupos from './mis-grupos-mock.json'
import {ApiRestService} from "../../../services/api-rest.service";
import {Group} from "../../../services/models/group";

@Component({
  selector: 'app-mis-grupos',
  templateUrl: './mis-grupos.component.html',
  styleUrls: ['./mis-grupos.component.css']
})
export class MisGruposComponent implements OnInit {

  public grupos= import_grupos;
  public misGrupos: any;
  pages: number = 1;
  public loading: boolean;

  constructor(private apiRest: ApiRestService) {
    this.loading = true
    this.getMisGrupos();
  }

  ngOnInit(): void {
    //this.grupos;
  }

  private getMisGrupos(): void {
    this.loading = true
    this.apiRest.misGrupos().subscribe(
        data => {
          this.misGrupos = data;
          console.log(data)
          if(data)
            this.loading = false
        },
        error => {
          console.log("Error al cargar mis grupos")
          this.loading = false
        }
    );
  }
}
