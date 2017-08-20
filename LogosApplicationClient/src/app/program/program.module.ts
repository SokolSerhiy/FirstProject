import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProgramRoutingModule } from './program-routing.module';
import { GlobalServiceModule } from '../global-service/global.service.module';
import {ProgramComponent} from './program.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ProgramSearchPipe} from './program.search.pipe';
import {SharedComponentsModule} from '../shared-components/shared.components.module';

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    ProgramRoutingModule,
    GlobalServiceModule,
    SharedComponentsModule
  ],
  declarations: [
    ProgramComponent,
    ProgramSearchPipe
  ],
  providers: [
    ProgramSearchPipe
  ]
})
export class ProgramModule { }
