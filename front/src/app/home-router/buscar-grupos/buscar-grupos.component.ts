import { Component, OnInit } from '@angular/core';
import import_grupos from './grupos-mock.json'
import { ApiRestService } from "../../services/api-rest.service";
import { AuthService } from "../../services/auth/auth.service";
import { ISearchGroup } from 'src/app/services/models/ISearchGroup';

@Component({
  selector: 'app-buscar-grupos',
  templateUrl: './buscar-grupos.component.html',
  styleUrls: ['./buscar-grupos.component.css']
})
export class BuscarGruposComponent implements OnInit {
  public grupos: any = [];
  public misRecomendaciones: any = [];
  public misMaterias: any = []
  public tags: any = []
  public loading: boolean;
  public materia: string = "Materia";
  public tag: string = "Tags";
  public filtros: ISearchGroup = {
    idUser: this.authService.getId(),
    pagina: '0',
    materia: '',
    tag: ''
  }

  pages: number = 1;

  constructor(private apiRest: ApiRestService, private authService: AuthService) {
    this.loading = true;
    this.getRecomendados(this.filtros)
    this.getMaterias()
    this.getTags()
  }

  ngOnInit(): void {
  }

  private getTags(){
    this.apiRest.getTags().subscribe(
      data=>{
        this.tags = data
      }
    )
  }

  private getMaterias(){
    this.apiRest.verUsuario(this.authService.getId()).subscribe(
      data=>{
        this.misMaterias = [...new Set([...data.materiasDia,...data.materiasTarde,...data.materiasNoche])]
      }
    )
  }

  private getRecomendados(filtros: ISearchGroup): void {
    this.apiRest.gruposRecomendados(filtros).subscribe(
      data => {
        this.misRecomendaciones = data;
        if (data)
          this.loading = false
      },
      error => {
        this.loading = false
        console.log("Error al cargar mis grupos")
      }
    );
  }

  filtrar(){
    var nuevoFiltros :ISearchGroup = {
      idUser: this.filtros.idUser,
      pagina: '0',
      materia: this.materia != "Materia" ? this.materia : '',
      tag: this.tag != "Tags" ? this.tag : ''
    }

    this.getRecomendados(nuevoFiltros);

  }

  borrarFiltros(){
    this.materia = "Materia"
    this.tag = "Tags"

    this.filtrar()
  }

}


