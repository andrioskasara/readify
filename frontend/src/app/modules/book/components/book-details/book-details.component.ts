import {Component, OnInit} from '@angular/core';
import {BookRequest} from "../../../../services/models/book-request";
import {BookService} from "../../../../services/services/book.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.scss']
})
export class BookDetailsComponent implements OnInit {
  bookRequest: BookRequest = {authorName: "", bookSummary: "", bookPlot: "", isbn: "", title: ""};
  errorMessage: Array<string> = [];
  selectedImage: string | undefined;

  constructor(
    private bookService: BookService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    const bookId = this.activatedRoute.snapshot.params['bookId'];
    if (bookId) {
      this.bookService.findBookById({
        'bookId': bookId
      }).subscribe({
        next: (book) => {
          this.bookRequest = {
            id: book.id,
            isbn: book.isbn as string,
            title: book.title as string,
            authorName: book.authorName as string,
            bookSummary: book.bookSummary as string,
            bookPlot: book.bookPlot as string,
          }
          if (book.bookCover) {
            this.selectedImage = 'data:image/jpg;base64,' + book.bookCover;
          }
        }
      })
    }
  }
}
