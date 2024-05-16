import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {skipUntil} from 'rxjs';
@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent {
  message: string = '';
  isOk: boolean = true;
  isSubmitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  onCodeCompleted(token: any): void {
    this.confirmAccount(token);
  }

  toLogin(): void {
    this.router.navigate(['login']);
  }

  private confirmAccount(token: string): void {
    this.authService.confirm({
      token
    }).subscribe({
      next: () => {
        this.message = 'Your account has been activated successfully.\nYou can proceed to log in.';
        this.isSubmitted = true;
        this.isOk = true;
      },
      error: () => {
        this.message = 'Activation token has expired. A new token has been sent to your email address.';
        this.isSubmitted = true;
        this.isOk = false;
      }
    });
  }

  protected readonly skipUntil = skipUntil;
}
