import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {CognitoUser, CognitoUserPool, AuthenticationDetails} from "amazon-cognito-identity-js"
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public formPassword: string="";
  public formMail: string="";
  public formRemember: boolean = false;
  public registradoFAIL: boolean;
  public mesage:string;
  public loading: boolean;

  constructor(private router: Router, private auth: AuthService) {
    this.loading = false

  }

  ngOnInit(): void {
  }

  close(){
    this.registradoFAIL = false;
  }

  onLogin(): void {
    let emailRegex = /^(?:[^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*|"[^\n"]+")@(?:[^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,63}$/i;
    if (!emailRegex.test(this.formMail)) {
      this.registradoFAIL = true;
      this.mesage = "El email no es valido"
      return;
    }

    if (this.formPassword.trimStart().length == 0) {
      this.registradoFAIL = true;
      this.mesage = "El Password no puede estar vacio"
      return;
    }

    // @ts-ignore
    var poolData = {
      UserPoolId: environment.UserPoolId,  // Su id de grupo de usuarios aquí
      ClientId: environment.ClientId,  // Su id de cliente aquí
    };
    var userPool = new CognitoUserPool(poolData);

    var authenticationData = {
      Username: this.formMail,
      Password: this.formPassword,
    };
    var userData = {
      Username: this.formMail,
      Pool: userPool
    };
    var cognitoUser = new CognitoUser(userData);
    var authenticationDetails = new AuthenticationDetails(authenticationData);

    cognitoUser.authenticateUser(authenticationDetails, {
      onSuccess: (result) => {
        var accessToken = result.getAccessToken().getJwtToken();

        this.loading = false

        this.auth.isEnabled(this.formMail).subscribe(data =>{
          this.router.navigate(['/register-complete']);
        }, error => {
          var userPool = new CognitoUserPool(poolData);
          var currentUser = userPool.getCurrentUser();
          currentUser.signOut();
          
          this.mesage = 'Su usuario se encuentra baneado. Contáctese con el soporte para solicitar una revisión.';
          this.registradoFAIL = true;
        })
        
      },
      onFailure: (err) => {
        this.mesage = 'Vuelva a intentarlo más tarde.';
        if (err.message == "Incorrect username or password.")
          this.mesage = 'Credenciales incorrectas.';
        this.loading = false
        this.registradoFAIL = true;
        return;
      }
    });


  }
}
