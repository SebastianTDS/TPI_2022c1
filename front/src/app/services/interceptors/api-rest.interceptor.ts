import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HTTP_INTERCEPTORS
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../auth/auth.service";
import {environment} from "../../../environments/environment";

@Injectable()
export class ApiRestInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {

  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let intReq = request;
    const token = this.authService.getToken();
    if (token != null) {
      intReq = request.clone({headers: request.headers.set(environment.AUTHORIZATION, environment.BEARER + token)})
    }

    return next.handle(intReq);
  }
}

export const inteceptorSpringProvider= [{provide:HTTP_INTERCEPTORS,useClass:ApiRestInterceptor,multi:true}]
