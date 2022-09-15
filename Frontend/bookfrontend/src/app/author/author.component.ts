import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { BookService } from '../book.service';
import { TokenService } from '../token.service';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {
  flag: boolean = false;
  public editBooks: any;
  bookdata: any;
  display = "none";
  authId: any;
  displayEditBooks = "none";
  constructor(private fb: FormBuilder, private bookService: BookService,private tokenService:TokenService) { }

  ngOnInit(): void {
   this.booksDisplay()
  }

  openModal() {
    this.display = "block";
  }
  onCloseHandled() {
    this.display = "none";
  }


  public frmCreateBook = this.fb.group({
    publisher: this.fb.control('', [Validators.required]),
    title: this.fb.control('', [Validators.required]),
    category: this.fb.control('', [Validators.required]),
    image: this.fb.control('', [Validators.required]),
    price: this.fb.control('', [Validators.required]),
    content: this.fb.control('', [Validators.required])
  })


  get publisher() {
    return this.frmCreateBook.get("publisher") as FormControl;
  }
  get title() {
    return this.frmCreateBook.get("title") as FormControl;
  }
  get category() {
    return this.frmCreateBook.get("category") as FormControl;
  }
  get image() {
    return this.frmCreateBook.get("image") as FormControl;
  }
  get price() {
    return this.frmCreateBook.get("price") as FormControl;
  }
  get content() {
    return this.frmCreateBook.get("content") as FormControl;
  }
  createBookClick(book: any) {
    console.log('Create Book clicked');
    this.onCloseHandled()
    book.authorId = this.getAuthorId();
    const promise = this.bookService.createBook(book);
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookdata = res.bookDto;
    }, (error: any) => {
      console.log(error);
    });

  }
  booksDisplay() {
    console.log('Books Display');
    this.authId = this.getAuthorId();
    const promise = this.bookService.bookDisplay(this.authId);
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookdata = res;
    }, (error: any) => {
      console.log(error);
    });

  }


  editBook(editBook: any, status: any) {
    console.log(editBook)
    this.flag = confirm("Are u  sure")
    if (this.flag) {
      editBook.status = status;
      const promise = this.bookService.editBook(editBook, editBook.user.id, editBook.id);
      promise.subscribe((res: any) => {
        console.log(res);
        this.bookdata = res.bookDto;
      }, (error: any) => {
        console.log(error);
      });
    }
  }
  public getAuthorId() {
    return this.tokenService.getUser().id;
  }
  
}
