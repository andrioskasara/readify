import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private fullNameSubject = new BehaviorSubject<string | null>(this.getFullNameFromLocalStorage());
  private userIdSubject = new BehaviorSubject<number | null>(this.getUserIdFromLocalStorage());

  fullName = this.fullNameSubject.asObservable();
  userId = this.userIdSubject.asObservable();

  setFullName(fullName: string) {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.setItem('fullName', fullName);
    }
    this.fullNameSubject.next(fullName);
  }

  setUserId(userId: number) {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.setItem('userId', userId.toString());
    }
    this.userIdSubject.next(userId);
  }

  private getFullNameFromLocalStorage(): string | null {
    if (typeof window !== 'undefined' && window.localStorage) {
      return localStorage.getItem('fullName');
    }
    return null;
  }

  private getUserIdFromLocalStorage(): number | null {
    if (typeof window !== 'undefined' && window.localStorage) {
      const userId = localStorage.getItem('userId');
      return userId ? parseInt(userId, 10) : null;
    }
    return null;
  }
}
