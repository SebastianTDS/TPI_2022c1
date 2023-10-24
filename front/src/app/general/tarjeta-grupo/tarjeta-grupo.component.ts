import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ApiRestService} from "../../services/api-rest.service";

@Component({
  selector: 'app-tarjeta-grupo',
  templateUrl: './tarjeta-grupo.component.html',
  styleUrls: ['./tarjeta-grupo.component.css']
})
export class TarjetaGrupoComponent implements OnInit {
  @Input() dataEntrante: any = [];
  public displayStyle = "none";
  public msgSolicitud: boolean = false;
  public grupo: any;
  public error: boolean;
  public imageProfile: any;
  public loading: boolean;

  constructor(private router: Router, private apiRest: ApiRestService) {
    
  }

  ngOnInit(): void {
    this.getGroupImageProfile()
  }

  openPopup() {
    this.displayStyle = "block";
    document.getElementById("black-screen")?.classList.add("black-screen")
    document.getElementsByTagName("body")[0].style.overflowY = "hidden"
  }

  showMore(container: HTMLElement){
    container.classList.remove("tags-container")
  }

  showLess(container: HTMLElement){
    container.classList.add("tags-container")
  }

  close() {
    this.error = false;
    this.msgSolicitud = false;
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

  checkOverflow (element: HTMLElement) {
    return element.offsetHeight + 10 < element.scrollHeight ||
           element.offsetWidth + 10 < element.scrollWidth;
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

  closePopup() {
    this.displayStyle = "none";
    document.getElementById("black-screen")?.classList.remove("black-screen")
    document.getElementsByTagName("body")[0].style.overflowY = "scroll"
  }

  public unirseGrupo(): void {
    this.loading = true
    this.apiRest.unirseGrupo(this.dataEntrante.idGrupo).subscribe(
        data => {
          this.grupo = data;
          this.loading = false
          this.router.navigate(['/home', 'g', this.dataEntrante.idGrupo])
        },
        error => {
          this.loading = false
        }
    );
  }

  public unirseGrupoCerrado(): void {

    const requestJoinDto = {
      groupId: this.dataEntrante.idGrupo,
      accepted: false
    };

    this.loading = true
    this.apiRest.unirseGrupoCerrado(requestJoinDto).subscribe(
        data => {
          this.grupo = data;
          this.loading = false
          this.msgSolicitud = true;
        },
        error => {
          this.error = true
          this.loading = false
        }
    );
  }

  public getGroupImageProfile(){
    this.apiRest.getGroupUrlImageProfile(this.dataEntrante.idGrupo).subscribe(data =>{
      this.imageProfile=data.url;
    },error => {
      console.log("error al cargar imagen de perfil")
    })
  }

}
