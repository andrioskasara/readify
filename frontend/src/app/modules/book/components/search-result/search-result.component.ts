import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {ActivatedRoute} from "@angular/router";

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
}
