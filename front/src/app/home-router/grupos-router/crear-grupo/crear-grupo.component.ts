import {Component, OnInit} from '@angular/core';
import {ApiRestService} from "../../../services/api-rest.service";
import {AuthService} from "../../../services/auth/auth.service";
import {Router} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
    selector: 'app-crear-grupo',
    templateUrl: './crear-grupo.component.html',
    styleUrls: ['./crear-grupo.component.css']
})
export class CrearGrupoComponent implements OnInit {


    public formImg: string = "https://api.time.com/wp-content/uploads/2021/02/laptop-home-office.jpg";

    public error: string;
    public errorStyle: string = "";
    public success: string = "";

    public user: any = [];
    public misGrupos: any[] = [];
    public materias: any = [];
    public turnos: any = [];
    public formTipoGrupo: any;
    public loading: boolean;
    public validarForm: any;

    constructor(private apiRest: ApiRestService, private authService: AuthService, private router: Router, private builder: FormBuilder) {
        this.loading = true
        this.getMisGrupos()
        this.verUsuario();
        this.validarForm = this.builder.group({
            name: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(40)]],
            description: ['', [Validators.required,Validators.minLength(10), Validators.maxLength(80)]],
            maxStudents: ['',[Validators.required, Validators.min(2), Validators.max(8)]],
            typeGroup: ['',[Validators.required]],
            closed : ['', [Validators.required]],
            shift: [''],
            career: [''],
            subject: [''],
        })
    }

    ngOnInit(): void {
    }

    close() {
        this.error = null;
    }

    private verUsuario() {
        this.loading = true
        this.apiRest.verUsuario(this.authService.getId()).subscribe(data => {
                this.user = data;
                this.materias = [...new Set([...this.user.materiasDia, ...this.user.materiasTarde, ...this.user.materiasNoche])]
                if (data)
                    this.loading = false
            },
            error => {
                this.loading = false
            });
    }

    public verTurnos(materia : any): void {
        console.log(materia)
        this.apiRest.getTurnos(this.user.facultad.abr, materia).subscribe(
            data => {
                this.turnos = data
                if (data)
                    this.loading = false
            },
            error => {
                this.loading = false
            }
        );
    }


    private getMisGrupos(): void {
        this.loading = true
        this.apiRest.misGrupos().subscribe(
            data => {
                this.misGrupos = data;
                if (data)
                    this.loading = false;
            },
            error => {
                this.loading = false
            }
        );
    }

    private registroErroneo(msj: string) {
        this.error = "Registro erróneo, " + msj;
        this.errorStyle = "form-control-error";
        this.success = "";
    }

    public crearGrupo() {

        console.log(this.validarForm.value.subject)

        if(this.validarForm.value.subject == ''){
            this.validarForm.value.subject = null
            this.validarForm.value.shift = null
            this.validarForm.value.career = null
        }
        this.error = "";
        this.errorStyle = "";
        const groupDto = {
            name: this.validarForm.value.name,
            image: this.formImg,
            description: this.validarForm.value.description,
            closed: this.validarForm.value.closed,
            maxNumberOfStudents: this.validarForm.value.maxStudents,
            shift: this.validarForm.value.shift != null ? this.validarForm.value.shift : null,
            career: this.validarForm.value.career != null ? this.user.carreras.filter((m: { id: string; }) => m.id == this.validarForm.value.career)[0].name : null,
            subject: this.validarForm.value.subject != null ? this.materias.filter((m: { codigo: string; }) => m.codigo == this.validarForm.value.subject)[0].title : null,
            idSubject: this.validarForm.value.subject
        };
        this.loading = true
        this.apiRest.crearGrupo(groupDto).subscribe(
            data => {
                if (data)
                    this.loading = false;
                console.log("Se guardo correctamente el grupo" + groupDto)
                this.success = "Grupo creado exitosamente";
                this.router.navigate(["home", "mis-grupos"])
            },
            error => {
                this.error = "Error al crear el grupo, intentelo en otro momento";
                this.loading = false
            }
        );
    }

    hideShow(option: any) {
        if (option.value != 'Materia') {
            document.getElementsByName("grupoMateria").forEach((e) => {
                e.style.display = 'none'
            })
        } else {
            this.formTipoGrupo = option
            document.getElementsByName("grupoMateria").forEach((e) => {
                e.style.display = ''
            })
        }
    }
}
