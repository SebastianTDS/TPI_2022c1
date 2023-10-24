import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ApiRestService} from "../../../services/api-rest.service";

@Component({
  selector: 'app-archivos-grupo',
  templateUrl: './archivos-grupo.component.html',
  styleUrls: ['./archivos-grupo.component.css']
})
export class ArchivosGrupoComponent implements OnInit {

  public grupo: any
  public archivos: any
  public loading: any;
  public error: string = null;
  public size: string = '0';

  constructor(private route: ActivatedRoute, private apiRest: ApiRestService) {
    this.route.parent?.params.subscribe(params => {
      this.apiRest.infoGrupo(params['id']).subscribe(
        data => {
          this.grupo = data;
          this.getFiles();
          this.checkSpace(this.grupo.id)
        },
        error => {
          console.log("error al mostrar el grupo")
        }
      );
      console.log(this.grupo)
    });
  }

  checkSpace(idGrupo: any){
    this.apiRest.tamanoRepoGrupo(idGrupo).subscribe(data =>{
      this.size = ((data.size / 1024) / 1024).toFixed(3)
    }, error =>{
      this.error = "El archivo que intenta subir excede el límite del repositorio de grupo.";
    })
  }

  ngOnInit(): void {
  }

  descargar(archivo: any) {
    window.open(archivo)
  }

  eliminar(name: any) {
    var fileDto = {
      name: name,
      idGroup: this.grupo.id
    };
    const options = {
      body: fileDto,
    };
    this.apiRest.deleteGroupFile(options).subscribe(data => {
        this.getFiles();
        this.checkSpace(this.grupo.id)
        if (data) {
          this.loading = false
        }
      },
      error => {
        this.loading = false
        this.error = "Fallo en el servidor. Intente nuevamente.";
      });

  }

  close() {
    this.error = null;
} 

  onFileUpload(event: any) {
    this.error = null;
    console.log(event.target.files[0]);
    let fileCapture = event.target.files[0];
    if (fileCapture.size > 1e+7) {
      this.error = "No se puede subir archivos mayores a 10Mb";
      fileCapture=[];
      return
    }
    this.loading = true
    try {
      const formularioDeDatos = new FormData();
      formularioDeDatos.append('file', fileCapture);
      formularioDeDatos.append('idUser', localStorage.getItem("CognitoIdentityServiceProvider.v5qnauc2t152bap78u7djjjjr.LastAuthUser"))
      formularioDeDatos.append('idGroup', this.grupo.id)
      formularioDeDatos.append('name', fileCapture.name)
      formularioDeDatos.append('userName', "null")
      this.apiRest.uploadGroupFile(formularioDeDatos).subscribe(data => {
          console.log("SUBIDO " + data)
          if (data) {
            this.getFiles();
            this.checkSpace(this.grupo.id)
            this.loading = false
          }
        }, error => {
          this.loading = false
          this.error = "Fallo en el servidor. Intente nuevamente.";
        }
      )
    } catch (e) {
      this.loading = false
      this.error = "Fallo en el servidor. Intente nuevamente.";
    }
  }

  private getFiles() {
    this.loading = true;
    this.apiRest.getGroupListFiles(this.grupo.id).subscribe(value => {
      this.archivos = value;
      this.loading = false;
    }, error => {
      this.loading = false;
      this.error = "Fallo en el servidor. Intente recargar la página.";
    })
  }
}
