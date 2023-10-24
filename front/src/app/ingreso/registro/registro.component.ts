import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment";
import {CognitoUserAttribute, CognitoUserPool} from "amazon-cognito-identity-js"
import {Iuser} from "../../services/models/iuser";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {
  public formMail: string;
  public formPassword: string="";
  public formRePassword: string="";
  public formTerminos: boolean = false;
  public registradoOK: boolean = false;
  public registradoFAIL: boolean = false;
  public mesage: string;
  public loading: boolean;

  constructor() {
    this.loading = false

  }

  ngOnInit(): void {
  }

  close(){
    this.registradoFAIL = false;
    this.registradoOK = false;
  }

  onRegister(): void {
    let emailRegex = /^(?:[^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*|"[^\n"]+")@(?:[^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,63}$/i;
    if (!emailRegex.test(this.formMail)) {
      this.registradoFAIL = true;
      this.mesage = "El email no es valido"
      return;
    }

    if (this.formPassword.trimStart().length == 0) {
      this.registradoFAIL = true;
      this.mesage = "Los Password no pueden estar vacios"
      return;
    }
    if (this.formPassword.trimStart().length < 8) {
      this.registradoFAIL = true;
      this.mesage = "Los Password deben contener minimo 8 caracteres"
      return;
    }
    if (this.formPassword != this.formRePassword) {
      this.registradoFAIL = true;
      this.mesage = "Los Password deben ser iguales"
      return;
    }
   let regexp_password = /^(?:[a-z]+[A-Z]+[0-9])/i;
    if (!regexp_password.test(this.formPassword)) {
      this.registradoFAIL = true;
      this.mesage = "El Password debe contener minusculas, mayusculas y numeros"
      return;
    }
    if(!this.formTerminos){
      this.registradoFAIL = true;
      this.mesage = "Debe aceptar los terminos y condiciones"
      return;
    }
    var poolData = {
      UserPoolId: environment.UserPoolId,  // Su id de grupo de usuarios aquí
      ClientId: environment.ClientId,// Su id de cliente aquí
    };

    var userPool = new CognitoUserPool(poolData);
    var attributeList = [];
    var iuser: Iuser = {
      name: "",
      email: this.formMail,
    }
    for (var key in iuser) {
      var attrData = {
        Name: key,
        Value: iuser[key]
      }
      var attr = new CognitoUserAttribute(attrData);
      attributeList.push(attr);
    }
    this.loading = true
    userPool.signUp(this.formMail, this.formPassword, attributeList, [], (err, result) => {
        if (err) {
          this.mesage = 'Vuelva a intentarlo más tarde.';
          if (err.message == "An account with the given email already exists.")
            this.mesage = 'El usuario ya se encuentra registrado.';
          this.loading = false

          this.registradoFAIL = true;
          return;
        }
        var cognitoUser = result.user;
        this.registradoFAIL = false;
        this.registradoOK = true;
        this.loading = false
      }
    )
  }
}
