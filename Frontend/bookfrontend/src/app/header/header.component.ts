import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private route:Router) {console.log("hello1"); this.isLoggedIn=localStorage.getItem('currentUser');console.log(this.isLoggedIn)}
  isLoggedIn:any;
  ngOnInit(): void {
    console.log("hello")
    this.isLoggedIn=localStorage.getItem('currentUser');
    console.log(this.isLoggedIn)
  }
  logout(){
    console.log("logout")
    localStorage.removeItem('currentUser');
    this.route.navigate(['/login']);
  }
}
