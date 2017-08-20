import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import {UserRoutingModule} from './user-routing.module';
import {GlobalServiceModule} from '../global-service/global.service.module';
import {HomeComponent} from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import {ProfileComponent} from './profile/profile.component';
import {AuthHttp} from '../global-service/http';
import {UploadImgComponent} from './upload.img/upload.img.component';


@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    ReactiveFormsModule,
    UserRoutingModule,
    GlobalServiceModule
  ],
  declarations: [
    LoginComponent,
    HomeComponent,
    RegistrationComponent,
    ProfileComponent,
    UploadImgComponent
  ],
  providers: [AuthHttp]
})
export class UserModule { }
