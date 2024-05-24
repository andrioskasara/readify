import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {Router} from "@angular/router";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
  page: number = 0;
  size: number = 4;
  bookResponse: PageResponseBookResponse = {};
  message: string = '';
  messageType: string = 'success';

  constructor(
    private router: Router,
    private bookService: BookService) {
  }

  ngOnInit(): void {
    this.findAllBooks();
  }

  private findAllBooks() {
    this.bookService.findAllBooks({
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

  borrowBook(book: BookResponse) {
    this.message = '';
    this.bookService.borrowBook({
      'bookId': book.id as number
    }).subscribe({
      next: () => {
        this.messageType = 'success';
        this.message = "The book is added to your list successfully";
      },
      error: (err) => {
        console.log(err);
        this.messageType = 'error';
        this.message = err.error.error;
      }
    });
  }

  showBookDetails(book: BookResponse) {
    const bookId = book.id;
    this.router.navigate(['/details', bookId]);
  }
}
