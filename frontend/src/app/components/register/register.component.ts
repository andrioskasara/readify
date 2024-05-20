import {Component} from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registrationRequest: RegistrationRequest = {email: '', firstName: '', lastName: '', password: ''};
  errorMessage: Array<string> = [];
  isWaiting: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService) {
  }

  register(): void {
    this.errorMessage = [];
    this.isWaiting = true;
    this.authService.register({
      body: this.registrationRequest
    }).subscribe({
      next: () => {
        this.router.navigate(['activate-account']);
      },
      error: (err) => {
        if (err.error && err.error.error === "User with that email is already registered") {
          this.errorMessage.push(err.error.error);
          this.isWaiting = false;
        } else {
          this.errorMessage = err.error.validationErrors;
        }
        this.isWaiting = false;
      },
      complete: () => {
        this.isWaiting = false;
      }
    });
  }

  login(): void {
    this.router.navigate(['login'])
  }
}
