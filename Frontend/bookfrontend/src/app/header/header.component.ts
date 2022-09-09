import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private route: Router) {
    console.log("hello1");
  }

  ngOnInit(): void {

  }
  logout() {
    console.log("logout")
    localStorage.removeItem('currentUser');
    localStorage.removeItem('emailId');
    localStorage.removeItem('id');
    this.route.navigate(['/login']);
  }
  isLoggedIn() {
    return localStorage.getItem('currentUser');
  }
  getLoggedInUserName() {
    return localStorage.getItem('emailId');
  }
}
