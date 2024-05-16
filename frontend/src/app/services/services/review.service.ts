/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createBookReview } from '../fn/review/create-book-review';
import { CreateBookReview$Params } from '../fn/review/create-book-review';
import { findAllReviewsByBook } from '../fn/review/find-all-reviews-by-book';
import { FindAllReviewsByBook$Params } from '../fn/review/find-all-reviews-by-book';
import { PageResponseBookReviewResponse } from '../models/page-response-book-review-response';

@Injectable({ providedIn: 'root' })
export class ReviewService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `createBookReview()` */
  static readonly CreateBookReviewPath = '/reviews';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createBookReview()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createBookReview$Response(params: CreateBookReview$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createBookReview(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createBookReview$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createBookReview(params: CreateBookReview$Params, context?: HttpContext): Observable<number> {
    return this.createBookReview$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findAllReviewsByBook()` */
  static readonly FindAllReviewsByBookPath = '/reviews/book/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllReviewsByBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllReviewsByBook$Response(params: FindAllReviewsByBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBookReviewResponse>> {
    return findAllReviewsByBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllReviewsByBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllReviewsByBook(params: FindAllReviewsByBook$Params, context?: HttpContext): Observable<PageResponseBookReviewResponse> {
    return this.findAllReviewsByBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBookReviewResponse>): PageResponseBookReviewResponse => r.body)
    );
  }

}
