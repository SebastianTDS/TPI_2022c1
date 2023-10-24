import {Component, OnInit} from '@angular/core';
import {ApiRestService} from "../../services/api-rest.service";

@Component({
  selector: 'app-curva',
  templateUrl: './curva.component.html',
  styleUrls: ['./curva.component.css']
})
export class CurvaComponent implements OnInit {
  public userFound: boolean=false;
  private idUser: string;
  public clickRegister: boolean=false;

  constructor(private apiRest: ApiRestService) {
    this.getUser()
  }

  ngOnInit(): void {
    this.perfilCompleto()
  }

  private getUser(): void {
    this.apiRest.getUser().subscribe(data => {
        this.idUser = data.id;
      },
      error => {
        this.userFound = false;
      });
  }

  private perfilCompleto(): void {
      this.userFound = this.idUser != "";
  }

}
