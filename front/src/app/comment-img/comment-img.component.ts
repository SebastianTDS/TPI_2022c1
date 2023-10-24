import {Component, Input, OnInit} from '@angular/core';
import {ApiRestService} from '../services/api-rest.service';

@Component({
  selector: 'app-comment-img',
  templateUrl: './comment-img.component.html',
  styleUrls: ['./comment-img.component.css']
})
export class CommentImgComponent implements OnInit {

  @Input()
  public userId:any
  public imageProfile: any;

  constructor(private apiRest:ApiRestService) { }

  ngOnInit(): void {
    this.getImageCommentProfile()
  }


  private getImageCommentProfile(){

    this.apiRest.getUrlImageProfile(this.userId).subscribe(data =>{
      this.imageProfile=data.url;
    },error => {
      console.log("error al cargar imagen de perfil")
      console.log('im here')
    })
  }




}
