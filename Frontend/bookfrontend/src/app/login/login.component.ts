import { Component, OnInit } from '@angular/core';
import { BookService } from '../book.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(public bookService: BookService) { }

  ngOnInit(): void {
  }
  LoginClick(login:any){
    console.log('Login clicked');
    const promise = this.bookService.loginUser(login);
    promise.subscribe((res: any) => {
      console.log(res);
      
    }, (error:any) => {
      console.log(error);
    });
  }
}
