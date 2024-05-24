import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {BookListComponent} from "./components/book-list/book-list.component";
import {MyBooksComponent} from "./components/my-books/my-books.component";
import {ManageBookComponent} from "./components/manage-book/manage-book.component";
import {BorrowedBookListComponent} from "./components/borrowed-book-list/borrowed-book-list.component";
import {ReturnedBooksComponent} from "./components/returned-books/returned-books.component";
import {authGuard} from "../../services/guard/auth.guard";
import {SearchResultComponent} from "./components/search-result/search-result.component";
import {BookDetailsComponent} from "./components/book-details/book-details.component";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        component: BookListComponent,
        canActivate: [authGuard]
      },
      {
        path: 'my-books',
        component: MyBooksComponent,
        canActivate: [authGuard]
      },
      {
        path: 'manage-book',
        component: ManageBookComponent,
        canActivate: [authGuard]
      },
      {
        path: 'manage-book/:bookId',
        component: ManageBookComponent,
        canActivate: [authGuard]
      },
      {
        path: 'borrowed-books',
        component: BorrowedBookListComponent,
        canActivate: [authGuard]
      },
      {
        path: 'returned-books',
        component: ReturnedBooksComponent,
        canActivate: [authGuard]
      },
      {
        path: 'search',
        component: SearchResultComponent,
        canActivate: [authGuard]
      },
      {
        path: 'details/:bookId',
        component: BookDetailsComponent,
        canActivate: [authGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule {
}
