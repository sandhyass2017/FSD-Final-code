import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

import { User } from './user';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class UserService {

   private userUrl = 'http://localhost:8080/user/all'; 	
   private rootUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient) { }

   /** GET Users from the server */
  getUsers (): Observable<User[]> {
      return this.http.get<User[]>(this.userUrl)
      .pipe(
        tap(users => this.log(`fetched users`)),
        catchError(this.handleError('getUsers', []))
      );
  }


   /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };

  }

  private log(message: string) {
    ///console.log(message);
    //TODO: send the error to remote logging infrastructure
  }



  /** POST: add a new user to the server */
  addUser (user: User): Observable<User> {

    var addUrl = "http://localhost:8080/user/add";

    return this.http.post<User>(addUrl, user, httpOptions).pipe(
      tap((user: User) => this.log(`added user w/ id=${user.userId}`)),
      catchError(this.handleError<User>('addHero'))
    );
  }

  deleteUser(userId: number): any {

     const url = 'http://localhost:8080/user/' + userId;

    return this.http.delete<any>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted user `)),
      catchError(this.handleError<any>('deleteuser'))
    );
  }

   /** PUT: update the user on the server */
  updateUser (user: User): Observable<any> {
    return this.http.put(this.rootUrl + "/update", user, httpOptions).pipe(
      tap(_ => this.log(`updated user`)),
      catchError(this.handleError<any>('updateUser'))
    );
  }

}
