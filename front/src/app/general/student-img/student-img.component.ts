import {Component, Input, OnInit} from '@angular/core';
import {ApiRestService} from "../../services/api-rest.service";

@Component({
  selector: 'app-student-img',
  templateUrl: './student-img.component.html',
  styleUrls: ['./student-img.component.css']
})
export class StudentImgComponent implements OnInit {
  @Input()
  public data: any
  public imageProfile: any;

  constructor(private apiRest: ApiRestService) {
  }

  ngOnInit(): void {
    this.getImageProfile();
  }


  private getImageProfile() {
    this.apiRest.getUrlImageProfile(this.data.id).subscribe(data => {
      this.imageProfile = data.url;
    }, error => {
      console.log("error al cargar imagen de perfil")
    })
  }
}

