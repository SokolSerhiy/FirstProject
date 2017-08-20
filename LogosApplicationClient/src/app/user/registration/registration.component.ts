import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {confirmPasswordValidator} from '../validator/home.validators';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/switchMap';
import {AuthHttp} from '../../global-service/http';
import {environment} from "../../../environments/environment";

@Component({
  selector: 'lgs-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  public registrationForm: FormGroup;
  public formErrors = {
    email: '',
    password: '',
    rePassword: ''
  };
  constructor(private fb: FormBuilder, private http: AuthHttp, private router: Router) { }
  ngOnInit() {
    this.buildForm();
  }
  onSubmit(registrationForm) {
    const dto = {
      login: registrationForm.value.email,
      password: registrationForm.value.password
    };
    this.http.post(environment.hostName + '/registration', dto).subscribe(
      ok => this.router.navigateByUrl('/login'),
      err => console.log(err));
  }
  buildForm(): void {
    this.registrationForm = this.fb.group({
      email: ['', Validators.pattern('[a-zA-Z0-9._%-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,4}')],
      password: ['', [Validators.required, Validators.minLength(7)]],
      rePassword: ['', [Validators.required, Validators.minLength(7), confirmPasswordValidator]]
    });
    this.registrationForm.valueChanges.subscribe(data => this.onValueChange(data));
    this.registrationForm.get('email').valueChanges
      .filter(val => val.length >= 5)
      .debounceTime(500)
      .switchMap(val => this.http.get(environment.hostName + `/validate?email=${val}`))
      .subscribe(exist => {
        if (exist.text() === 'true') {
          this.registrationForm.get('email').setErrors({exist: true});
          this.onValueChange();
        }
      });
    this.onValueChange();
  }
  onValueChange(data?: any) {
    if (!this.registrationForm) {
      return;
    }
    this.formErrors.password = '';
    this.formErrors.rePassword = '';
    this.formErrors.email = '';
    const form = this.registrationForm;
    const password = form.get('password');
    if (password.invalid && password.dirty) {
      this.formErrors.password = 'Мінімальна довжина 7 символів ';
    }
    const rePassword = form.get('rePassword');
    if (rePassword.invalid && password.dirty && rePassword.dirty) {
      this.formErrors.rePassword = 'Паролі повинні співпадати';
    }
    const email = form.get('email');
    if (email.invalid && email.dirty) {
      if (email.errors['pattern']) {
        this.formErrors.email = 'Не коректний формат електронної пошти';
      }
      if (email.errors['exist']) {
        this.formErrors.email = 'Така електронна пошта вже використовується';
      }
    }
  }
}
