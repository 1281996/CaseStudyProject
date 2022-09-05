import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookService } from '../book.service';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private fb:FormBuilder,private bookService:BookService,private route:Router,private notifyService: NotificationService) { }
title:any="Books.com"
  ngOnInit(): void {
  }
  public frmRegister = this.fb.group({
    emailId: this.fb.control('', [Validators.required]),
    password: this.fb.control('', [Validators.required]),
    firstName:this.fb.control('',[Validators.required]),
    lastName:this.fb.control('',[Validators.required])
  })

  get emailId() {
    return this.frmRegister.get("emailId") as FormControl;
  }
  get password() {
    return this.frmRegister.get("password") as FormControl;
  }
  get firstName() {
    return this.frmRegister.get("firstName") as FormControl;
  }
  get lastName() {
    return this.frmRegister.get("lastName") as FormControl;
  }
  RegisterClick(register:any){
    console.log('Register clicked');
    const promise = this.bookService.registerUser(register);
     promise.subscribe((res: any) => {
       console.log(res);
       this.showToasterSuccess(res.response);
       this.route.navigate(['/login']);
     }, (error: any) => {
       this.showToasterError(error.response);
     });
     this.route.navigate(['/author']);
   }
   showToasterSuccess(msg:any) {
    console.log("showToasterSuccess");
    this.notifyService.showSuccess(msg, this.title)
  }
  showToasterError(msg:any) {
    console.log("showToasterError");
    this.notifyService.showError(msg,this.title)
  }
}
