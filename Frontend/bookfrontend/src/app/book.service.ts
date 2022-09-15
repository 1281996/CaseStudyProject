import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


const AUTHOR_URL = "http://localhost:8080/digitalbooks/author";
const READER_URL = "http://localhost:8080/digitalbooks/readers";
const BOOKS_URL = "http://localhost:8080/digitalbooks/books"

const CARD_URL="http://localhost:8081/card"
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
  readContent(emailId: any, bookId: any) {
    return this.http.get(READER_URL +'/' +emailId+'/books/'+bookId, {
      responseType: 'blob'
    });
  }
  refund(emailId: any, bookId: any) {
    return this.http.get(READER_URL +'/' +emailId+'/books/'+bookId+'/refund');
  }
  searchByPaymentId(emailId: any, paymentId: any,payment:any) {
    console.log(emailId)
    console.log(paymentId)
    return this.http.post(READER_URL +'/' +emailId+'/books'+'/'+paymentId,payment);
  }
  saveCardDetails(cardInfo: any) {
    return this.http.post(CARD_URL ,cardInfo);
  }
  
}
