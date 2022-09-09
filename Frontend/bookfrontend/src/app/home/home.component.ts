import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookService } from '../book.service';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  bookData: any;
  catagories: any = ['FICTIONAL', 'HORROR', 'HISTORY', 'FANTACY'];
  users: any;
  publishers: any;
  bookId: any;
  display = "none";
  displayBuy = "none";
  displayOrder = "none";
  term = '';
  price: any;
  bookCategory: any;
  bookPublisher: any;
  bookAuthor: any;

  userInfo = {
    name: "",
    email: "",
    cardNumber: "",
    cvc: ""
  }

  constructor(private fb: FormBuilder, private bookService: BookService, private notifyService: NotificationService, private route: Router) { }

  ngOnInit(): void {
    const promise = this.bookService.getAllReadersBooks();
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookData = res;
    }, (error: any) => {
      console.log(error);
    });
    //get authors list
    const promiseAuthorUsers = this.bookService.getAuthorsRoleUsers();
    promiseAuthorUsers.subscribe((res: any) => {
      console.log(res);
      this.users = res;
    }, (error: any) => {
      console.log(error);
    });
    //get publishers list
    const promisePublishers = this.bookService.getDistinctPublishers();
    promisePublishers.subscribe((res: any) => {
      console.log(res);
      this.publishers = res;
    }, (error: any) => {
      console.log(error);
    });
  }

  public frmLogin = this.fb.group({
    emailId: this.fb.control('', [Validators.required, Validators.minLength(4)]),

  })

  get emailId() {
    return this.frmLogin.get("emailId") as FormControl;
  }
  myBooksClick(emailId: any) {
    console.log(emailId);

  }
  buy(book: any) {
    this.openModalBuy();
    this.bookId = book.id;

  }
  filteredClick(catagory: any) {
    console.log('filteredClick')
  }
  openModal() {
    this.display = "block";
  }
  closeModal() {
    this.display = "none";
  }
  openModalBuy() {
    this.displayBuy = "block";
  }
  closeModalBuy() {
    this.displayBuy = "none";
  }
  openModalOrder() {
    this.displayOrder = "block";
  }
  closeModalOrder() {
    this.displayOrder = "none";
  }

  saveFilters() {
    const promise = this.bookService.getFilteredBooks(this.bookCategory, this.bookAuthor, this.price, this.bookPublisher)
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookData = res;
    }, (error: any) => {
      console.log(error);
    });
  }

  onCheckCatageory(event: any, category: any) {

    console.log(category)
    console.log(event.checked)
    this.bookCategory = category
  }
  onCheckPublishers(event: any, publisher: any) {

    console.log(publisher)
    console.log(event.checked)
    this.bookPublisher = publisher;
  }
  onCheckAuthors(event: any, user: any) {

    console.log(user)
    console.log(event.checked)
    this.bookAuthor = user;
  }
  procedeToBuy(bookId: any, userInfo: any) {
    console.log(bookId)
    this.closeModalBuy();
    userInfo.bookId = bookId;
    console.log(userInfo);
    const promise = this.bookService.buyBook(this.userInfo)
    promise.subscribe((res: any) => {
      if (!localStorage.getItem('currentUser')) {
        localStorage.setItem('emailId', userInfo.email);
        localStorage.setItem('currentUser', 'true');
      }
      this.showToasterSuccess('sucess')
    }, (error: any) => {
      console.log(error);
    });
  }
  showToasterSuccess(msg: any) {
    console.log("showToasterSuccess");
    this.notifyService.showSuccess(msg, "Books.com")
  }
  routeToOrder(userInfo: any) {
    localStorage.setItem('emailId', userInfo.email);
    localStorage.setItem('currentUser', 'true');
    this.route.navigate(["/order"])
  }
  openEmailPopUp() {
    if (!localStorage.getItem('currentUser')) {
      this.openModalOrder()
    }
    else {
      this.route.navigate(["/order"])
    }
  }
}
