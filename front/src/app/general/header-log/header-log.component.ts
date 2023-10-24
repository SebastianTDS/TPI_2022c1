import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CognitoUserAttribute, CognitoUserPool} from "amazon-cognito-identity-js"
import {environment} from "../../../environments/environment";
import {ApiRestService} from "../../services/api-rest.service";

@Component({
  selector: 'app-header-log',
  templateUrl: './header-log.component.html',
  styleUrls: ['./header-log.component.css']
})
export class HeaderLogComponent implements OnInit {
  public image: string;
  public firstName: string;
  public lastName: string;
  private idUser: string;
  private atributes: CognitoUserAttribute[];
  public notification: number = 0
  public valoration: number = 0;

  constructor(private router: Router, private apiRest: ApiRestService) {
    this.getUser();
    this.getNotification();
    let timerUser = setInterval(() => this.getUser(), 60000);
    let timerNotification = setInterval(() => this.getNotification(), 60000);
   // let timereventReminder = setInterval(() => this.eventReminder(), 60000);

  }

  ngOnInit(): void {
  }


  public getUser(): void {
    this.apiRest.getUser().subscribe(data => {
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.idUser = data.id;
        this.getImageProfile();
      },
      error => {
        console.log("Usuario no registrado")
      });
  }


  private getAtributes(): void {
    var poolData = {
      UserPoolId: environment.UserPoolId,  // Su id de grupo de usuarios aquí
      ClientId: environment.ClientId,  // Su id de cliente aquí
    };
    var userPool = new CognitoUserPool(poolData);
    var currentUser = userPool.getCurrentUser();
    currentUser.getSession((error: any, session: any) => {
      if (error) {
        alert(error.message || JSON.stringify(error))
        return;
      }
      currentUser.getUserAttributes((err, result) => {
        if (err) {
          alert(err.message || JSON.stringify(err))
          return;
        }
        this.atributes = result;
        this.atributes.forEach((attr: CognitoUserAttribute) => {
          if (attr.Name == 'name')
            return

        });
      })
    })
  }

  public onLogOut(): void {
    var poolData = {
      UserPoolId: environment.UserPoolId,  // Su id de grupo de usuarios aquí
      ClientId: environment.ClientId,  // Su id de cliente aquí
    };
    var userPool = new CognitoUserPool(poolData);
    var currentUser = userPool.getCurrentUser();
    currentUser.signOut();

    this.router.navigate(['/'])
  }

  public getImageProfile() {
    this.apiRest.getUrlImageProfile(this.idUser).subscribe(data => {
      this.image = data.url;
    }, error => {
      console.log("error al cargar imagen de perfil")
    })
  }

  private getNotification() {
    this.apiRest.getNotification().subscribe(data => {
      this.notification = data.length;
    }, error => {
      this.notification = 0;
    });
    this.apiRest.getValoration().subscribe(data => {
      this.valoration = data.length;
    }, error => {
      this.valoration = 0;
    });
  }

  private eventReminder() {
    this.apiRest.reminderCalendar().subscribe(data => {
    }, error => {
      console.log("Error al ejecutar el event reminder")
    });
  }
}
