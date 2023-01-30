import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'linkify',
})
export class LinkifyPipe implements PipeTransform {
  transform(value: any): string {
    const httpRegex = /^http:\/\//i;
    if (httpRegex.test(value)) {
      const link = value;
      const segments = value.split('/');
      let text = segments.pop();
      text = text.replace(/_/g, ' ');
      return `<a href="${link}">${text}</a>`;
    } else return value;
  }
}
