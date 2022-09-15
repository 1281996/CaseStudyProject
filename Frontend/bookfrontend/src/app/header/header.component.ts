import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { TokenService } from '../token.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {


  constructor(private route: Router, private tokenService: TokenService) {

  }

  ngOnInit(): void {

  }
  logout() {
    this.tokenService.signOut()
    this.route.navigate(['/login']);

  }
  isLoggedIn() {
    return this.tokenService.getIsLoggedIn()
  }
  getLoggedInUserName() {
    return this.tokenService.getUser().email;
  }
  getRole() {
    if (this.tokenService.getUser().roles != null) {
      console.log(this.tokenService.getUser().roles[0])
      return this.tokenService.getUser().roles[0]
    }
    else {
      return "NOTLoggedIn"
    }
  }
}
