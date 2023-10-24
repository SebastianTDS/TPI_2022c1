import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Group } from 'src/app/services/models/group';
import { IGrupo } from 'src/app/services/models/IGrupo';
import { IuserDto } from 'src/app/services/models/iuserDto';
import {ApiRestService} from "../../../services/api-rest.service";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
    selector: 'app-grupo-home',
    templateUrl: './grupo-home.component.html',
    styleUrls: ['./grupo-home.component.css']
})
export class GrupoHomeComponent implements OnInit {

    public grupo: Group = {};
    public infoGrupoAdmin: IGrupo = {}
    public imageProfile: any;
    public administrador: IuserDto = {}
    public isAdmin: boolean = false;
    public error: boolean;
    public success: boolean;
    public mesage: string;
    public tags: any;
    public tag: string = "Tags";
    public loading: boolean;
    public formClosed: boolean;
    public validarForm : any;

    constructor(private route: ActivatedRoute, private apiRest: ApiRestService, private routeLink: Router, private auth :AuthService, private builder : FormBuilder) {
        this.loading = false;
        this.route.parent?.params.subscribe(params => {
            this.apiRest.infoGrupo(params['id']).subscribe(
                data => {
                    this.grupo = data;
                    this.getTagsGrupo(params['id']);
                    this.getGroupImageProfile();

                    this.validarForm = this.builder.group({
                        name: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(40)]],
                        description: ['', [Validators.required,Validators.minLength(10), Validators.maxLength(80)]],
                        maxStudents: ['',[Validators.required, Validators.min(2), Validators.max(8)]],
                    })
                    this.validarForm.controls['name'].setValue(data.name)
                    this.validarForm.controls['description'].setValue(data.description)
                    this.validarForm.controls['maxStudents'].setValue(data.maxNumberOfStudents)
                    this.formClosed = data.closed
                    this.getTagsGrupo(params['id'])
                }, error => {
                    this.mesage = error.error.message
                    this.error = true
                }
            );
        });
        this.getTags()
    }

    ngOnInit(): void {
    }

    openPopup(id: any) {
        document.getElementById(id)?.classList.remove("modal-hide")
        document.getElementById("black-screen")?.classList.add("black-screen")
        document.getElementsByTagName("body")[0].style.overflowY = "hidden"
    }

    closePopup(id: any) {
        document.getElementById(id)?.classList.add("modal-hide")
        document.getElementById("black-screen")?.classList.remove("black-screen")
        document.getElementsByTagName("body")[0].style.overflowY = "scroll"
    }

    close() {
        this.error = false;
        this.success = false;
    }

    myId(){
        return this.auth.getId();
    }

    private getTags() {
        this.apiRest.getTags().subscribe(
            data => {
                this.tags = data
            }, error => {
                this.mesage = error.error.message
                this.error = true
            }
        )
    }

    public getTagsGrupo(id: any): void {
        this.loading = true;
        this.apiRest.interesGrupo(id).subscribe(
            data => {
                this.loading = false;
                this.infoGrupoAdmin = data;
                this.administrador = this.grupo.students.filter(e => e.id == data.administrador.parKey)[0]
                if(this.administrador.id == this.auth.getId())
                    this.isAdmin = true;
            }, error => {
                this.loading = false;
                this.mesage = error.error.message
                this.error = true
            }
        );
    }

    addTagGrupo() {
        this.loading = true;
        let body = {
            idInteres: this.tag != "Tags" ? this.tag : '',
            idGrupo: this.grupo.id
        }

        this.apiRest.cargarInteresGrupo(body).subscribe(
            data => {
                this.infoGrupoAdmin = data;
                this.loading = false;
            }, error => {
                this.mesage = error.error.message
                this.error = true
                this.loading = false;
            }
        );
    }

    onDeleteInteres(idInteres: any) {
        this.loading = true;
        let body = {
            idInteres: idInteres,
            idGrupo: this.grupo.id,
            idAdmin: this.administrador['parKey']
        };

        const options = {
            body: body
        };

        this.apiRest.borrarInteresGrupo(options).subscribe(
            data => {
                this.infoGrupoAdmin = data;
                this.loading = false;
            }, error => {
                this.mesage = error.error.message
                this.error = true
                this.loading = false;
            }
        );
    }

    modificarGrupo(idGrupo: any) {
        const groupDto = {
            name: this.validarForm.value.name,
            image: this.grupo.image,
            description: this.validarForm.value.description,
            closed: this.formClosed,
            maxNumberOfStudents: this.validarForm.value.maxStudents,
            shift: this.grupo.shift,
            career: this.grupo.career,
            subject: this.grupo.subject,
            idSubject: this.grupo['idSubject']
        };
        
        this.loading = true;
        this.apiRest.modificarGrupo(idGrupo, groupDto).subscribe(
            data => {
                this.loading = false;
                this.ngOnInit();
            },
            error => {
                this.loading = false;
                this.mesage = "No se pudo modificar"
                this.error = true

            }
        );


    }

    onDeleteGroup() {
        this.loading = true;
        let groupDeleteDto = {
            id: this.grupo.id
        };

        const options = {
            body: groupDeleteDto
        };
        console.log(options)
        this.apiRest.borrarGrupo(options).subscribe(
            data => {
                if (data)
                    this.loading = false;
                this.routeLink.navigate(["/home"]);
            }
            , error => {
                this.loading = false;
                this.mesage = "No se pudo borrar el grupo. Intentelo más tarde"
                this.error = true

            }
        );
    }

    public onImagenPerfil(event: any): any {
        this.loading = true
        console.log(event.target.files[0]);
        const fileCapture = event.target.files[0];
        try {
            const formularioDeDatos = new FormData();
            formularioDeDatos.append('file', fileCapture);
            formularioDeDatos.append('idUser', localStorage.getItem("CognitoIdentityServiceProvider.v5qnauc2t152bap78u7djjjjr.LastAuthUser"))
            formularioDeDatos.append('idGroup', this.grupo.id as unknown as string)
            formularioDeDatos.append('name', fileCapture.name)
            formularioDeDatos.append('userName', "null")
            this.apiRest.uploadGroupImgPerfil(formularioDeDatos).subscribe(data => {
                    if (data) {
                        this.getGroupImageProfile()
                        this.loading = false
                    }
                }, error => {
                    this.loading = false
                }
            )
        } catch (e) {
            this.loading = false
        }
    }

    public getGroupImageProfile() {
        this.apiRest.getGroupUrlImageProfile(this.grupo.id).subscribe(data => {
            this.imageProfile = data.url;
            if (this.tags) this.loading = false;
        }, error => {
            if (this.tags) this.loading = false;
        })
    }
    
    public solicitarExpulsion(idStudent: any){
        this.closePopup(idStudent)
        this.loading = true;

        var body = {
            idVotado: idStudent,
            idGrupo: this.grupo.id
        }

        this.apiRest.crearVotacion(body).subscribe(data => {
            this.loading = false;
            this.success = true;
            this.mesage = "Se ha enviado la solicitud de expulsión."
        }, error => {
            this.loading = false;
            this.error = true;
            this.mesage = "Ya se solicitó una votación de expulsión a ese usuario."
        })
    }


    grouoClose(b: boolean) {
        this.formClosed = b;
        console.log(this.formClosed);
    }
}
