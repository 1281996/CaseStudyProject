import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookService } from '../book.service';

import * as fileSaver from 'file-saver'; 

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  payments:any=[];
  paymentId:any;
  bookData: any;
  payment={
    id:1
  }
  constructor(private route: Router,private bookService:BookService) { }

  ngOnInit(): void {
    const promise = this.bookService.getAllPurchashsedBooks(this.getEmailIdFromLocalStorage());
    promise.subscribe((res: any) => {
      console.log(res);
      this.payments = res;
    }, (error: any) => {
      console.log(error);
    });
  }
  backToHome() {
    this.route.navigate(['/'])
  }
  getEmailIdFromLocalStorage(){
   return localStorage.getItem('emailId');
  }
  readContent(payment:any){
    const promise = this.bookService.readContent(payment.email,payment.book.id);
    promise.subscribe((res:any) =>this.saveFile(res) , (error: any) => {
      console.log(error);
    });
  }
  saveFile(data: any) {
    const blob = new Blob([data], {type: 'application/pdf; charset=utf-8'});
    fileSaver.saveAs(blob, 'filename');
  }
  refund(payment:any){
   if(confirm('are u sure?')){
    const promise = this.bookService.refund(payment.email,payment.book.id);
    promise.subscribe((res:any) =>{
      this.payments = res;
    } , (error: any) => {
      console.log(error);
    });
  }
  }
  searchByPaymentId(paymentId:any){
    const promise = this.bookService.searchByPaymentId(this.getEmailIdFromLocalStorage(),paymentId,this.payment);
    promise.subscribe((res:any) =>{
      this.bookData = res;
    } , (error: any) => {
      console.log(error);
    });
  }
}

