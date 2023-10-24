import {Component, Input, OnInit} from '@angular/core';
import {ApiRestService} from "../../../../services/api-rest.service";
import {
  ForoGeneralComponent
} from "../foro-general.component";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  @Input() public data : any ;
  @Input() public tags : any;
  public minimizado2: string ='forum-preview texto-base ';
  public minimizado1: string ='masked-overflow ';
  public desplegado:boolean=false;
  public inputComment: string;
  public error: string;
  public image: any;
  public loading: boolean;

  constructor(private apiRestService:ApiRestService,private forogeneral:ForoGeneralComponent) { }

  ngOnInit(): void {
    this.getImageProfile()
  }



  public desplegarPost() {
    this.desplegado=!this.desplegado;
  }

  public darLikeComment(id:number) {
    var likeDto={
      id_comment:id
    }

    this.apiRestService.createLike(likeDto).subscribe(value =>
      {
        this.forogeneral.onSearch();
      },error => {
        console.log("ya diste like");
      }
    )
  }

  public darLikePost() {
    var likeDto={
      id_post:this.data.id
    }

    this.apiRestService.createLike(likeDto).subscribe(value =>
      {
        this.forogeneral.onSearch();
      },error => {
      console.log("ya diste like");
      }
    )
  }

  public createComment(id:number) {
    if(!id || !this.inputComment||!this.inputComment.trim()){
      this.error="El comentario no puede estar vacio";
      return
    }
    var commentDto={
      content:this.inputComment,
      id_post:id
    }

    this.apiRestService.createComment(commentDto).subscribe(value=>{
        this.forogeneral.onSearch();
      },error=>{
      console.log("ERROR")
      }
    )
  }

  public getImageProfile(){
    this.loading=true;
    console.log(this.data);
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
