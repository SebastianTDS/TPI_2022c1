import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApiRestService} from "../../../services/api-rest.service";
import {CKEditor4} from "ckeditor4-angular";
import {IFiltersDto} from "../../../services/models/post/IFiltersDto";

@Component({
  selector: 'app-foro-grupo',
  templateUrl: './foro-grupo.component.html',
  styleUrls: ['./foro-grupo.component.css']
})
export class ForoGrupoComponent implements OnInit {
  public post: any = [];
  public pageable: number = 0;
  public title: string;
  public content: string;
  public error: string;
  public success: boolean = false;
  public idGroup: number;
  public grupo: any;
  public errorStyle: any;
  public formTag: number;
  public formName: string;
  public createPostTag: number;
  public tagName: string;
  public tags: any=[];
  public loading: boolean;


  constructor(private route: ActivatedRoute, private apiRestService: ApiRestService) {
    const postDto: IFiltersDto = {
      idSubject:null,
      idCareer:null,
      idUniversity:null,
      idDepartment:null,
      idTag: this.formTag,
      name: this.formName
    };
    this.route.parent?.params.subscribe(params => {
      this.idGroup = params['id'];
      this.getGroupPost(0, this.idGroup)
    });
  }

  ngOnInit(): void {
    this.getTags()
  }

  private getTags(): void {
    this.loading=true;
    this.apiRestService.getTags().subscribe(
      data => {
        this.tags = data
        if(data)
          this.loading=false
      },
      error => {
        console.log("Error al cargar tags")
        this.loading=false
      }
    );
  }

  public getGroupPost(pageable: number, idGroup: number): void {
    const postDto: IFiltersDto = {
      idSubject:null,
      idCareer:null,
      idUniversity:null,
      idDepartment:null,
      idTag: this.formTag,
      name: this.formName
    };
    this.apiRestService.getGroupPost(pageable, idGroup,postDto).subscribe(data => {
        this.post = data;
        console.log(this.post)
      },
      err => {
        console.log("no hay mas post" + err)
      });
  }


  public paginaPrevia() {

    this.pageable--;
    if (this.pageable < 0) {
      this.pageable = 0;
      return
    }
    this.getGroupPost(this.pageable, this.idGroup);
  }

  public paginaSiguiente() {
    if (this.post.length == 0) {
      return;
    }
    this.pageable++;
    this.getGroupPost(this.pageable, this.idGroup);
  }

  public createPost() {
    if (!this.title || !this.title.trim()) {
      this.error = "Debe completar el titulo del Post";
      this.success = false;
      return;
    }
    if (!this.content || !this.content.trim()) {
      this.error = "Debe completar el contenido del Post";
      this.success = false;
      return;
    }

    for(let aux of this.tags){
      if(aux.id==this.createPostTag){
        this.tagName=aux.name
      }
    }

    var postDto = {
      content: this.content,
      name: this.title,
      idGroup: this.idGroup,
      idTag:this.createPostTag,
      tagName:this.tagName
    }

    this.apiRestService.createGroupPost(postDto).subscribe(value => {
        this.getGroupPost(this.pageable, this.idGroup);
        this.content = "";
        this.error = null;
        this.title = "";
        this.success = true;
      }, error => {
        this.error = "No se ha podido crear el post:" + error.message
      }
    )
  }

  public onChange(event: CKEditor4.EventInfo) {
    this.content = event.editor.getData();
  }

  onSearch() {
    if(!this.formName && !this.formTag){
      this.getGroupPost(this.pageable, this.idGroup)
    }
    this.loading=true;
    const postDto: IFiltersDto = {
      idSubject:null,
      idCareer:null,
      idUniversity:null,
      idDepartment:null,
      idTag: this.formTag,
      name: this.formName
    };

    this.apiRestService.getGroupPost(this.pageable,this.idGroup,postDto).subscribe(value => {
      this.post=value;
      if(value) this.loading=false;
    },error =>{
      this.loading=false;
    })

  }

  getGroupPostReload():void {
    this.loading = true
    const postDto: IFiltersDto = {
      idSubject:null,
      idCareer:null,
      idUniversity:null,
      idDepartment:null,
      idTag: this.formTag,
      name: this.formName
    };
    this.apiRestService.getGroupPost(this.pageable,this.idGroup,postDto).subscribe(data => {
        this.post = data;
        if (data)
          this.loading = false
      },
      err => {
        this.loading = false
        console.log("no hay mas post" + err)
      });
  }
}
