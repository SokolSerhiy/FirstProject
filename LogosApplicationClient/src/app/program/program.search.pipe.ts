import {Pipe, PipeTransform} from '@angular/core';
import {Program} from './program';

@Pipe({name: 'programSearch'})
export class ProgramSearchPipe implements PipeTransform {
  transform(value: Program[], search: string): Program[] {
    if (!search) {
      return value;
    } else {
      const filtered = new Array<Program>();
      for (let i = 0; i < value.length; i++) {
        if (value[i].name.indexOf(search) > -1) {
          filtered.push(value[i]);
        }
      }
      return filtered;
    }
  }
}
