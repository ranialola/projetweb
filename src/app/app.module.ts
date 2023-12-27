import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AdminpageComponent } from './adminpage/adminpage.component';
import { UpdateMemberComponent } from './update-member/update-member.component';
import { NewMemberComponent } from './new-member/new-member.component'; 

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminpageComponent,
    UpdateMemberComponent,
    NewMemberComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
