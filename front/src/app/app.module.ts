import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {LandingPageComponent} from './landing-page/landing-page.component';
import {LoginComponent} from './ingreso/login/login.component';
import {RegistroComponent} from './ingreso/registro/registro.component';
import {HeaderSinLogComponent} from './general/header-sin-log/header-sin-log.component';
import {HeaderLogComponent} from './general/header-log/header-log.component';
import {FooterComponent} from './general/footer/footer.component';
import {HomeRouterComponent} from './home-router/home-router.component';
import {HomeComponent} from './home-router/home/home.component';
import {PerfilComponent} from './home-router/perfil-router/perfil/perfil.component';
import {BuscarGruposComponent} from './home-router/buscar-grupos/buscar-grupos.component';
import {HerramientasGeneralesComponent} from './home-router/herramientas_generales_router/herramientas-generales.component';
import {PerfilRouterComponent} from './home-router/perfil-router/perfil-router.component';
import {MisGruposComponent} from './home-router/perfil-router/mis-grupos/mis-grupos.component';
import {MisNotificacionesComponent} from './home-router/perfil-router/mis-notificaciones/mis-notificaciones.component';
import {MisValoracionesComponent} from './home-router/perfil-router/mis-valoraciones/mis-valoraciones.component';
import {ForoGeneralComponent} from './home-router/herramientas_generales_router/foro-general/foro-general.component';
import {GruposRouterComponent} from './home-router/grupos-router/grupos-router.component';
import {ForoGrupoComponent} from './home-router/grupos-router/foro-grupo/foro-grupo.component';
import {BuscarIntegrantesComponent} from './home-router/grupos-router/buscar-integrantes/buscar-integrantes.component';
import {RouterModule} from "@angular/router";
import {AppRoutingModule} from './app-routing.module';
import {FiltrosGruposComponent} from './home-router/buscar-grupos/filtros-grupos/filtros-grupos.component';
import {TarjetaGrupoComponent} from './general/tarjeta-grupo/tarjeta-grupo.component';
import {TarjetaMiGrupoComponent} from './general/tarjeta-mi-grupo/tarjeta-mi-grupo.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxPaginationModule} from "ngx-pagination";
import {RatingModule} from "primeng/rating";
import {  TarjetaValoracionComponent} from './home-router/perfil-router/mis-valoraciones/tarjeta-valoracion/tarjeta-valoracion.component';
import {  TarjetaNotificacionComponent} from './home-router/perfil-router/mis-notificaciones/tarjeta-notificacion/tarjeta-notificacion.component';
import {CalendarioComponent} from './home-router/grupos-router/calendario/calendario.component';
import {FullCalendarModule} from '@fullcalendar/angular'; // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid'; // a plugin!
import interactionPlugin from '@fullcalendar/interaction'; // a plugin!
import {ArchivosGrupoComponent} from './home-router/grupos-router/archivos-grupo/archivos-grupo.component';
import {CurvaComponent} from './general/curva/curva.component';
import {GrupoHomeComponent} from './home-router/grupos-router/grupo-home/grupo-home.component';
import {CrearGrupoComponent} from './home-router/grupos-router/crear-grupo/crear-grupo.component';
import {HttpClientModule} from '@angular/common/http';
import {inteceptorSpringProvider} from "./services/interceptors/api-rest.interceptor";
import { InteresesComponent } from './home-router/perfil-router/perfil/intereses/intereses.component';
import {PostComponent} from "./home-router/herramientas_generales_router/foro-general/post/post.component";
import {CKEditorModule} from "ckeditor4-angular";
import { PostGroupComponent } from './home-router/grupos-router/foro-grupo/post-group/post-group.component';
import { LoadingComponent } from './general/loading/loading.component';
import {CarruselComponent} from "./landing-page/carrusel/carrusel.component";
import { StudentImgComponent } from './general/student-img/student-img.component';
import { CommentImgComponent } from './comment-img/comment-img.component';
import { TarjetaEstudianteComponent } from './general/tarjeta-estudiante/tarjeta-estudiante.component';
import { ImgStudentNotificationComponent } from './general/img-student-notification/img-student-notification.component';
import { RecompenzasComponent } from './general/recompenzas/recompenzas.component';
import { ErrorComponent } from './general/error/error.component';
import { SuccessComponent } from './general/success/success.component';
import { PublicidadComponent } from './general/publicidad/publicidad.component';
import { NgxMaskModule } from 'ngx-mask';
import { PhonePipe } from './home-router/perfil-router/perfil/phone.pipe';
import { ControlMessagesComponent } from './general/control-message/ControllMessage.component';
import { NgCircleProgressModule } from 'ng-circle-progress';
import { NgxTippyModule } from 'ngx-tippy-wrapper';


FullCalendarModule.registerPlugins([ // register FullCalendar plugins
  dayGridPlugin,
  interactionPlugin
]);

@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    LoginComponent,
    RegistroComponent,
    HeaderSinLogComponent,
    HeaderLogComponent,
    FooterComponent,
    HomeRouterComponent,
    HomeComponent,
    PerfilComponent,
    BuscarGruposComponent,
    HerramientasGeneralesComponent,
    PerfilRouterComponent,
    MisGruposComponent,
    MisNotificacionesComponent,
    MisValoracionesComponent,
    ForoGeneralComponent,
    GruposRouterComponent,
    ForoGrupoComponent,
    BuscarIntegrantesComponent,
    FiltrosGruposComponent,
    TarjetaGrupoComponent,
    TarjetaEstudianteComponent,
    TarjetaMiGrupoComponent,
    TarjetaValoracionComponent,
    TarjetaNotificacionComponent,
    CalendarioComponent,
    ArchivosGrupoComponent,
    CurvaComponent,
    GrupoHomeComponent,
    CrearGrupoComponent,
    InteresesComponent,
    PostComponent,
    PostGroupComponent,
    LoadingComponent,
    CarruselComponent,
    StudentImgComponent,
    CommentImgComponent,
    ImgStudentNotificationComponent,
    RecompenzasComponent,
    ErrorComponent,
    SuccessComponent,
    PublicidadComponent,
    PhonePipe,
    ControlMessagesComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    RatingModule,
    FullCalendarModule,
    CKEditorModule,
    ReactiveFormsModule,
    NgxMaskModule.forRoot(),
    NgCircleProgressModule.forRoot(),
    NgxTippyModule,
  ],
  providers: [inteceptorSpringProvider],
  bootstrap: [AppComponent]
})
export class AppModule {
}
