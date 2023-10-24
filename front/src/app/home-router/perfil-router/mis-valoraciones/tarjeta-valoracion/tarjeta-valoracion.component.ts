import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ApiRestService} from "../../../../services/api-rest.service";

@Component({
  selector: 'app-tarjeta-valoracion',
  templateUrl: './tarjeta-valoracion.component.html',
  styleUrls: ['./tarjeta-valoracion.component.css']
})
export class TarjetaValoracionComponent implements OnInit {
  @Input() data: any;
  public forms: FormGroup;
  public valoracionNoRealizada: boolean = true;
  public error: boolean = false;
  public mesage: string;
  public loading: boolean;
  private puntos:number=0;

  constructor(private formBuilder: FormBuilder, private apiRestService: ApiRestService) {
    this.forms = this.formBuilder.group({
      idValorationNotification: 0,
      valuationIn: '',
      responsable: false,
      colaborador: false,
      proActivo: false,
      organizado: false,
      detallista: false,
      conocedor: false
    });
  }

  ngOnInit(): void {
  }

  enviarValoracion() {
    if (false) {
      this.error = true;
      this.mesage = "Debe seleccionar una valoración";
      return;
    }
if(this.forms.value.responsable)
  this.puntos++;
    if(this.forms.value.colaborador)
      this.puntos++;
    if(this.forms.value.proActivo)
      this.puntos++;
    if(this.forms.value.organizado)
      this.puntos++;
    if(this.forms.value.detallista)
      this.puntos++;
    if(this.forms.value.conocedor)
      this.puntos++;

    this.forms.value.idValorationNotification = this.data.id;
    this.forms.value.valuationIn = this.data.valuationIn.id;

    var studentDto = {
      puntos:this.puntos*20,
      id: this.forms.value.valuationIn,
    };
    this.loading = true;

    this.apiRestService.valoration(this.forms.value).subscribe(data => {
      if (data)
        this.loading = false;
      this.valoracionNoRealizada = false;

      this.apiRestService.valorationGrafo(studentDto).subscribe(data => {
        if (data)
          this.loading = false;
      }, error => {
        this.valoracionNoRealizada = true;
        this.loading = false;
        console.log("Fallo la valoracion" + error)
      });
    }, error => {
      this.valoracionNoRealizada = true;
      this.loading = false;
      console.log("Fallo la valoracion" + error)
    });
  }
}
