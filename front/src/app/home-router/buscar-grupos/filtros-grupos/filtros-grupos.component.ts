import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import import_carreras from './carrera-mock.json'

@Component({
  selector: 'app-filtros-grupos',
  templateUrl: './filtros-grupos.component.html',
  styleUrls: ['./filtros-grupos.component.css']
})
export class FiltrosGruposComponent implements OnInit {
  public carreras= import_carreras;
  public materias: any = [];
  public seleccionadosCarrera: any = [];
  public seleccionadosTipo: any;

  public forms;

  constructor(private formBuilder: FormBuilder) {
    this.forms = this.formBuilder.group({carrera: '', materia: '', turno: '', nombreGrupo: '',tipo:false});

  }

  ngOnInit(): void {
    this.carreras;

  }

  cargarMaterias() {
    for (let aux of this.carreras)
      if (aux.id === this.seleccionadosCarrera)
        this.materias = aux.materias;
    if (this.seleccionadosCarrera === 'TODO') {
      this.materias = [];
    }
  }

  buscarGrupos() {
    console.log(this.forms.value);
  }
}
