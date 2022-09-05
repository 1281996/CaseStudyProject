import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { BookService } from '../book.service';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  constructor(private fb: FormBuilder, private bookService: BookService) { }

  ngOnInit(): void {
  }
  public editBooks: any;
  bookdata: any;
  display = "none";
  displayBooks = "none";
  displayEditBooks = "none";
  openModal() {
    this.display = "block";
  }
  onCloseHandled() {
    this.display = "none";
  }

  onBooksCloseHandled() {
    this.displayBooks = "none";
  }
  openBooksModal() {
    this.displayBooks = "block";
  }
  public frmCreateBook = this.fb.group({
    authorId: this.fb.control('', [Validators.required]),
    publisher: this.fb.control('', [Validators.required]),
    title: this.fb.control('', [Validators.required]),
    category: this.fb.control('', [Validators.required]),
    image: this.fb.control('', [Validators.required]),
    price: this.fb.control('', [Validators.required]),
    content: this.fb.control('', [Validators.required])
  })

  get authorId() {
    return this.frmCreateBook.get("authorId") as FormControl;
  }
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
  CreateBookClick(book: any) {
    console.log('Create Book clicked');
    this.onCloseHandled()
    const promise = this.bookService.createBook(book);
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookdata = res.bookDto;
    }, (error: any) => {
      console.log(error);
    });

  }
  BooksDisplay(author: any) {
    console.log('Books Display');
    this.onBooksCloseHandled();
    const promise = this.bookService.bookDisplay(author.authorId);
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookdata = res;
    }, (error: any) => {
      console.log(error);
    });

  }


  EditBook(editBook: any) {
    console.log(editBook)
    const promise = this.bookService.editBook(editBook,editBook.user.id,editBook.id);
    promise.subscribe((res: any) => {
      console.log(res);
      this.bookdata =  res.bookDto;
    }, (error: any) => {
      console.log(error);
    });

  }

}
