import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookService } from '../book.service';
import { NotificationService } from '../notification.service';
import { TokenService } from '../token.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  bookData: any;
  catagories: any = ['FICTIONAL', 'HORROR', 'HISTORY'];
  users: any;
  publishers: any;
  bookId: any;
  display = "none";
  displayBuy = "none";
  displayOrder = "none";
  displayCard = "none";
  term = '';
  price: any;
  bookCategory: any;
  bookPublisher: any;
  bookAuthor: any;
  addToCart: any = [];
  cartFlag = true;
  userInfo = {
    name: "",
    email: "",
    cardNumber: "",
    cvc: "",
    books: "",
    amount: 0
  }
  addtoCartFilter: any = [];
  cartTotal: number = 0;

  user = {
    email: null,
    id: null,
    roles: ["ROLE_GUEST"]
  }

  constructor(private fb: FormBuilder, private bookService: BookService, private notifyService: NotificationService, private route: Router, private tokenService: TokenService) { }

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

  public frmEmail = this.fb.group({
    email: this.fb.control('', [Validators.required]),
    cardNumber: this.fb.control('', [Validators.required]),
    cvc: this.fb.control('', [Validators.required]),
    name: this.fb.control('', [Validators.required])
  })


  get email() {
    return this.frmEmail.get("email") as FormControl;
  }
  get cardNumber() {
    return this.frmEmail.get("cardNumber") as FormControl;
  }
  get cvc() {
    return this.frmEmail.get("cvc") as FormControl;
  }
  get name() {
    return this.frmEmail.get("name") as FormControl;
  }

  addBookToCart(bookData: any) {
    console.log(bookData);
    console.log(this.addToCart.length)

    //
    if (this.addToCart.length != 0) {
      console.log("if")
      let index = this.addToCart.indexOf(bookData);
      if (index < 0) {
        console.log("if")
        console.log(index)
        this.addToCart.push(bookData);
      }
    }
    //
    if (this.addToCart.length == 0) {
      console.log("2nd if")
      this.addToCart.push(bookData);
    }

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
  openModelCard() {
    this.displayCard = "block";
  }
  closeModalCard() {
    this.displayCard = "none";
  }
  saveFilters() {
    this.closeModal();
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
  procedeToBuy(buy: any) {
    console.log(buy)
    this.closeModalBuy();
    this.userInfo.books = this.addToCart;
    this.userInfo.amount = this.cartTotal;
    this.userInfo.cardNumber = buy.cardNumber;
    this.userInfo.cvc = buy.cvc;
    this.userInfo.name = buy.name;
    this.userInfo.email = buy.email;
    console.log(buy);
    const promise = this.bookService.buyBook(this.userInfo)
    promise.subscribe((res: any) => {
      if (!this.tokenService.getIsLoggedIn()) {
        this.user.email = buy.email;
        this.saveReaderEmailInLocalStorage(this.user);
      }
      this.addToCart = [];
      this.showToasterSuccess(res.response)
      this.route.navigate(["/order"])
    }, (error: any) => {
      console.log(error);
    });
  }
  showToasterSuccess(msg: any) {
    console.log("showToasterSuccess");
    this.notifyService.showSuccess(msg, "Books.com")
  }
  routeToOrder(userInfo: any) {

    console.log(userInfo)
    this.user.email = userInfo.email;
    this.saveReaderEmailInLocalStorage(this.user);
    this.route.navigate(["/order"])
  }
  openEmailPopUp() {
    if (!this.tokenService.getIsLoggedIn()) {
      this.openModalOrder()
    }
    else {
      this.route.navigate(["/order"])
    }
  }
  showCart() {
    console.log('showCart')
    this.cartTotal = 0;
    this.addToCart.filter((book: any) => {
      this.cartTotal = book.price + this.cartTotal;
    })
    this.cartFlag = false;
  }
  removeFromCart(id: any) {
    this.addToCart = this.addToCart.filter((book: any) => book.id !== id)

  }
  saveCardDetails(cardInfo: any) {
    this.closeModalCard();
    const promise = this.bookService.saveCardDetails(cardInfo);
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookData = res;
    }, (error: any) => {
      console.log(error);
    });
  }
  saveReaderEmailInLocalStorage(user: any) {
    this.tokenService.saveIsLoggedIn(true);
    this.tokenService.saveUser(user);
  }
}
