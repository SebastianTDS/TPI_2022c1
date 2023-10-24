import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {CognitoUserPool} from "amazon-cognito-identity-js";
import {Observable} from "rxjs";
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) {
  }

  public isAuth(): boolean {
    var isAuth: boolean = false;
    var poolData = {
      UserPoolId: environment.UserPoolId,  // Su id de grupo de usuarios aquí
      ClientId: environment.ClientId,  // Su id de cliente aquí
    };

    var userPool = new CognitoUserPool(poolData);
    var currentUser = userPool.getCurrentUser();
    if (currentUser != null) {
      currentUser.getSession((error: any, session: any) => {
        if (error) {
          console.log(error.message || JSON.stringify(error))
        }
        isAuth = session.isValid();
      });
    }
    return isAuth;
  }


  public getId(): string {
    for (let i = 0; i < localStorage.length; i++) {
      if (localStorage.key(i).endsWith("LastAuthUser") && localStorage.key(i).includes(environment.ClientId)) {
        return localStorage.getItem(localStorage.key(i))
      }
    }
    return null;
  }

  public getToken(): string {
    for (let i = 0; i < localStorage.length; i++) {
      if (localStorage.key(i).endsWith(environment.ACCESS_TOKEN) && localStorage.key(i).includes(environment.ClientId)) {
        return localStorage.getItem(localStorage.key(i))
      }
    }
    return null;
  }

  public isEnabled(email: string): Observable<any> {
    return this.httpClient.get<any>(environment.API_STUDENT+ `student/check-status/${email}`);
  }


}



