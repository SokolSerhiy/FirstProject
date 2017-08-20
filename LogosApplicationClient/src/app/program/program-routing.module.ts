import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {ProgramComponent} from './program.component';

@NgModule({
  imports : [
    RouterModule.forChild([{ path : 'program', component : ProgramComponent}])
  ], exports : [
    RouterModule
  ]
})
export class ProgramRoutingModule { }
