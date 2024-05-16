/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseBookReviewResponse } from '../../models/page-response-book-review-response';

export interface FindAllReviewsByBook$Params {
  bookId: number;
  page?: number;
  size?: number;
}

export function findAllReviewsByBook(http: HttpClient, rootUrl: string, params: FindAllReviewsByBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBookReviewResponse>> {
  const rb = new RequestBuilder(rootUrl, findAllReviewsByBook.PATH, 'get');
  if (params) {
    rb.path('bookId', params.bookId, {});
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseBookReviewResponse>;
    })
  );
}

findAllReviewsByBook.PATH = '/reviews/book/{bookId}';
