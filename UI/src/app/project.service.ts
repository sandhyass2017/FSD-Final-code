import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

import { Project } from './project';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable()
export class ProjectService {

  private rootUrl = 'http://localhost:8080/project';

  constructor(private http: HttpClient) { }


   getProjects (): Observable<Project[]> {
      return this.http.get<Project[]>(this.rootUrl + "/all")
      .pipe(
        tap(projects => this.log(`fetched projects`)),
        catchError(this.handleError('getProjects', []))
      );
  }

  addProject(project: Project): Observable<Project> {
    //console.log(project);
    return this.http.post<Project>(this.rootUrl, project, httpOptions).pipe(
      tap((project: Project) => this.log(`added project}`)),
      catchError(this.handleError<Project>('addProject'))
    );
  }

  deleteProject(projectId: number): any {
    return this.http.delete<any>(this.rootUrl + "/"+projectId, httpOptions).pipe(
      tap(_ => this.log(`deleted project `)),
      catchError(this.handleError<any>('deleteproject'))
    );
  }

  updateProject (project: Project): Observable<any> {
    return this.http.put(this.rootUrl, project, httpOptions).pipe(
      tap(_ => this.log(`updated project`)),
      catchError(this.handleError<any>('updateProject'))
    );
  }


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
    //console.log(message);
    //TODO: send the error to remote logging infrastructure
  }
}
