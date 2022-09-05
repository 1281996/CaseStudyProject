import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

const AUTHOR_URL = "http://localhost:8080/api/v1/digitalbooks/author";
@Injectable({
  providedIn: 'root'
})
export class BookService {


  constructor(private http: HttpClient) { }


  public loginUser(login: any) {
    return this.http.post(AUTHOR_URL + '/login', login);
  }
  public createBook(book: any) {

    return this.http.post(AUTHOR_URL + '/' + book.authorId + '/books', book);
  }
  public bookDisplay(authorId: any) {
    return this.http.get(AUTHOR_URL + '/' + authorId + '/books/display');
  }
  public  editBook(editBook: any, authorId: any, id: any) {
   
    return this.http.put(AUTHOR_URL + '/' + authorId + '/books'+'/'+id, editBook);
  }
  
  public registerUser(register: any) {

    return this.http.post(AUTHOR_URL + '/signup' , register);
  }
}
