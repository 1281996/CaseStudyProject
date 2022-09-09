import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


const AUTHOR_URL = "http://localhost:8080/digitalbooks/author";
const READER_URL = "http://localhost:8080/digitalbooks/readers";
const BOOKS_URL = "http://localhost:8080/digitalbooks/books"
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
  public editBook(editBook: any, authorId: any, id: any) {

    return this.http.put(AUTHOR_URL + '/' + authorId + '/books' + '/' + id, editBook);
  }

  public registerUser(register: any) {

    return this.http.post(AUTHOR_URL + '/signup', register);
  }
  public getAllReadersBooks() {
    return this.http.get(READER_URL);
  }
  public getFilteredBooks(authorId: any, publisher: any, catageory: any, price: any) {
    return this.http.get(BOOKS_URL + '/search/' + authorId + '/' + publisher + '/' + catageory + '/' + price);
  }
  public getAuthorsRoleUsers() {
    return this.http.get(AUTHOR_URL + '/authorsRoles');
  }
  public getDistinctPublishers() {
    return this.http.get(BOOKS_URL + '/publishers');
  }
  public buyBook(buy: any) {
    return this.http.post(BOOKS_URL + '/buy', buy);
  }
  getAllPurchashsedBooks(emaildId: any) {
    return this.http.get(READER_URL +'/' +emaildId+'/books');
  }
}
