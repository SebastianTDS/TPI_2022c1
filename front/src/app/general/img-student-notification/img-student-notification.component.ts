import {Component, Input, OnInit} from '@angular/core';
import {ApiRestService} from "../../services/api-rest.service";

@Component({
  selector: 'app-img-student-notification',
  templateUrl: './img-student-notification.component.html',
  styleUrls: ['./img-student-notification.component.css']
})
export class ImgStudentNotificationComponent implements OnInit {
  @Input()
  public notificationImg: any
  public imageProfile: any;

  constructor(private apiRest: ApiRestService) {
  }

  ngOnInit(): void {
    this.getImageProfile();
  }


  private getImageProfile() {
    this.apiRest.getUrlImageProfile(this.notificationImg).subscribe(data => {
      this.imageProfile = data.url;
    }, error => {
      console.log("error al cargar imagen de perfil")
    })
  }
}


