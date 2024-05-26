import {Component, OnInit} from '@angular/core';
import {PageResponseBorrowedBookResponse} from "../../../../services/models/page-response-borrowed-book-response";
import {BorrowedBookResponse} from "../../../../services/models/borrowed-book-response";
import {BookService} from "../../../../services/services/book.service";
import {BookReviewRequest} from "../../../../services/models/book-review-request";
import {ReviewService} from "../../../../services/services/review.service";

@Component({
  selector: 'app-borrowed-book-list',
  templateUrl: './borrowed-book-list.component.html',
  styleUrls: ['./borrowed-book-list.component.scss']
})
export class BorrowedBookListComponent implements OnInit {
  borrowedBooks: PageResponseBorrowedBookResponse = {};
  selectedBook: BorrowedBookResponse | undefined = undefined;
  bookReviewRequest: BookReviewRequest = {bookId: 0, comment: "", rating: 0};
  page: number = 0;
  size: number = 4;

  constructor(
    private bookService: BookService,
    private reviewService: ReviewService) {
  }

  ngOnInit(): void {
    this.findAllBorrowedBooks();
  }

  private findAllBorrowedBooks() {
    this.bookService.findAllBorrowedBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (res) => {
        this.borrowedBooks = res;
      }
    });
  }

  returnBorrowedBook(book: BorrowedBookResponse) {
    this.selectedBook = book;
    this.bookReviewRequest.bookId = book.id as number;
  }


  goToFirstPage() {
    this.page = 0;
    this.findAllBorrowedBooks();
  }

  goToPreviousPage() {
    this.page -= 1;
    this.findAllBorrowedBooks();
  }

  goToPage(pageIndex: number) {
    this.page = pageIndex;
    this.findAllBorrowedBooks();
  }

  goToNextPage() {
    this.page += 1;
    this.findAllBorrowedBooks();
  }

  goToLastPage() {
    this.page = this.borrowedBooks.totalPages as number - 1;
    this.findAllBorrowedBooks();
  }


  returnBook(withReview: boolean) {
    this.bookService.returnBorrowedBook({
      'bookId': this.selectedBook?.id as number
    }).subscribe({
      next: () => {
        if (withReview) {
          this.giveReview();
        }
        this.selectedBook = undefined;
        this.findAllBorrowedBooks();
      }
    });
  }

  cancelReturn() {
    this.selectedBook = undefined;
  }

  private giveReview() {
    this.reviewService.createBookReview({
      body: this.bookReviewRequest
    }).subscribe({
      next: () => {
      }
    });
  }
}
