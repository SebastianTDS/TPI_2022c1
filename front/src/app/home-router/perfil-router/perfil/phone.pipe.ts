import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'phone'
})
export class PhonePipe implements PipeTransform {

  transform(tel: any) {
    if (tel) {
      const value = tel.toString().replace(/\D/g, '');

      let foneFormatado = '';

      if (value.length > 12) {
        foneFormatado = value.replace(/(\d{2})?(\d{2})?(\d{5})?(\d{4})/, '+$1 ($2) $3-$4');

      } else if (value.length > 11) {
        foneFormatado = value.replace(/(\d{2})?(\d{2})?(\d{4})?(\d{4})/, '+$1 ($2) $3-$4');

      } else if (value.length > 10) {
        foneFormatado = value.replace(/(\d{2})?(\d{5})?(\d{4})/, '($1) $2-$3');

      } else if (value.length > 9) {
        foneFormatado = value.replace(/(\d{2})?(\d{4})?(\d{4})/, '($1) $2-$3');

      } else if (value.length > 8) {
        foneFormatado = value.replace(/^(\d{2})?(\d{0,4})?(\d{4})/, '($1) $2-$3');

      } else if (value.length > 7) {
        foneFormatado = value.replace(/^(\d{2})?(\d{0,4})?(\d{3})/, '($1) $2-$3');

      } else {
        if (tel !== '') { foneFormatado = value.replace(/^(\d*)/, '($1'); }
      }
      return foneFormatado;
    }

    return null
  }
}