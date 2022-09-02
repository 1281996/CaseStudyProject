import { Injectable } from '@angular/core';
import { HttpClient ,HttpHeaders} from '@angular/common/http';
import { map } from 'rxjs/operators';

const AUTHOR_URL = "http://localhost:8080/api/v1/digitalbooks/author";
@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpClient) { }
  
  
  public loginUser(login: any) {
    return this.http.post(AUTHOR_URL+'/login', login).pipe(
      map(
        userData => {
       

         
         return null;
        }
      )

     );
  }

}
