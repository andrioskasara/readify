import {Component, OnInit} from '@angular/core';
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {Router} from "@angular/router";
import {BookService} from "../../../../services/services/book.service";
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrls: ['./my-books.component.scss']
})
export class MyBooksComponent implements OnInit {
  page: number = 0;
  size: number = 4;
  bookResponse: PageResponseBookResponse = {};
  message: string = '';

  constructor(
    private router: Router,
    private bookService: BookService) {
  }

  ngOnInit(): void {
    this.findAllBooks();
  }

  private findAllBooks() {
    this.bookService.findAllBooksByOwner({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (books) => {
        this.bookResponse = books;
      }
    });
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBooks();
  }

  goToPreviousPage() {
    this.page -= 1;
    this.findAllBooks();
  }

  goToPage(pageIndex: number) {
    this.page = pageIndex;
    this.findAllBooks();
  }

  goToNextPage() {
    this.page += 1;
    this.findAllBooks();
  }

  goToLastPage() {
    this.page = this.bookResponse.totalPages as number - 1;
    this.findAllBooks();
  }

  editBook(book: BookResponse) {
    this.router.navigate(['books', 'manage-book', book.id])
  }

  archiveBook(book: BookResponse) {
    this.bookService.updateArchivedBookStatus({
      'bookId': book.id as number
    }).subscribe({
      next: () => {
        book.archived = !book.archived;
      },
      error: (err) => {
        this.message = err.error.error;
      }
    });
  }

  shareBook(book: BookResponse) {
    this.bookService.updateShareableBookStatus({
      'bookId': book.id as number
    }).subscribe({
      next: () => {
        book.shareable = !book.shareable;
      },
      error: (err) => {
        this.message = err.error.error;
      }
    });
  }
}
