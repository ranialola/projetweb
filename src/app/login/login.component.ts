import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private authService: LoginService, private formBuilder: FormBuilder,private router : Router) {
    this.loginForm = this.formBuilder.group({
      mail: ['', [Validators.required, Validators.email]],
      mdp: ['', Validators.required]
    });
  }

  get mail() {
    return this.loginForm.get('mail');
  }

  get mdp() {
    return this.loginForm.get('mdp');
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const credentials = {
        mail: this.mail?.value,
        mdp: this.mdp?.value
      };

      this.authService.login(credentials).subscribe(
        response => {
          // Stocker le token dans le stockage local
          const token = response.token;
          localStorage.setItem('access_token', token);
          // Stocker  l'email dans le stockage local 
          localStorage.setItem('user_email', credentials.mail);
          
          this.router.navigateByUrl('/adminPage');
        },
        error => {
          // Gérer les erreurs
          console.error('Une erreur s\'est produite lors de la connexion :', error);
        }
      );
    }
  }
}
