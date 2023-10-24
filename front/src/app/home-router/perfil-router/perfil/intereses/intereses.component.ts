import {Component, OnInit} from '@angular/core';
import {ApiRestService} from "../../../../services/api-rest.service";
import {Router} from "@angular/router";
import {AuthService} from "../../../../services/auth/auth.service";

@Component({
  selector: 'app-intereses',
  templateUrl: './intereses.component.html',
  styleUrls: ['./intereses.component.css']
})
export class InteresesComponent implements OnInit {
  public formFacultad: any;
  public formDepartamento: any = "";
  public formCarrera: any = "";
  public formMateria: any = "";
  public formTag: any = "";
  public formTurno: any = "";


  public facultades: any = [];
  public departamentos: any = [];
  public carreras: any = [];
  public materias: any = [];
  public tags: any = [];
  public user: any = [];
  public turnos: any = [];

  public error: string = null;
  public success: string = null;
  private carreraCargada: boolean = false;
  public loading: boolean;

  constructor(private apiRest: ApiRestService, private router: Router, private authSeervice: AuthService) {
    this.loading=true;
    this.verUsuario();
    this.getFacultades();
    this.getTags();
  }

  ngOnInit(): void {

  }

  close() {
    this.error = null;
    this.success = null;
}

  private getTags(): void {
    this.loading=true;
    this.apiRest.getTags().subscribe(
      data => {
        this.tags = data
        if(data)
          this.loading=false
      },
      error => {
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
        this.loading=false
      }
    );
  }

  private getFacultades(): void {
    this.loading=true;
    this.apiRest.getFacultades().subscribe(
      data => {
        this.facultades = data
        if(data)
          this.loading=false
      },
      error => {
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
        this.loading=false
      }
    );
  }

  public cargarDepartamentos(): void {
    this.loading=true
    this.formCarrera = ""
    this.formMateria = ""
    this.formTurno = ""
    this.formDepartamento = ""
    this.apiRest.getDepartamentos(this.formFacultad).subscribe(
      data => {
        this.departamentos = data
        if(data)
          this.loading=false
      },
      error => {
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
        this.loading=false
      }
    );
    console.log(this.departamentos)

  }

  public cargarCarreras(): void {
    this.loading=true
    this.formCarrera = ""
    this.formMateria = ""
    this.formTurno = ""
    this.apiRest.getCarreras(this.formFacultad, this.formDepartamento.toString()).subscribe(
      data => {
        this.carreras = data
        if(data)
          this.loading=false
      },
      error => {
        this.loading=false
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
      }
    );
  }

  public cargarMaterias(): void {
    this.loading=true
    this.formMateria = ""
    this.formTurno = ""
    this.apiRest.getMaterias(this.formFacultad, this.formDepartamento.toString(), this.formCarrera.toString()).subscribe(
      data => {
        this.materias = data
        if(data)
          this.loading=false
      },
      error => {
        this.loading=false
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
      }
    );
  }

  public cargarTurnos(): void {
    this.loading=true
    this.formTurno = ""
    let aux2: any;
    for (let o of this.materias)
      if (o.id == this.formMateria)
        aux2 = o.codigo
    this.apiRest.getTurnos(this.formFacultad, aux2).subscribe(
      data => {
        this.turnos = data
        if(data)
          this.loading=false
      },
      error => {
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
        this.loading=false
      }
    );
  }

  public cambiarFacultad(){
    this.loading=true
    let aux: any;
    for (let o of this.facultades)
      if (o.abr == this.formFacultad)
        aux = o.id

    var studentDto = {
      idFacultad: aux,
    };

    this.apiRest.cambiarFacultad(studentDto).subscribe(data => {
      this.loading = false
    },error =>{
      this.error = "No se puede cambiar de facultad ya que está anotado en carreras de la actual.";
      this.loading = false
    });
  }

  public onRegisterMateria(): void {
    this.loading=true
    let aux: any;
    for (let o of this.facultades)
      if (o.abr == this.formFacultad)
        aux = o.id

    let aux2: any;
    for (let o of this.materias)
      if (o.id == this.formMateria)
        aux2 = o.codigo

    if (!aux || !this.formCarrera || !aux2 || !this.formTurno) {
      this.loading=false
      this.error = "No se puede agregar, debe completar todos los campos del Régimen Académico";
      return
    }
    var studentDto = {
      idFacultad: aux,
      idCarrera: this.formCarrera,
      idMateria: aux2,
      turno: this.formTurno
    };

    for (let car of this.user.carreras) {
      if (car.id == this.formCarrera) {
        this.carreraCargada = true;
        console.log(this.carreraCargada)
        break;
      }
    }
    if (!this.carreraCargada) {
      this.apiRest.cargarCarrera(studentDto).subscribe(data => {
          this.apiRest.cargarMateria(studentDto).subscribe(data => {
              if(data)
                this.loading=false
              this.verUsuario();
              this.success = "Datos cargados con éxito."
            },
            error => {
              this.loading=false
              this.error = "No se puede agregar a una materia que ya esta inscripto";
            });
        },
        error => {
          this.loading=false
          this.error = "No se ha podido registrar la carrera, intentelo mas tarde";
        });
    }
    else {
        this.loading=true
      this.apiRest.cargarMateria(studentDto).subscribe(data => {
          if(data)
            this.loading=false
          this.verUsuario();
          this.success = "Datos cargados con éxito."
        },
        error => {
          this.loading=false
          this.error = "No se puede agregar a una materia que ya esta inscripto";
        });
    }
  }

  public onRegisterTag(): void {
    if (!this.formTag) {
      this.error = "Debe seleccionar un Tag, para agregar";
      return
    }
    this.loading=true
    var studentDto = {
      idInteres: this.formTag,
    };

    this.apiRest.cargarInteres(studentDto).subscribe(data => {
        if(data)
          this.loading=false
        this.verUsuario();
        this.success = "Interés cargado con éxito."
      },
      error => {
        this.error = "No se puede agregar un Tag que ya posse";
        this.loading=false
      });


  }

  private verUsuario() {
    this.loading=true;
    this.apiRest.verUsuario(this.authSeervice.getId()).subscribe(data => {
        this.user = data;
        this.formFacultad = this.user.facultad.abr
        this.cargarDepartamentos()
        if(data)
          this.loading=false
      },
      error => {
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
        this.loading=false
      });
  }

  public onDeleteInteres(tag: any): void {
    this.loading=true;
    var studentDto = {
      idInteres: tag.id
    };
    const options = {
      body: studentDto,
    };
    console.log(options)
    this.apiRest.borrarInteres(options).subscribe(data => {
        this.verUsuario();
        if(data)
          this.loading=false
        this.success = "Interés borrado con éxito."
      },
      error => {
        this.loading=false
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
      });


  }

  public onDeleteMateria(mat: any): void {
      this.loading=true
    var studentDto = {
      idMateria: mat.codigo,
    };
    const options = {
      body: studentDto,
    };
    this.apiRest.abandonarMateria(options).subscribe(data => {
        if(data)
          this.loading=false
        this.verUsuario();
        this.success = "Materia borrada con éxito."
      },
      error => {
        this.loading=false
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
      });
  }

  public onDeleteCarrera(car: any) {
      this.loading=true
    var studentDto = {
      idCarrera: car.id,
    };
    const options = {
      body: studentDto,
    };
    this.apiRest.abandonarCarrera(options).subscribe(data => {
        if(data)
          this.loading=false
        this.verUsuario();
        this.success = "Carrera borrada con éxito."
      },
      error => {
        this.loading=false
        this.error = "Error al conectar con el servidor. Intente recargar el sitio."
      });
  }
}
