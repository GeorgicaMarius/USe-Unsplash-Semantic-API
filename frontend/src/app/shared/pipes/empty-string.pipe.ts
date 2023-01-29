import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'emptyString',
})
export class EmptyStringPipe implements PipeTransform {
  transform(value: unknown, ...args: unknown[]): unknown {
    return value ? value : 'N/A';
  }
}
