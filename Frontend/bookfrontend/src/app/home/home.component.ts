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
  catagories: any = ['FICTIONAL', 'HORROR', 'HISTORY'];
  users: any;
  publishers: any;
  bookId: any;
  display = "none";
  displayBuy = "none";
  displayOrder = "none";
  displayCard="none";
  term = '';
  price: any;
  bookCategory: any;
  bookPublisher: any;
  bookAuthor: any;
  addToCart:any=[]; 
  cartFlag=true; 
  userInfo = {
    name: "",
    email: "",
    cardNumber: "",
    cvc: "",
    books:"",
    amount:0
  }
  addtoCartFilter: any=[];
  cartTotal: number=0;

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
  addBookToCart(bookData: any) {
    console.log(bookData);
    console.log(this.addToCart.length)
   
    //
    if(this.addToCart.length!=0){
      console.log("if")
    let index = this.addToCart.indexOf(bookData);
    if(index<0){
      console.log("if")
      console.log(index)
      this.addToCart.push(bookData);
    }
    }
   //
   if(this.addToCart.length==0){
    console.log("2nd if")
    this.addToCart.push(bookData);
  }
    
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
  openModelCard(){
    this.displayCard= "block";
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
  procedeToBuy(bookId: any, userInfo: any) {
    console.log(bookId)
    this.closeModalBuy();
    userInfo.bookId = bookId;
    this.userInfo.books=this.addToCart;
    this.userInfo.amount=this.cartTotal;
    console.log(userInfo);
    const promise = this.bookService.buyBook(this.userInfo)
    promise.subscribe((res: any) => {
      if (!localStorage.getItem('currentUser')) {
        localStorage.setItem('emailId', userInfo.email);
        localStorage.setItem('currentUser', 'true');
      }
      this.showToasterSuccess(res.response)
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
  showCart(){
    console.log('showCart')
     this.cartTotal=0;
    this.addToCart.filter((book:any)=>{
      this.cartTotal= book.price+this.cartTotal;
    })
    this.cartFlag=false; 
  }
  removeFromCart(id:any){
    this.addToCart = this.addToCart.filter((book:any) => book.id !== id)

  }
  saveCardDetails(cardInfo:any){
    this.closeModalCard();               
    const promise = this.bookService.saveCardDetails(cardInfo);
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookData = res;
    }, (error: any) => {
      console.log(error);
    });
  }
}
