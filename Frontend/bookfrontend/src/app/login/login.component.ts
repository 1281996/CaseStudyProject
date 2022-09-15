import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { AuthService } from '../auth.service';
import { BookService } from '../book.service';
import { NotificationService } from '../notification.service';
import { TokenService } from '../token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],

})
export class LoginComponent implements OnInit {
  title: any = "Books.com"
  public isLoggedIn: any = false;
  roles: any=[];
  constructor(public authService: AuthService,public tokenService:TokenService,private fb: FormBuilder, private route: Router, private notifyService: NotificationService) { }

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenService.getUser().roles;
    }
  }

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
  loginClick(login: any) {
    console.log('Login clicked');
    const promise = this.authService.loginUser(login);
    promise.subscribe((res: any) => {
     
      //  localStorage.setItem('currentUser', this.isLoggedIn);
       // localStorage.setItem('emailId', login.emailId);
       // localStorage.setItem('id', res.id);
       // console.log(localStorage.getItem('currentUser'))

       this.tokenService.saveToken(res.token);
       this.tokenService.saveUser(res);
       this.tokenService.saveIsLoggedIn(true);

    
       this.isLoggedIn = true;
       this.roles = this.tokenService.getUser().roles;
      

        this.route.navigate(['/author']);
      
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
