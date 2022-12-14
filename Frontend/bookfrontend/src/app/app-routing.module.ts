import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthorComponent } from './author/author.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { OrderComponent } from './order/order.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [{ path: "login", component: LoginComponent }, { path: "register", component: RegisterComponent }
  , { path: '', component: HomeComponent }, { path: "author", component: AuthorComponent }, { path: "header", component: HeaderComponent },{path:"order",component:OrderComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
