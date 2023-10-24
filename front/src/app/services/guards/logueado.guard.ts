import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {catchError, map, Observable, of} from 'rxjs';
import {AuthService} from "../auth/auth.service";
import {ApiRestService} from "../api-rest.service";
import * as Rx from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LogueadoGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    var isAuth = this.authService.isAuth();


    if (isAuth) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }

  }
}

