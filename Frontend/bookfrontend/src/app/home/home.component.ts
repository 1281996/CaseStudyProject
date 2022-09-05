import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private fb:FormBuilder) { }

  ngOnInit(): void {
  }
  display = "none";
 
openModal() {
    this.display = "block";
  }
  onCloseHandled() {
    this.display = "none";
  }
  public frmLogin = this.fb.group({
    emailId: this.fb.control('', [Validators.required, Validators.minLength(4)]),
   
  })

  get emailId() {
    return this.frmLogin.get("emailId") as FormControl;
  }
  MyBooksClick(emailId:any){
    console.log(emailId);
    this.onCloseHandled() 
  }
  
}
