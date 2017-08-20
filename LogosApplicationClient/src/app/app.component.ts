import {Component, OnInit} from '@angular/core';
import {RefreshTokenService} from './global-service/refresh.token.service';
import {Router} from '@angular/router';
import {AuthHttp} from './global-service/http';
import {environment} from '../environments/environment';
import {SmartService} from './global-service/smart.service';

@Component({
  selector: 'lgs-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  map = {};
  constructor(private tokenService: RefreshTokenService, private router: Router, private http: AuthHttp, private smart: SmartService) { }
  ngOnInit(): void {
    if (!this.isTokenValid()) {
      this.removeToken();
      this.navigateToLogin();
    }
    this.smart.baseLinks.subscribe(map => this.map = map);
    this.refreshLinks();
  }
  exit() {
    this.removeToken();
    this.navigateToLogin();
    this.refreshLinks();
  }
  private refreshLinks() {
    this.http.get(environment.hostName).subscribe(
      res => this.smart.refreshBaseLinks(res.json().links),
      err => console.log(err));
  }
  private removeToken() {
    localStorage.removeItem('token');
  }
  private isTokenValid(): boolean {
    return this.tokenService.isTokenValid(this.getToken());
  }
  private getToken(): string {
    return localStorage.getItem('token');
  }
  private navigateToLogin() {
    this.router.navigate(['/login']);
  }
}
