import {Component, OnInit} from '@angular/core';
import {BookRequest} from "../../../../services/models/book-request";
import {BookService} from "../../../../services/services/book.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.scss']
})
export class ManageBookComponent implements OnInit {
  bookRequest: BookRequest = {authorName: "", bookSummary: "", isbn: "", title: ""};
  errorMessage: Array<string> = [];
  selectedImage: string | undefined;
  selectedBookCoverImage: any;

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
            isShareable: book.shareable
          }
          if (book.bookCover) {
            this.selectedImage = 'data:image/jpg;base64,' + book.bookCover;
          }
        }
      })
    }
  }

  saveBook() {
    this.bookService.createBook({
      body: this.bookRequest
    }).subscribe({
      next: (bookId) => {
        this.bookService.uploadBookCoverImage({
          'bookId': bookId,
          body: {
            file: this.selectedBookCoverImage
          }
        }).subscribe({
          next: () => {
            this.router.navigate(['/books/my-books']);
          }
        })
      },
      error: (err) => {
        this.errorMessage = err.error.validationErrors;
      }
    });
  }

  onFileSelected(file: any) {
    this.selectedBookCoverImage = file.target.files[0];
    if (this.selectedBookCoverImage) {
      const fileReader = new FileReader();
      fileReader.onload = () => {
        this.selectedImage = fileReader.result as string;
      }
      fileReader.readAsDataURL(this.selectedBookCoverImage);
    }
  }
}
