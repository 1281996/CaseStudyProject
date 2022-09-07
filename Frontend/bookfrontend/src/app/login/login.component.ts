import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { BookService } from '../book.service';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],

})
export class LoginComponent implements OnInit {

  constructor(public bookService: BookService, private fb: FormBuilder, private route: Router, private notifyService: NotificationService) { }

  ngOnInit(): void {
  }
  title: any = "Books.com"
  public isLoggedIn: any = false;

  public frmLogin = this.fb.group({
    emailId: this.fb.control('', [Validators.required, Validators.minLength(4)]),
    password: this.fb.control('', [Validators.required])
  })

  get emailId() {
    return this.frmLogin.get("emailId") as FormControl;
  }
  get password() {
    return this.frmLogin.get("password") as FormControl;
  }



  LoginClick(login: any) {
    console.log('Login clicked');
    const promise = this.bookService.loginUser(login);
    promise.subscribe((res: any) => {
      console.log(res);
      if (res.flag === true) {
        this.isLoggedIn = res.flag;
        localStorage.setItem('currentUser', this.isLoggedIn);
        console.log(localStorage.getItem('currentUser'))
        
        
        this.route.navigate(['/author']);
      }
      this.showToasterSuccess(res.response);
    }, (error: any) => {
      console.log(error);
      this.showToasterError(error.response);
    });

  }
  showToasterSuccess(msg: any) {
    console.log("showToasterSuccess");
    this.notifyService.showSuccess(msg, "Books.com")
  }
  showToasterError(msg: any) {
    console.log("showToasterError");
    this.notifyService.showError(msg, this.title)
  }
}
