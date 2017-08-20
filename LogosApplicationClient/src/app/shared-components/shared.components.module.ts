import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {PageableComponent} from './pageable/pageable.component';
import {FormsModule} from '@angular/forms';
import {PageSizeComponent} from "./page-size/page.size.component";

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule
  ],
  declarations: [
    PageableComponent,
    PageSizeComponent
  ],
  exports: [
    PageableComponent,
    PageSizeComponent
  ],
  providers: []
})
export class SharedComponentsModule { }
