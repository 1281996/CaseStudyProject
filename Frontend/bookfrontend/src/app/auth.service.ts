import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

const URL = 'https://3b5kvbz4ci.execute-api.us-east-1.amazonaws.com/prod/author/';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }
  public registerUser(register: any) {

    return this.http.post(URL+'signup', register);
  }
  public loginUser(login: any) {
    return this.http.post(URL+'login', login);
  }
}
