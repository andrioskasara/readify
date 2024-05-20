import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private fullNameSubject = new BehaviorSubject<string | null>(this.getFullNameFromLocalStorage());
  fullName = this.fullNameSubject.asObservable();

  setFullName(fullName: string) {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.setItem('fullName', fullName);
    }
    this.fullNameSubject.next(fullName);
  }

  private getFullNameFromLocalStorage(): string | null {
    if (typeof window !== 'undefined' && window.localStorage) {
      return localStorage.getItem('fullName');
    }
    return null;
  }
}
