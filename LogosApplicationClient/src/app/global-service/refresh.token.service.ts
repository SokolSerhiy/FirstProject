import {Injectable} from '@angular/core';
import {AuthHttp} from './http';
import * as JWT from 'jwt-decode';
import {environment} from '../../environments/environment';

@Injectable()
export class RefreshTokenService {
  constructor(private http: AuthHttp) { }
  setToken(token: string) {
    localStorage.setItem('token', token);
    this.refreshToken(token, this);
  }
  refreshToken(token: string, service: RefreshTokenService) {
    if (this.isTokenValid(token)) {
      const timeout: number = this.getTimeout(token);
        setTimeout(function () {
          service.http.get(environment.hostName + '/auth/refresh').subscribe(res => {
              localStorage.setItem('token', res.text());
              service.refreshToken(res.text(), service);
            },
            err => {
              console.log(err);
            }
          );
        }, timeout - 5000);
    }
  }
  getTimeout(token: string): number {
    const parsedToken: any = JWT(token);
    const exp = new Date(parsedToken.expiration).getTime();
    const now = new Date().getTime();
    return exp - now;
  }
  isTokenValid(token: string): boolean {
    if (token) {
      const timeout = this.getTimeout(token);
      if (timeout > 5000) {
        return true;
      }
    }
    return false;
  }
}
