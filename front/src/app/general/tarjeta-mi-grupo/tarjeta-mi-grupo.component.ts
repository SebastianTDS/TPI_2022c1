import {Component, Input, OnInit} from '@angular/core';
import {ApiRestService} from "../../services/api-rest.service";

@Component({
  selector: 'app-tarjeta-mi-grupo',
  templateUrl: './tarjeta-mi-grupo.component.html',
  styleUrls: ['./tarjeta-mi-grupo.component.css']
})
export class TarjetaMiGrupoComponent implements OnInit {

  @Input() dataGroupo: any=[];
  public loading: boolean;
  public image: any;

  constructor(private apiRest:ApiRestService) {
  }

  ngOnInit(): void {
    this.getImageProfile()
  }


  public getImageProfile(){
    this.loading=true;
    this.apiRest.getGroupUrlImageProfile(this.dataGroupo.id).subscribe(data =>{
      this.image=data.url;
      if(data)this.loading=false;
    },error => {
      this.loading=false;
      console.log("error al cargar imagen de perfil")
    })
  }


}
