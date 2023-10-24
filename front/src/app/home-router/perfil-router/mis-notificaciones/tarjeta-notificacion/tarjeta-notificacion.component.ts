import {Component, Input, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {ApiRestService} from "../../../../services/api-rest.service";

@Component({
    selector: 'app-tarjeta-notificacion',
    templateUrl: './tarjeta-notificacion.component.html',
    styleUrls: ['./tarjeta-notificacion.component.css']
})
export class TarjetaNotificacionComponent implements OnInit {
    @Input() data: any;
    public idGrupo: number;
    public loading: boolean = false;
    public error:string = null;
    public success:string = null;

    constructor(private apiRestService: ApiRestService, private router: Router) {
       
    }

    ngOnInit(): void {
    }

    close() {
        this.error = null;
        this.success = null;
    }

    borrarNotificacion() {
        var notificationDto = {
            id: this.data.id
        };
        const options = {
            body: notificationDto,
        };
        this.apiRestService.deleteNotification(options).subscribe(data => {
        }, error => {
        })
    }

    aceptarSolicitud(idGrupo: any, idStudent: any) {
        this.loading = true;
        this.borrarNotificacion()
        this.apiRestService.aceptarPeticion(idGrupo, idStudent).subscribe(data => {
            this.success = "Solicitud aceptada."
            this.loading = false;
        }, error => {
            this.error = "Error al aceptar la solicitud."
            this.loading = false;
        })
    }

    aceptarVeto(idGrupo: any, idVotado: any) {
        this.loading = true;
        this.borrarNotificacion()

        var votacionDTO = {
            idVotado: idVotado,
            idGrupo: idGrupo
        };
        
        this.apiRestService.aceptarVeto(votacionDTO).subscribe(data => {
            this.success = "Se ha enviado su voto."
            this.loading = false;
        }, error => {
            this.error = "La votación ya terminó."
            this.loading = false;
        })
    }

    rechazarVeto(idGrupo: any, idVotado: any) {
        this.loading = true;
        this.borrarNotificacion()
        
        var votacionDTO = {
            idVotado: idVotado,
            idGrupo: idGrupo
        };

        const options = {
            body: votacionDTO,
        };

        this.apiRestService.rechazarVeto(options).subscribe(data => {
            this.success = "Se ha enviado su voto."
            this.loading = false;
        }, error => {
            this.error = "La votación ya terminó."
            this.loading = false;
        })
    }

    rechazarSolicitud(idGrupo: any, idStudent: any) {
        this.loading = true;
        this.borrarNotificacion()
        this.apiRestService.rechazarPeticion(idGrupo, idStudent).subscribe(data => {
            this.success = "Solicitud rechazada."
            this.loading = false;
        }, error => {
            this.error = "Error al rechazar la solicitud."
            this.loading = false;
        })
    }

    clickInvitacion(respuesta: boolean) {

    }
}
