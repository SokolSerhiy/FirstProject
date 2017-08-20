import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {confirmPasswordValidator} from '../validator/home.validators';
import {AuthHttp} from '../../global-service/http';
import {ImageData} from '../upload.img/image.data';
import {RefreshTokenService} from '../../global-service/refresh.token.service';
import {environment} from '../../../environments/environment';

@Component({
  moduleId: module.id,
  selector: 'lgs-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public addCredentialsForm: FormGroup;
  public changePasswordForm: FormGroup;
  public user = {
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    photoUrl: ''
  };
  public changePasswordFormErrors = this.buildChangePasswordFormErrors();
  public addCredentialsFormErrors = this.buildAddCredentialsFormErrors();
  constructor(private fb: FormBuilder, private http: AuthHttp, private tokenService: RefreshTokenService) { }
  ngOnInit(): void {
    this.http.get(environment.hostName + '/current/user').subscribe(
      res => {
        this.parseToUser(res.json());
        this.buildAddCredentialsForm();
      },
          err => console.log(err)
      );
    this.buildAddCredentialsForm();
    this.buildChangePasswordForm();
  }
  buildAddCredentialsForm(): void {
    this.addCredentialsForm = this.fb.group({
      firstName: [this.user.firstName],
      lastName: [this.user.lastName],
      email: [this.user.email, Validators.pattern('[a-zA-Z0-9._%-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,4}')],
      phoneNumber: [this.user.phoneNumber, Validators.pattern('^\\+380\\d{9}$')]
    });
    this.addCredentialsForm.valueChanges.subscribe(data => this.onAddCredentialsValueChange());
    this.addCredentialsForm.get('email').valueChanges
      .filter(val => val.length >= 5)
      .debounceTime(500)
      .switchMap(val => this.http.get(environment.hostName + `/validate?registeredEmail=${val}`))
      .subscribe(exist => {
        if (exist.text() === 'true') {
          this.addCredentialsForm.get('email').setErrors({exist: true});
          this.onAddCredentialsValueChange();
        }
      });
    this.onAddCredentialsValueChange();
  }
  buildChangePasswordForm(): void {
    this.changePasswordForm = this.fb.group({
      oldPassword: ['', [Validators.required, Validators.minLength(7)]],
      password: ['', [Validators.required, Validators.minLength(7)]],
      rePassword: ['', [Validators.required, Validators.minLength(7), confirmPasswordValidator]]
    });
    this.changePasswordForm.valueChanges.subscribe(data => this.onChangePasswordValueChange());
    this.changePasswordForm.get('oldPassword').valueChanges
      .filter(val => val.length >= 7)
      .debounceTime(500)
      .switchMap(val => this.http.get(environment.hostName + `/validate?password=${val}`))
      .subscribe(match => {
        if (match.text() === 'false') {
          this.changePasswordForm.get('oldPassword').setErrors({notMatch: true});
          this.onChangePasswordValueChange();
        }
      });
    this.onChangePasswordValueChange();
  }
  onAddCredentialsValueChange() {
    if (!this.addCredentialsForm) {
      return;
    }
    this.addCredentialsFormErrors = this.buildAddCredentialsFormErrors();
    const form = this.addCredentialsForm;
    const email = form.get('email');
    if (email.invalid && email.dirty) {
      if (email.errors['pattern']) {
        this.addCredentialsFormErrors.email = 'Не коректний формат електронної пошти';
      }
      if (email.errors['exist']) {
        this.addCredentialsFormErrors.email = 'Така електронна пошта вже використовується';
      }
    }
    const phoneNumber = form.get('phoneNumber');
    if (phoneNumber.invalid && phoneNumber.dirty) {
      this.addCredentialsFormErrors.phoneNumber = 'Коректний формат номеру +380971234567';
    }
  }
  onChangePasswordValueChange() {
    if (!this.changePasswordForm) {
      return;
    }
    this.changePasswordFormErrors = this.buildChangePasswordFormErrors();
    const form = this.changePasswordForm;
    const oldPassword = form.get('oldPassword');
    if (oldPassword.invalid && oldPassword.dirty) {
      this.changePasswordFormErrors.oldPassword = 'Невірно введений пароль';
    }
    const password = form.get('password');
    if (password.invalid && password.dirty) {
      this.changePasswordFormErrors.password = 'Пароль повинен бути більше 7 символів';
    }
    const rePassword = form.get('rePassword');
    if (rePassword.invalid && rePassword.dirty && password.dirty) {
      this.changePasswordFormErrors.rePassword = 'Паролі повинні співпадати';
    }
  }
  buildChangePasswordFormErrors() {
    return {
      oldPassword : '',
      password: '',
      rePassword: ''
    };
  }
  buildAddCredentialsFormErrors() {
    return {
      email: '',
      phoneNumber: ''
    };
  }
  onSubmitPassword(changePasswordForm): void {
    const dto = {
      oldPassword: changePasswordForm.value.oldPassword,
      password: changePasswordForm.value.password
    };
    this.http.post(environment.hostName + '/change/password', dto).subscribe(
      res => {
        this.tokenService.setToken(res.text());
        this.buildChangePasswordForm();
      },
      err => console.log(err)
    );
  }
  onSubmitCredentials(addCredentialsForm): void {
    const dto = {
      firstName: addCredentialsForm.value.firstName,
      lastName: addCredentialsForm.value.lastName,
      email: addCredentialsForm.value.email,
      phoneNumber: addCredentialsForm.value.phoneNumber
    };
    this.http.post(environment.hostName + '/change/data', dto).subscribe(
      res => {
        this.tokenService.setToken(res.text());
        this.changeUserData(addCredentialsForm);
        this.buildAddCredentialsForm();
      },
      err => console.log(err)
    );
  }
  parseToUser(data: any) {
    this.user = data;
    this.user.photoUrl = '/photo/' + this.user.photoUrl;
  }
  onPhotoSend(data: ImageData) {
    this.http.post(environment.hostName + '/profile/photo', data).subscribe(
      res => this.user.photoUrl = '/photo/' + res.text(),
      err => console.log('fail'));
  }
  changeUserData(addCredentialsForm) {
    this.user.firstName = addCredentialsForm.value.firstName;
    this.user.lastName = addCredentialsForm.value.lastName;
    this.user.email = addCredentialsForm.value.email;
    this.user.phoneNumber = addCredentialsForm.value.phoneNumber;
  }
}
