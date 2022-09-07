import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { BookService } from '../book.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  bookData: any;

  constructor(private fb:FormBuilder,private bookService:BookService ) { }

  ngOnInit(): void {
    const promise = this.bookService.getAllReadersBooks();
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookData = res;
    }, (error: any) => {
      console.log(error);
    });
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
  Buy(book:any){

  }
}
