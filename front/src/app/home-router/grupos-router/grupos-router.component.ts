import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from 'src/app/services/auth/auth.service';
import {Group} from 'src/app/services/models/group';
import {ApiRestService} from "../../services/api-rest.service";

@Component({
  selector: 'app-grupos-router',
  templateUrl: './grupos-router.component.html',
  styleUrls: ['./grupos-router.component.css']
})
export class GruposRouterComponent implements OnInit {

  public grupo: Group = {};
  public imageProfile: any;
  public loading: boolean;
  public formEmailInvitado: string;
  public error: boolean;
  public success: boolean;
  public mesage: string;
  public isAdmin: boolean = false;
  public errorImg: string;

  constructor(private route: ActivatedRoute, private apiRest: ApiRestService, private router: Router, private auth: AuthService) {
    this.route.params.subscribe(params => {
      this.apiRest.infoGrupo(params['id']).subscribe(
        data => {
          this.grupo = data;
          this.getGroupImageProfile()
          if (this.grupo.idUserAdmin == this.auth.getId())
            this.isAdmin = true;
        },
        error => {
        }
      );
    });
  }

  ngOnInit(): void {
  }

  mostrarMenu(): void {
    var menu = document.getElementsByClassName("sidemenu-hidden")[0]
    var buttonMenu = document.getElementsByClassName("menu-toggle")[0]

    if (menu == null) {
      menu = document.getElementsByClassName("sidemenu-show")[0]
      menu.className = menu.className.replace("sidemenu-show", "sidemenu-hidden")
      buttonMenu.classList.remove("sidemenu-button-move")
    } else {
      menu.className = menu.className.replace("sidemenu-hidden", "sidemenu-show")
      buttonMenu.classList.add("sidemenu-button-move")
    }
  }

  abandonarGrupo(id: any) {
    this.loading = true
    this.apiRest.abandonarGrupo(id).subscribe(data => {
      this.loading = false;
      this.router.navigate(["home"]);
    }, error => {
      this.loading = false;
    })
  }

  public onImagenPerfil(event: any): any {
    let tiposFormatos = ["image/png", "image/jpg", "image/jpeg", "image/bmp"]
    this.errorImg = "";
    console.log(event.target.files[0]);
    const fileCapture = event.target.files[0];

    if (!tiposFormatos.includes(fileCapture.type)) {
      this.errorImg = "Debe subir una imagen en estos formatos: png, jpg, jpeg, bmp ";
      return
    }
    if (fileCapture.size > 5e+6) {
      this.errorImg = "No se puede subir imágenes mayores a 5Mb";
      return
    }
    this.loading = true

    try {
      const formularioDeDatos = new FormData();
      formularioDeDatos.append('file', fileCapture);
      formularioDeDatos.append('idUser', localStorage.getItem("CognitoIdentityServiceProvider.v5qnauc2t152bap78u7djjjjr.LastAuthUser"))
      formularioDeDatos.append('idGroup', this.grupo.id as unknown as string)
      formularioDeDatos.append('name', fileCapture.name)
      formularioDeDatos.append('userName', "null")
      this.apiRest.uploadGroupImgPerfil(formularioDeDatos).subscribe(data => {
          console.log("SUBIDO " + data)
          if (data) {
            this.getGroupImageProfile()
            this.loading = false
          }
        }, error => {
          this.loading = false
          console.log("error")
        }
      )
    } catch (e) {
      this.loading = false
      console.log("error")
      console.log("ERROR", e)
    }
  }

  enviarInvitacion() {
    const requestJoinDto = {
      groupId: this.grupo.id,
      accepted: false,
      usuario: this.formEmailInvitado
    };

    this.apiRest.invitarEstudianteEmail(requestJoinDto).subscribe(
      data => {
        this.mesage = "Solicitud Enviada"
        this.success = true;
      },
      error => {
        if (error.error.message.length > 50)
          this.mesage = "Error inesperado pruebe nuevamente."
        else if (error.error.message === "The request join already exist")
          this.mesage = "Ya invitó a ese usuario!"
        else
          this.mesage = error.error.message
        this.error = true
      }
    );

  }

  public getGroupImageProfile() {
    this.apiRest.getGroupUrlImageProfile(this.grupo.id).subscribe(data => {
      this.imageProfile = data.url;
    }, error => {

    })
  }
}
