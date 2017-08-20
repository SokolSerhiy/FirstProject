import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameRoutingModule } from './game-routing.module';
import { GlobalServiceModule } from '../global-service/global.service.module';
import { GameComponent } from './game.component';

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    GameRoutingModule,
    GlobalServiceModule
  ],
  declarations: [
    GameComponent
  ],
  providers: []
})
export class GameModule { }
