import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthHttp} from '../../global-service/http';
import {RefreshTokenService} from '../../global-service/refresh.token.service';
import {SmartService} from '../../global-service/smart.service';

@Component({
  moduleId: module.id,
  selector: 'lgs-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loginForm: FormGroup;
  private loginUrl: string;
  constructor(private fb: FormBuilder, private http: AuthHttp, private router: Router,
              private tokenService: RefreshTokenService, private smart: SmartService) { }
  ngOnInit(): void {
    this.buildForm();
    this.smart.baseLinks.subscribe(map => this.loginUrl = map.login);
  }
  onSubmit(form) {
    this.http.post(this.loginUrl, form.value).subscribe(
      res => {
        const resource = res.json();
        this.tokenService.setToken(resource.token);
        this.smart.refreshBaseLinks(resource.links);
        this.router.navigate(['']);
      },
      err => console.log(err)
      );
  }
  private buildForm(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
}
