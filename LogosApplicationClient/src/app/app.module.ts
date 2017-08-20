import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { UserModule } from './user/user.module';
import { GameModule } from './game/game.module';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { GlobalServiceModule } from './global-service/global.service.module';
import { AuthHttp } from './global-service/http';
import {ProgramModule} from './program/program.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    UserModule,
    GlobalServiceModule,
    GameModule,
    ProgramModule,
    RouterModule.forRoot([])
  ],
  providers: [AuthHttp],
  bootstrap: [AppComponent]
})
export class AppModule { }
