import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import { ProfileComponent } from './profile/profile.component';


@NgModule({
  imports : [
    RouterModule.forChild([
      { path : 'login', component : LoginComponent},
      { path : '', component : HomeComponent},
      { path : 'registration', component : RegistrationComponent},
      { path : 'profile', component : ProfileComponent}
    ])
    ], exports : [
      RouterModule
  ]
})
export class UserRoutingModule { }
