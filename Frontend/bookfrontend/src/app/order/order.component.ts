import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookService } from '../book.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  payments:any=[];
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
}
