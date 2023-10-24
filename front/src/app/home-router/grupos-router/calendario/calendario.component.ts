import {Component, OnInit} from '@angular/core';
import {ApiRestService} from "../../../services/api-rest.service";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-calendario',
  templateUrl: './calendario.component.html',
  styleUrls: ['./calendario.component.css']
})
export class CalendarioComponent implements OnInit {

  public eventos: any[] = []
  public loading: boolean;
  private idGroup: number;
  formDate: Date;
  errorStyle: any;
  success: any;
  error: any;
  formName: any;
  formDescription: any;

  constructor(private route: ActivatedRoute, private apiRest: ApiRestService) {
    this.route.parent?.params.subscribe(params => {
      this.idGroup = params['id'];
    });
  }

  ngOnInit(): void {

    this.getCalendar();
  }

  close() {
    this.error = false;
    this.success = false;
  }

  private getCalendar() {
    this.eventos = [];
    this.loading = true;
    this.apiRest.getCalendar(this.idGroup).subscribe(data => {
      this.eventos = data;
      this.loading = false;
    }, error => {
      this.loading = false;
    })
  }

  deleteEvent(id: number) {
    this.loading = true;
    var eventDto = {
      id: id
    };
    const options = {
      body: eventDto,
    };
    console.log(options)
    this.apiRest.deleteCalendar(options).subscribe(data => {
        this.getCalendar();
        this.loading = false
      },
      error => {
        this.loading = false
        console.log("Error al borrar el evento")
      });
  }

  onCreateCalendar() {
    let hoy = new Date()
    let objetivo = new Date(this.formDate)

    let hoyDate = new Date(hoy.getFullYear(), hoy.getMonth(), hoy.getDate(), 0, 0, 0, 0 )
    let objetivoDate = new Date(objetivo.getFullYear(), objetivo.getMonth(), objetivo.getDate(), 0, 0, 0, 0)

    this.error="";
    if (!this.formDate)
      this.error = "La fecha no puede estar vacía";
    if ((objetivo.getTime() + objetivoDate.getTimezoneOffset() * 60000) < hoyDate.getTime())
      this.error = "La fecha no puede ser menor a la actual";
    if (!this.formName)
      this.error = "El nombre no puede estar vacío";
    if (!this.formDescription)
      this.error = "La descripción no puede estar vacía";
    if(this.error){
      return
    }
    var eventDto = {
      eventDate: this.formDate,
      name:this.formName,
      description:this.formDescription,
      idGroup:this.idGroup,
    };
    this.loading=true;
    this.apiRest.createCalendar(eventDto).subscribe(data => {
        this.getCalendar();
        this.loading = false;
      },
      error => {
        this.loading = false;
        console.log("Error al crear el evento");
      });
  }
}

