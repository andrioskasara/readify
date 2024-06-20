import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {ActivatedRoute, Router} from "@angular/router";
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.scss']
})
export class SearchResultComponent implements OnInit {
  bookResponse: PageResponseBookResponse = {};
  searchTerm: string = '';
  page = 0;
  size = 4;
  message = '';
  messageType = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private bookService: BookService
  ) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.searchTerm = params['q'] || '';
      this.searchBooks();
    });
  }

  searchBooks(): void {
    this.bookService.searchBooks({
      q: this.searchTerm,
      page: this.page,
      size: this.size
    }).subscribe({
      next: (response) => {
        this.bookResponse = response;
        this.message = '';
      },
      error: (error) => {
        this.message = 'An error occurred while searching for books.';
        console.error(error);
      }
    });
  }

  goToFirstPage(): void {
    this.page = 0;
    this.searchBooks();
  }

  goToPreviousPage() {
    this.page -= 1;
    this.searchBooks();
  }

  goToPage(pageIndex: number) {
    this.page = pageIndex;
    this.searchBooks();
  }

  goToNextPage() {
    this.page += 1;
    this.searchBooks();
  }

  goToLastPage() {
    this.page = this.bookResponse.totalPages as number - 1;
    this.searchBooks();
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
