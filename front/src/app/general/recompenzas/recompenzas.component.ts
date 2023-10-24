import {Component, Input, OnInit} from '@angular/core';


@Component({
  selector: 'app-recompenzas',
  templateUrl: './recompenzas.component.html',
  styleUrls: ['./recompenzas.component.css']
})
export class RecompenzasComponent implements OnInit {
  @Input()
  public user: any = [];

  constructor() {
  }

  ngOnInit(): void {
    this.cargarNotificaciones();
  }

  cargarNotificaciones() {
console.log("**************************** "+this.user)
  }
}
