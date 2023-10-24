import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {IuserDto} from "./models/iuserDto";
import {IFiltersDto} from "./models/post/IFiltersDto";
import { ISearchGroup } from './models/ISearchGroup';

@Injectable({
  providedIn: 'root'
})
export class ApiRestService {
  private API_STUDENT = environment.API_STUDENT;
  private API_ALGORITHM = environment.API_ALGORITHM;
  private API_NOTIFICATION = environment.API_NOTIFICATION;
  private API_FORUM = environment.API_FORUM;
  private API_FILES = environment.API_FILES;
  private API_CALENDAR = environment.API_CALENDAR;

  public headers = new HttpHeaders();

  constructor(private httpClient: HttpClient) {
  this.headers.set('Content-Type', 'multipart/form-data ; charset=utf-8');
  }
  private isNull(value: any) {
    return (typeof value === 'undefined' || !value) ? '' : value;
  }

  public tamanoRepoGrupo(idGrupo: any): Observable<any> {
    return this.httpClient.get<any>(this.API_FILES + 'file' + '/get-space/' + idGrupo)
  }

  public crearVotacion(votacionDTO: any): Observable<any>{
    return this.httpClient.post<string>(this.API_STUDENT + 'group' + '/kick/crear', votacionDTO)
  }

  public aceptarVeto(votacionDTO: any): Observable<any>{
    return this.httpClient.put<string>(this.API_STUDENT + 'group' + '/kick/votar', votacionDTO)
  }

  public rechazarVeto(votacionDTO: any): Observable<any>{
    return this.httpClient.delete<string>(this.API_STUDENT + 'group' + '/kick/rechazar', votacionDTO)
  }

  public getGeneralPost(pageable: number): Observable<any> {
    return this.httpClient.get<any>(this.API_FORUM + 'forum' + '/general?size=6&page=' + pageable)
  }

  public getGroupPost(pageable: number, idGroup: number,filtersDto: IFiltersDto): Observable<any> {
    return this.httpClient.get<any>(this.API_FORUM + 'forum' + '/group/' + idGroup + '?size=6&page=' + pageable +'&idTag='+ this.isNull(filtersDto.idTag)+'&name='+ this.isNull(filtersDto.name))
  }

  public createGroupPost(postDto: any): Observable<any> {
    return this.httpClient.post<any>(this.API_FORUM + 'forum' + '/group/create', postDto);
  }


  public getGeneralPostByFilters(filtersDto: IFiltersDto): Observable<any> {
    return this.httpClient.get<any>(this.API_FORUM + 'forum' + '/general?name=' + this.isNull(filtersDto.name) + '&idTag=' + this.isNull(filtersDto.idTag));
  }

  public createGeneralPost(postDto: any): Observable<any> {
    return this.httpClient.post<any>(this.API_FORUM + 'forum' + '/general/create', postDto);
  }

  public createLike(likeDto: any): Observable<any> {
    return this.httpClient.post<any>(this.API_FORUM + 'forum' + '/like', likeDto);
  }

  public createComment(commentDto: any): Observable<any> {
    return this.httpClient.post<any>(this.API_FORUM + 'forum' + '/comment', commentDto);
  }




  public getUser(): Observable<any> {
    return this.httpClient.get<any>(this.API_STUDENT + 'student' + '/get-user')
  }
  public getUserById(id:string): Observable<any> {
    return this.httpClient.post<any>(this.API_STUDENT + 'student' + '/get-user'+'/'+id,{})
  }
  public createUser(userDto: IuserDto): Observable<any> {
    return this.httpClient.post<any>(this.API_STUDENT + 'student' + '/create-user', userDto);
  }
  public getHistorico(): Observable<any> {
    return this.httpClient.get<any>(this.API_STUDENT + 'group' + '/hist')
  }

  public getValoration(): Observable<any> {
    return this.httpClient.get<any>(this.API_STUDENT + 'valoration' + '/get-valoration');
  }

  public valoration(valoration: any): Observable<any> {
    return this.httpClient.put<any>(this.API_STUDENT + 'valoration' + '/valoration', valoration);
  }
  public valorationGrafo(valoration: any): Observable<any> {
    return this.httpClient.put<any>(this.API_ALGORITHM + 'algorithm/user' + '/sumar-puntos', valoration);
  }




  public getTags(): Observable<any> {
    return this.httpClient.get<any>(this.API_ALGORITHM + 'algorithm/graph' + '/tags');
  }

  public getFacultades(): Observable<any> {
    return this.httpClient.get<any>(this.API_ALGORITHM + 'algorithm/graph' + '/facultades');
  }

  public getDepartamentos(facultad: string): Observable<any> {
    return this.httpClient.get<any>(this.API_ALGORITHM + 'algorithm/graph/' + facultad + '/departamentos');
  }

  public getCarreras(facultad: string, departamento: string): Observable<any> {
    return this.httpClient.get<any>(this.API_ALGORITHM + 'algorithm/graph/' + facultad + "/" + departamento + '/carreras');
  }

  public getMaterias(facultad: string, departamento: string, carrera: string): Observable<any> {
    return this.httpClient.get<any>(this.API_ALGORITHM + 'algorithm/graph/' + facultad + "/" + departamento + "/" + carrera + '/materias');
  }

  public getTurnos(facultad: string, materia: string): Observable<any> {
    return this.httpClient.get<any>(this.API_ALGORITHM + 'algorithm/graph/' + facultad + "/" + materia + '/turnos');
  }

  public cambiarFacultad(DTOUser: any): Observable<any> {
    return this.httpClient.put<any>(this.API_ALGORITHM + 'algorithm/user' + '/cambiar-facultad', DTOUser);
  }


  public cargarInteres(DTOUser: any): Observable<any> {
    return this.httpClient.put<any>(this.API_ALGORITHM + 'algorithm/user' + '/cargar-interes', DTOUser);
  }

  public cargarInteresGrupo(DTOUser: any): Observable<any> {
    return this.httpClient.put<any>(this.API_ALGORITHM + 'algorithm/group' + '/agregar-interes', DTOUser);
  }

  public cargarCarrera(DTOUser: any): Observable<any> {
    return this.httpClient.put<any>(this.API_ALGORITHM + 'algorithm/user' + '/anotar-carrera', DTOUser);
  }
  public cargarMateria(DTOUser: any): Observable<any> {
    return this.httpClient.put<any>(this.API_ALGORITHM + 'algorithm/user' + '/anotar-materia', DTOUser);
  }
  public verUsuario(id: string): Observable<any> {
    return this.httpClient.get<any>(this.API_ALGORITHM + 'algorithm/user/' + id);
  }
  public borrarInteres(DTOUser: any): Observable<any> {
    return this.httpClient.delete<any>(this.API_ALGORITHM + 'algorithm/user' + '/borrar-interes', DTOUser);
  }

  public borrarInteresGrupo(Body: any): Observable<any> {
    return this.httpClient.delete<any>(this.API_ALGORITHM + 'algorithm/group' + '/borrar-interes', Body);
  }

  public abandonarMateria(DTOUser: any): Observable<any> {
    return this.httpClient.delete<any>(this.API_ALGORITHM + 'algorithm/user' + '/abandonar-materia', DTOUser);
  }
  public abandonarCarrera(DTOUser: any): Observable<any> {
    return this.httpClient.delete<any>(this.API_ALGORITHM + 'algorithm/user' + '/abandonar-carrera', DTOUser);
  }

  public crearGrupo(GroupDto: any): Observable<any> {
    return this.httpClient.post<any>(this.API_STUDENT + 'group/save', GroupDto);
  }

  public modificarGrupo(idGrupo: any,GrupoDto: any) : Observable<any> {
    return this.httpClient.put<any>(this.API_STUDENT + 'group/update/'+ idGrupo, GrupoDto);
  }

  public misGrupos(): Observable<any> {
    return this.httpClient.get<any>(this.API_STUDENT + 'group/my-groups');
  }

  public gruposRecomendados(filtros :ISearchGroup): Observable<any> {
    return this.httpClient.get<any>(this.API_STUDENT + 'group/recommended-groups?user=' + filtros.idUser + "&pagina=" + filtros.pagina + "&materia=" + filtros.materia + "&tag=" + filtros.tag);
  }

  public estudiantesRecomendados(filtros :ISearchGroup): Observable<any> {
    return this.httpClient.get<any>(this.API_STUDENT + 'group/recommended-students?group=' + filtros.idGroup + "&pagina=" + filtros.pagina + "&materia=" + filtros.materia + "&tag=" + filtros.tag);
  }

  public unirseGrupo(idGrupo: any): Observable<any> {
    return this.httpClient.get<any>(this.API_STUDENT + 'group/join/' + idGrupo)
  }

  public infoGrupo(idGrupo: any): Observable<any> {
    return this.httpClient.get<any>(this.API_STUDENT + 'group/' + idGrupo)
  }

  public interesGrupo(idGrupo: any): Observable<any> {
    return this.httpClient.get<any>(this.API_ALGORITHM + 'algorithm/group/' + idGrupo)
  }

  public getNotification() {
    return this.httpClient.get<any>(this.API_NOTIFICATION + 'notification/' + 'get-notification')
  }

  public deleteNotification(DTONotif: any) {
    return this.httpClient.delete<any>(this.API_NOTIFICATION + 'notification/' + 'delete-notification', DTONotif)
  }

  public uploadImgPerfil(fileDto: any): Observable<any> {
    return this.httpClient.post<any>(this.API_FILES + 'file/profile/upload', fileDto)
    };

  public uploadGroupImgPerfil(fileDto: any): Observable<any> {
    return this.httpClient.post<any>(this.API_FILES + 'file/group/upload/image', fileDto,{ headers: this.headers })
  };

  public getUrlImageProfile(id:string): Observable<any> {
    return this.httpClient.get<any>(this.API_FILES + 'file/profile/getImage/'+id)
  };

  public getGroupUrlImageProfile(id:number): Observable<any> {
    return this.httpClient.get<any>(this.API_FILES + 'file/group/profile/getImage/'+id)
  };

  public getGroupListFiles(id:number):Observable<any>{
    return this.httpClient.get<any>(this.API_FILES+ 'file/group/files/'+id)
  }

  public uploadGroupFile(fileDto: any): Observable<any> {
    return this.httpClient.post<any>(this.API_FILES + 'file/group/upload', fileDto,{ headers: this.headers })
  };


  public deleteGroupFile(fileDto:any) {
      return this.httpClient.delete<any>(this.API_FILES + 'file/group/delete', fileDto)
  }

  public unirseGrupoCerrado(RequestJoinDataDto : any): Observable<any>{
    return this.httpClient.post<any>(this.API_STUDENT+ 'request-join/save/', RequestJoinDataDto);
  }

  public invitarEstudiante(RequestJoinDataDto : any): Observable<any>{
    return this.httpClient.post<any>(this.API_STUDENT + 'request-join/invite/', RequestJoinDataDto);
  }

  public invitarEstudianteEmail(RequestJoinDataDto : any): Observable<any>{
    return this.httpClient.post<any>(this.API_STUDENT + 'request-join/invite-email/', RequestJoinDataDto);
  }

  public borrarGrupo(Body: any): Observable<any> {
    return this.httpClient.delete<any>(this.API_STUDENT + 'group/delete', Body);
  }

  public aceptarPeticion(idGrupo : any, idUsuario: any): Observable<any>{
    return this.httpClient.get<any>(this.API_STUDENT + 'request-join/accept/'+idGrupo + "/" + idUsuario);
  }

  public rechazarPeticion(idGrupo : any, idUsuario: any): Observable<any>{
    return this.httpClient.delete<any>(this.API_STUDENT + 'request-join/reject/'+ idGrupo + "/" + idUsuario);
  }

  public abandonarGrupo(idGrupo : any): Observable <any>{
    return this.httpClient.get<any>(this.API_STUDENT + 'group/leave/'+idGrupo );
  }

  public getCalendar(idGrupo : any): Observable <any>{
    return this.httpClient.get<any>(this.API_CALENDAR + 'calendar/'+idGrupo );
  }
  public deleteCalendar(Body: any): Observable<any> {
    return this.httpClient.delete<any>(this.API_CALENDAR + 'calendar/delete', Body);
  }
  public createCalendar(calendarDto: any): Observable<any> {
    return this.httpClient.post<any>(this.API_CALENDAR + 'calendar/save', calendarDto)
  };
  public reminderCalendar(): Observable <any>{
    return this.httpClient.get<any>(this.API_CALENDAR + 'calendar/events/reminder' );
  }
}
