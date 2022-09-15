import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
const AUTHOR_URL = "http://localhost:8080/digitalbooks/author";
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }
  public registerUser(register: any) {

    return this.http.post(AUTHOR_URL + '/signup', register);
  }
  public loginUser(login: any) {
    return this.http.post(AUTHOR_URL + '/login', login);
  }
}
