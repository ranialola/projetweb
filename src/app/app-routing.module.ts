import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdminpageComponent } from './adminpage/adminpage.component';
import { AuthGuard } from './auth.guard';
import { UpdateMemberComponent } from './update-member/update-member.component';
import { NewMemberComponent } from './new-member/new-member.component';

const routes: Routes = [
  {path:"login",component:LoginComponent},
  { path: 'adminPage', component: AdminpageComponent },
  { path: 'updatemember/:id', component:UpdateMemberComponent },
  { path: 'newMember', component: NewMemberComponent },
  {path:"",component:LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }