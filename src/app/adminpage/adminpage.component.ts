import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adminpage',
  templateUrl: './adminpage.component.html',
  styleUrls: ['./adminpage.component.css']
})
export class AdminpageComponent implements OnInit {
  userEmail: string | null;
  members: any[] = [];

  constructor(private loginService: LoginService,private router: Router) {
    // Récupérez l'e-mail du stockage local
    this.userEmail = localStorage.getItem('user_email');
  }

  ngOnInit(): void {
    // Récupérer le token de l'utilisateur depuis le service LoginService
    const token = this.loginService.getToken();

    if (!token) {
      console.error('Token non trouvé dans le service LoginService');
      return;
    }

    // Appeler le service pour récupérer la liste des membres
    this.loginService.getAllMembers(token).subscribe(
      (data) => {
        console.log('Données récupérées avec succès : ', data);
        // Attribuez les données à la propriété members de votre composant
        this.members = data;
      },
      (error) => {
        console.error('Erreur lors de la récupération des membres : ', error);
      }
    );
  }
  logout(): void {
    // Effacer le token du stockage local
    localStorage.removeItem('access_token');
    
    // Rediriger vers la page de connexion
    this.router.navigate(['/login']);
  }
}
