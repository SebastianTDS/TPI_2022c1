import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiRestService } from 'src/app/services/api-rest.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ISearchGroup } from 'src/app/services/models/ISearchGroup';

@Component({
  selector: 'app-buscar-integrantes',
  templateUrl: './buscar-integrantes.component.html',
  styleUrls: ['./buscar-integrantes.component.css']
})
export class BuscarIntegrantesComponent implements OnInit {

  public estudiantes: any;
  public loading: boolean;
  public misRecomendaciones: any = [];
  public grupo: any;
  public filtros: ISearchGroup;
  public materia: string = "Materia";
  public tag: string = "Tags";
  public misMaterias: any = []
  public tags: any = []

  pages: number = 1;

  constructor(private route: ActivatedRoute, private apiRest: ApiRestService, private authService: AuthService) {

    this.route.parent?.params.subscribe(params => {
      this.filtros = {
        idGroup: params['id'],
        pagina: '0',
        tag: '',
        materia: '',
      }
      this.getRecomendados(this.filtros);
      this.getMaterias()
      this.getTags()
    });
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

  getRecomendados(filtros: any) {
    this.loading = true

    this.apiRest.estudiantesRecomendados(filtros).subscribe(
      data => {
        this.misRecomendaciones = data;
        this.loading = false
      },
      error => {
        this.loading = false
      }
    );
  }

  ngOnInit(): void {
  }

  filtrar(){
    var nuevoFiltros :ISearchGroup = {
      idGroup: this.filtros.idGroup,
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
