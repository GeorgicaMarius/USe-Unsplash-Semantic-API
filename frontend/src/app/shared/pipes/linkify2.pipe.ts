import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'linkify2',
})
export class Linkify2Pipe implements PipeTransform {
  transform(value: any): string {
    const httpRegex = /^http:\/\//i;
    if (httpRegex.test(value)) {
      return `<a href="${value}">${value}</a>`;
    } else return value;
  }
}
