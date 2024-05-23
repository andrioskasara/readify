import {Component, OnInit, Inject, PLATFORM_ID} from '@angular/core';
import {UserService} from "../../../../services/services/user.service";
import {isPlatformBrowser} from '@angular/common';
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  fullName: string | null = '';
  searchTerm: string = '';
  isBrowser: boolean;

  constructor(
    private userService: UserService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  ngOnInit(): void {
    this.userService.fullName.subscribe(fullName => {
      this.fullName = fullName;
    });
    if (this.isBrowser) {
      const linkColor = document.querySelectorAll('.nav-link');
      linkColor.forEach(link => {
        if (window.location.href.endsWith(link.getAttribute('href') || '')) {
          link.classList.add('active');
        }
        link.addEventListener('click', () => {
          linkColor.forEach(l => l.classList.remove('active'));
          link.classList.add('active');
        });
      });
    }
  }

  search() {
    if (this.searchTerm) {
      this.router.navigate(['/search'], {queryParams: {q: this.searchTerm}});
    }
  }

  logout() {
    if (this.isBrowser) {
      localStorage.clear();
      window.location.reload();
    }
  }
}
