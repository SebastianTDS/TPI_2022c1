import {Component, Input, OnInit} from '@angular/core';
import {ApiRestService} from "../../../../services/api-rest.service";
import {ForoGeneralComponent} from "../../../herramientas_generales_router/foro-general/foro-general.component";
import {ForoGrupoComponent} from "../foro-grupo.component";

@Component({
  selector: 'app-post-group',
  templateUrl: './post-group.component.html',
  styleUrls: ['./post-group.component.css']
})
export class PostGroupComponent implements OnInit {
  @Input() public data : any ;
  public desplegado:boolean=false;
  public inputComment: string;
  public error: string;
  public image: any;
  public loading: boolean;
  constructor(private apiRestService:ApiRestService,private foroGroup:ForoGrupoComponent) { }

  ngOnInit(): void {
    this.getImageProfile()
  }


  desplegarPost() {
    this.desplegado=!this.desplegado;
  }

  darLikeComment(id:number) {
    var likeDto={
      id_comment:id
    }

    this.apiRestService.createLike(likeDto).subscribe(value =>
      {
        this.foroGroup.onSearch();
      },error => {
        console.log("ya diste like");
      }
    )
  }

  darLikePost() {
    var likeDto={
      id_post:this.data.id
    }

    this.apiRestService.createLike(likeDto).subscribe(value =>
      {
        this.foroGroup.onSearch();
      },error => {
        console.log("ya diste like");
      }
    )
  }

  createComment(id:number) {
    if(!id || !this.inputComment||!this.inputComment.trim()){
      this.error="El comentario no puede estar vacio";
      return
    }
    var commentDto={
      content:this.inputComment,
      id_post:id
    }

    this.apiRestService.createComment(commentDto).subscribe(value=>{
        this.foroGroup.onSearch();
      },error=>{
        console.log("ERROR")
      }
    )



  }

  public getImageProfile(){
    this.loading=true;
    this.apiRestService.getUrlImageProfile(this.data.idUser).subscribe(data =>{
      this.image=data.url;
      if(data)this.loading=false;
    },error => {
      this.loading=false;
      console.log("error al cargar imagen de perfil")
    })
  }

  public getImageUserComment(id:string):string {
    this.loading=true;
    this.apiRestService.getUrlImageProfile(id).subscribe(data =>{
      if(data){
        this.loading=false;
        return data.url;
      }

    },error => {
      this.loading=false;
      console.log("error al cargar imagen de perfil")
    })
    return null;
  }
}

