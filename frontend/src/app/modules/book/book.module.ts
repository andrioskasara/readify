import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BookRoutingModule} from './book-routing.module';
import {HomeComponent} from './components/home/home.component';
import {HeaderComponent} from './components/header/header.component';
import {BookListComponent} from './components/book-list/book-list.component';
import { BookItemComponent } from './components/book-item/book-item.component';
import { RatingComponent } from './components/rating/rating.component';
import { MyBooksComponent } from './components/my-books/my-books.component';
import { ManageBookComponent } from './components/manage-book/manage-book.component';
import {FormsModule} from "@angular/forms";
import { BorrowedBookListComponent } from './components/borrowed-book-list/borrowed-book-list.component';
import { ReturnedBooksComponent } from './components/returned-books/returned-books.component';
import { SearchResultComponent } from './components/search-result/search-result.component';


@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    BookListComponent,
    BookItemComponent,
    RatingComponent,
    MyBooksComponent,
    ManageBookComponent,
    BorrowedBookListComponent,
    ReturnedBooksComponent,
    SearchResultComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    BookRoutingModule
  ]
})
export class BookModule {
}
