import {Component, OnInit} from '@angular/core';
import {AppComponent} from "../../../app.component";
import {ApiRestService} from "../../../services/api-rest.service";
import {CognitoUserAttribute, CognitoUserPool} from "amazon-cognito-identity-js";
import {IuserDto} from "../../../services/models/iuserDto";
import {environment} from "../../../../environments/environment";
import {AuthService} from "../../../services/auth/auth.service";
import { FormBuilder, Validators } from '@angular/forms';
import { formatDate } from '@angular/common';
import { ValidationService } from 'src/app/services/validation.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  public usuario: any = [];
  public temaSeleccionado: string = 'Claro';
  public formMail: string;
  public formImage: string = null;
  private atributes: CognitoUserAttribute[];
  public user: any = [];
  public error: string;
  public errorStyle: string = "";
  public success: string = "";
  public loading: boolean;
  public imageProfile: any;
  public historico:any;
  public errorImg: string;
  public updateDataForm: any;

  public facultades: any = [];

  constructor(private _builder:FormBuilder, private serviceAppComponent: AppComponent, private apiRest: ApiRestService, private authSeervice: AuthService) {
    this.updateDataForm = this._builder.group({
      firstName: [this.usuario.firstName, [Validators.required, ValidationService.textValidator]],
      lastName: [this.usuario.lastName, [Validators.required, ValidationService.textValidator]],
      birthday: [this.usuario.birthday, Validators.required],
      phone: [this.usuario.phone, Validators.required],
      facultad: ['', Validators.required]
    })
    this.getUser();
    this.verUsuario();
    this.getAtributes();
    this.getHistorico();
    this.getFacultades();
    this.loading = true;
  }

  ngOnInit(): void {

  }

  private getFacultades(): void {
    this.loading=true;
    this.apiRest.getFacultades().subscribe(
      data => {
        this.facultades = data
        if(data)
          this.loading=false
      },
      error => {
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
        this.loading=false
      }
    );
  }

  close() {
    this.error = null;
    this.success = null;
  }

  private getHistorico(){
    this.loading = true
    this.apiRest.getHistorico().subscribe(data => {
        this.historico = data;
        if (this.usuario){
          this.loading = false
      }},
      error => {
        this.loading = false
      });
  }

  public onRegister() {
    this.error = "";
    this.errorStyle = "";

    let fecha = new Date(this.updateDataForm.controls['birthday'].value);

    var iuserDto: IuserDto = {
      id: null,
      firstName: this.updateDataForm.controls['firstName'].value,
      lastName: this.updateDataForm.controls['lastName'].value,
      birthday: (fecha.getTime() + fecha.getTimezoneOffset() * 60000).toString(),
      valoration: null,
      email: this.formMail,
      phone: this.updateDataForm.controls['phone'].value.toString(),
      idFacultad: (this.updateDataForm.controls['facultad'].value as number)
    }

    this.apiRest.createUser(iuserDto).subscribe(
      data => {
        this.getUser();
        this.success = "Datos guardados exitosamente"
        if(data)
          this.loading = false;

      },
      error => {
        this.error = "Error al guardar los datos, intentelo en otro momento";
        this.loading = false;

      }
    );
  }

  private getAtributes(): void {
    this.loading = true
    var poolData = {
      UserPoolId: environment.UserPoolId,  // Su id de grupo de usuarios aquí
      ClientId: environment.ClientId,  // Su id de cliente aquí
    };
    var userPool = new CognitoUserPool(poolData);
    var currentUser = userPool.getCurrentUser();
    currentUser.getSession((error: any, session: any) => {
      if (error) {
        
        this.error = "Error en el servidor. Pruebe recargar el sitio."
        this.loading = false
        return;
      }
      currentUser.getUserAttributes((err, result) => {
        if (err) {
          console.log(err.message || JSON.stringify(err))
          this.loading = false
          return;
        }
        this.atributes = result;
        this.atributes.forEach((attr: CognitoUserAttribute) => {
          if (attr.Name == 'email') {
            this.formMail = attr.Value
            if (this.usuario)
              this.loading = false
          }
        });
      })
    })
  }

  public cargarTema() {
    this.serviceAppComponent.cambiarEstilos(this.temaSeleccionado);
  }

  private getUser() {
    this.loading = true
    this.apiRest.getUser().subscribe(data => {
        this.usuario = data;
        this.updateDataForm.controls['firstName'].setValue(this.usuario.firstName)
        this.updateDataForm.controls['lastName'].setValue(this.usuario.lastName)
        this.updateDataForm.controls['birthday'].setValue(formatDate(this.usuario.birthday, 'yyyy-MM-dd', 'en'))
        this.updateDataForm.controls['phone'].setValue(this.usuario.phone)
        this.updateDataForm.controls['facultad'].setValidators([])
        this.updateDataForm.controls['facultad'].updateValueAndValidity()
        if (this.usuario){
          this.loading = false
          this.getImageProfile();
      }},
      error => {
        this.loading = false
      });
  }

  public getImageProfile(){
    this.loading=true;
    this.apiRest.getUrlImageProfile(this.usuario.id).subscribe(data =>{
      this.imageProfile=data.url;
      if(data)this.loading=false;
    },error => {
      this.loading=false;
    })
  }

  private verUsuario() {
    this.loading = true
    this.apiRest.verUsuario(this.authSeervice.getId()).subscribe(data => {
        this.user = data;
        if (this.usuario)
          this.loading = false
      },
      error => {
        this.loading = false
      });
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
      formularioDeDatos.append('idUser',this.usuario.id)
      formularioDeDatos.append('name',fileCapture.name)
      formularioDeDatos.append('userName',this.usuario.firstName+' '+this.usuario.lastName)
      this.apiRest.uploadImgPerfil(formularioDeDatos).subscribe(data=>{
        if(data){
          this.getImageProfile()
          this.loading=false
      }},error=>{
          this.loading=false
        }
      )
    } catch (e) {
      this.loading=false
    }
  }


}
