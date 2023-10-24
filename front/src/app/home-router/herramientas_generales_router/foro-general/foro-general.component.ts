import {Component, OnInit} from '@angular/core';
import {ApiRestService} from "../../../services/api-rest.service";
import {CKEditor4} from "ckeditor4-angular";
import {IFiltersDto} from "../../../services/models/post/IFiltersDto";

@Component({
  selector: 'app-foro-general',
  templateUrl: './foro-general.component.html',
  styleUrls: ['./foro-general.component.css']
})

export class ForoGeneralComponent implements OnInit {
  public post: any = [];
  public pageable: number = 0;
  public fltCarrera: any
  public fltMateria: any;
  public fltDepartamento: any;
  public title: string;
  public content: string;
  public error: string;
  public success: string;
  public errorStyle: any;
  public editorData = '';
  public loading: boolean;
  public tags: any=[];
  public formTag: number;
  public formName: string;
  public createPostTag: number;
  public tagName: string;


  constructor(private apiRestService: ApiRestService) {
    this.loading = true
    this.getTags();
  }

  ngOnInit(): void {
    this.getGeneralPost(0);
  }

  close() {
    this.error = null;
    this.success = null;
}

  getGeneralPostReload(): void {
    this.loading = true
    this.apiRestService.getGeneralPost(this.pageable).subscribe(data => {
        this.post = data;
        if (data)
          this.loading = false
      },
      err => {
        this.loading = false
      });
  }

  getGeneralPost(pageable: number): void {
    this.loading = true
    this.apiRestService.getGeneralPost(pageable).subscribe(data => {
        this.post = data;
        if (data)
          this.loading = false
      },
      err => {
        this.loading = false
      });
  }


  limpiar() {
    this.fltCarrera = [];
    this.fltMateria = [];

    (document.getElementById("Departamento") as HTMLInputElement).value = "";
  }

  paginaPrevia() {

    this.pageable--;
    if (this.pageable < 0) {
      this.pageable = 0;
      return
    }
    this.getGeneralPost(this.pageable);
  }

  paginaSiguiente() {
    if (this.post.length == 0) {
      return;
    }
    this.pageable++;
    this.getGeneralPost(this.pageable);
  }

  createPost() {
    if (!this.title || !this.title.trim()) {
      this.error = "Debe completar el titulo del Post";
      return;
    }
    if (!this.content || !this.content.trim()) {
      this.error = "Debe completar el contenido del Post";
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
      idTag:this.createPostTag,
      tagName:this.tagName
    }

    console.log(postDto);
    this.loading = true
    this.apiRestService.createGeneralPost(postDto).subscribe(value => {

        this.getGeneralPost(this.pageable)
        this.content = "";
        this.error = null;
        this.title = "";
        this.success = "¡Post creado con éxito!";
        if (value)
          this.loading = false
      }, error => {
        this.loading = false
        this.error = "No se ha podido crear el post: " + error.message
      }
    )


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
        this.loading=false
      }
    );
  }

  public onChange(event: CKEditor4.EventInfo) {
    this.content = event.editor.getData();
  }

  onSearch() {
    if(!this.formName && !this.formTag){
      this.getGeneralPost(this.pageable)
      return
    }
    this.loading=true;
    const postDto: IFiltersDto = {
      idSubject:null,
      idCareer:null,
      idUniversity:null,
      idDepartment:null,
      idTag: this.formTag,
      name: this.formName,
    };

    this.apiRestService.getGeneralPostByFilters(postDto).subscribe(value => {
      this.post=value;
      if(value) this.loading=false;
    },error =>{
      this.loading=false;
      this.error = "Error al buscar posts. Intente nuevamente"
    })

  }



}


