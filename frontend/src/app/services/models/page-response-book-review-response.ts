/* tslint:disable */
/* eslint-disable */
import { BookReviewResponse } from '../models/book-review-response';
export interface PageResponseBookReviewResponse {
  first?: boolean;
  last?: boolean;
  pageContent?: Array<BookReviewResponse>;
  pageNumber?: number;
  pageSize?: number;
  totalElements?: number;
  totalPages?: number;
}
