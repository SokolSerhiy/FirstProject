import {AbstractControl} from '@angular/forms';

export function confirmPasswordValidator (control: AbstractControl): { [key: string]: any } {
  const rePasswordField = control.root.get('password');
  if (rePasswordField === null || control.value === rePasswordField.value) {
    return null;
  } else {
    const rePassword = rePasswordField.value;
    return {'confirmPasswordValidator': {rePassword}};
  }
}
