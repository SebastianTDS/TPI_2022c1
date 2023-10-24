import {Component} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public cssUrl: string;
  private modoOscuro: string = '/assets/css/accesibility/modoOscuro.css';
  private modoDia: string = '/assets/css/accesibility/modoDia.css';
  private modoProtanopia: string = '/assets/css/accesibility/modoProtanopia.css';
  private modoDeuteranopia: string = '/assets/css/accesibility/modoDeuteranopia.css';
  private modoTritanopia: string = '/assets/css/accesibility/modoTritanopia.css';
  private modoAcromatia: string = '/assets/css/accesibility/modoAcromatia.css';

  constructor(public sanitizer: DomSanitizer) {
    this.cssUrl = this.modoDia;
  }

  cambiarEstilos(temaSeleccionado: string) {
    switch (temaSeleccionado) {
      case 'Claro':
        this.cssUrl = this.modoDia;
        break;
      case 'Oscuro':
        this.cssUrl = this.modoOscuro;
        break;
      case 'Protanopia':
        this.cssUrl = this.modoProtanopia;
        break;
      case 'Deuteranopia':
        this.cssUrl = this.modoDeuteranopia;
        break;
      case 'Tritanopia':
        this.cssUrl = this.modoTritanopia;
        break;
      case 'Acromatía':
        this.cssUrl = this.modoAcromatia;
        break;
      default:
        this.cssUrl = this.modoDia;
        break;

    }
  }
}
