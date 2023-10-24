import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeRouterComponent} from "./home-router/home-router.component";
import {HomeComponent} from "./home-router/home/home.component";
import {BuscarGruposComponent} from "./home-router/buscar-grupos/buscar-grupos.component";
import {LandingPageComponent} from "./landing-page/landing-page.component";
import {
  HerramientasGeneralesComponent
} from './home-router/herramientas_generales_router/herramientas-generales.component';
import {ForoGeneralComponent} from './home-router/herramientas_generales_router/foro-general/foro-general.component';
import {GruposRouterComponent} from './home-router/grupos-router/grupos-router.component';
import {ForoGrupoComponent} from './home-router/grupos-router/foro-grupo/foro-grupo.component';
import {MisGruposComponent} from "./home-router/perfil-router/mis-grupos/mis-grupos.component";
import {PerfilRouterComponent} from "./home-router/perfil-router/perfil-router.component";
import {PerfilComponent} from "./home-router/perfil-router/perfil/perfil.component";
import {MisNotificacionesComponent} from "./home-router/perfil-router/mis-notificaciones/mis-notificaciones.component";
import {MisValoracionesComponent} from "./home-router/perfil-router/mis-valoraciones/mis-valoraciones.component";
import {BuscarIntegrantesComponent} from './home-router/grupos-router/buscar-integrantes/buscar-integrantes.component';
import {CalendarioComponent} from './home-router/grupos-router/calendario/calendario.component';
import {LoginComponent} from "./ingreso/login/login.component";
import {RegistroComponent} from "./ingreso/registro/registro.component";
import {GrupoHomeComponent} from './home-router/grupos-router/grupo-home/grupo-home.component';
import {CrearGrupoComponent} from "./home-router/grupos-router/crear-grupo/crear-grupo.component";
import {LoginGuard} from "./services/guards/login.guard";
import {LogueadoGuard} from "./services/guards/logueado.guard";
import {InteresesComponent} from "./home-router/perfil-router/perfil/intereses/intereses.component";
import { ArchivosGrupoComponent } from './home-router/grupos-router/archivos-grupo/archivos-grupo.component';


const routes: Routes = [

  {path: '', component: LandingPageComponent, canActivate: [LoginGuard]},

  {path: 'login', component: LoginComponent, canActivate: [LoginGuard]},

  {path: 'register', component: RegistroComponent, canActivate: [LoginGuard]},

  {
    path: 'home', component: HomeRouterComponent, canActivate: [LogueadoGuard],

    children: [
      {path: '', component: HomeComponent},
      {path: 'mis-grupos', component: MisGruposComponent},
      {path: 'buscar-grupos', component: BuscarGruposComponent},
      {path: 'crear-grupo', component: CrearGrupoComponent},
      {
        path: 'tools',
        component: HerramientasGeneralesComponent,

        children: [
          {path: 'forum', component: ForoGeneralComponent}
        ]
      },
      {
        path: 'g/:id',
        component: GruposRouterComponent,

        children: [
          {path: '', component: GrupoHomeComponent},
          {path: 'forum', component: ForoGrupoComponent},
          {path: 'buscar-integrantes', component: BuscarIntegrantesComponent},
          {path: 'calendario', component: CalendarioComponent},
          {path: 'archivos', component: ArchivosGrupoComponent},
        ]
      }
    ]
  },

  {
    path: 'perfil', component: PerfilRouterComponent, canActivate: [LogueadoGuard],
    children: [
      {path: '', component: PerfilComponent},
      {path: 'notificaciones', component: MisNotificacionesComponent},
      {path: 'intereses', component: InteresesComponent},
      {path: 'valoraciones', component: MisValoracionesComponent},
    ]
  },

  {path: '**', redirectTo: '/', pathMatch: 'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
