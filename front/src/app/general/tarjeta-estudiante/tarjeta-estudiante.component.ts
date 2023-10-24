import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ApiRestService } from "../../services/api-rest.service";

@Component({
  selector: 'app-tarjeta-estudiante',
  templateUrl: './tarjeta-estudiante.component.html',
  styleUrls: ['./tarjeta-estudiante.component.css']
})
export class TarjetaEstudianteComponent implements OnInit {

  @Input() dataEntrante: any;
  public displayStyle = "none";
  public error: boolean = false;
  public msgSolicitud: boolean = false;
  public idGroup: string;
  public imageProfile: any;
  public userRecompenza: any;

  constructor(private route: ActivatedRoute, private apiRest: ApiRestService) {
    this.route.parent.params?.subscribe(params => {
      this.idGroup = params['id'];
    });
  }


  ngOnInit(): void {
    this.getImageProfile()
    this.getUserRecompenzas()
  }

  openPopup(id: any) {
    document.getElementById(id)?.classList.remove("modal-hide")
    document.getElementById("black-screen")?.classList.add("black-screen")
    document.getElementsByTagName("body")[0].style.overflowY = "hidden"
  }

  close(){
    this.msgSolicitud = false;
    this.error = false
  }

  getFirstColor(valor:number){
    if(valor <= 100 && valor >= 90)
      return '#78C000'
    if(valor < 90 && valor >= 75)
      return '#b7da07'
    if(valor < 75 && valor >= 55)
      return '#e3dd16'
    if(valor < 55 && valor >= 30)
      return '#e3a316'
    if(valor < 30)
      return '#e32416'
    else
      return '#000000'
  }

  getSecondColor(valor:number){
    if(valor <= 100 && valor >= 90)
      return '#C7E596'
    if(valor < 90 && valor >= 75)
      return '#d1e97c'
    if(valor < 75 && valor >= 55)
      return '#f8f99a'
    if(valor < 55 && valor >= 30)
      return '#f9d19a'
    if(valor < 30)
      return '#f9ad9a'
    else
      return '#000000'
  }

  closePopup(id: any) {
    document.getElementById(id)?.classList.add("modal-hide")
    document.getElementById("black-screen")?.classList.remove("black-screen")
    document.getElementsByTagName("body")[0].style.overflowY = "scroll"
  }

  checkOverflow (element: HTMLElement) {
    return element.offsetHeight + 10 < element.scrollHeight ||
           element.offsetWidth + 10 < element.scrollWidth;
  }

  showMore(container: HTMLElement){
    container.classList.remove("tags-container")
  }

  showLess(container: HTMLElement){
    container.classList.add("tags-container")
  }

  invitarEstudiante(button: any, idEstudiante: any) {
    const requestJoinDto = {
      groupId: this.idGroup,
      accepted: false,
      usuario: idEstudiante
    };

    this.apiRest.invitarEstudiante(requestJoinDto).subscribe(
      data => {
        button.innerHTML = "Solicitud enviada!"
        button.classList.add("solicitud-enviada")
        this.msgSolicitud = true;
      },
      error => {
        this.error = true;
      }
    );
  }

  calculateAge() {
    let timeDiff = Math.abs(Date.now() - Date.parse(this.dataEntrante.birthday));
    let age = Math.floor((timeDiff / (1000 * 3600 * 24)) / 365.25);

    return age;
  }

  public getImageProfile(){
    this.apiRest.getUrlImageProfile(this.dataEntrante.idEstudiante).subscribe(data =>{
      this.imageProfile=data.url;
    },error => {
      console.log("error al cargar imagen de perfil")
    })
  }

  private getUserRecompenzas() {
    this.apiRest.getUserById(this.dataEntrante.idEstudiante).subscribe(data =>{
        this.userRecompenza=data;
    },error => {
      console.log("Error al cargar los datos del usuario")
    })
  }
}
