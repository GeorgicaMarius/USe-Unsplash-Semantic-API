import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'customCityCountryFormat'
})
export class CustomCityCountryPipe implements PipeTransform {
  transform(value: any): string {
    return value.replace(/([A-Z])/g, ' $1').replace("country", "").replace("city", "");
  }
}
