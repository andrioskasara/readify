import {Component, OnInit} from '@angular/core';
import {PageResponseBorrowedBookResponse} from "../../../../services/models/page-response-borrowed-book-response";
import {BorrowedBookResponse} from "../../../../services/models/borrowed-book-response";
import {BookService} from "../../../../services/services/book.service";

@Component({
  selector: 'app-returned-books',
  templateUrl: './returned-books.component.html',
  styleUrls: ['./returned-books.component.scss']
})
export class ReturnedBooksComponent implements OnInit {

  returnedBooks: PageResponseBorrowedBookResponse = {};
  page: number = 0;
  size: number = 4;
  message: string = '';
  messageType: string = 'success';

  constructor(private bookService: BookService) {
  }

  ngOnInit(): void {
    this.findAllReturnedBooks();
  }

  private findAllReturnedBooks() {
    this.bookService.findAllReturnedBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (res) => {
        this.returnedBooks = res;
      }
    });
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllReturnedBooks();
  }

  goToPreviousPage() {
    this.page -= 1;
    this.findAllReturnedBooks();
  }

  goToPage(pageIndex: number) {
    this.page = pageIndex;
    this.findAllReturnedBooks();
  }

  goToNextPage() {
    this.page += 1;
    this.findAllReturnedBooks();
  }

  goToLastPage() {
    this.page = this.returnedBooks.totalPages as number - 1;
    this.findAllReturnedBooks();
  }

  approveReturn(book: BorrowedBookResponse) {
    if (!book.returned) {
      this.messageType = 'error';
      this.message = 'The book is not returned';
      return;
    }
    this.bookService.approveReturnedBook({
      'bookId': book.id as number
    }).subscribe({
      next: () => {
        this.messageType = 'success';
        this.message = 'Book return approved';
        this.findAllReturnedBooks();
      }
    });
  }
}
