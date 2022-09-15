import { Injectable } from '@angular/core';


const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const LOGGEDIN_KEY = 'auth-flag';
@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }
  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }
  public saveIsLoggedIn(isLoggedIn:any){
    window.sessionStorage.setItem(LOGGEDIN_KEY, isLoggedIn);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }
public getIsLoggedIn(){
  return window.sessionStorage.getItem(LOGGEDIN_KEY);
}
  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }
  reloadPage(): void {
    window.location.reload();
  }
  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  }
}
