import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RefreshTokenService } from './refresh.token.service';
import { SmartService } from './smart.service';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [],
  providers: [RefreshTokenService, SmartService]
})
export class GlobalServiceModule { }

