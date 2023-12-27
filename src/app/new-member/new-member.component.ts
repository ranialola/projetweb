import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-new-member',
  templateUrl: './new-member.component.html',
  styleUrls: ['./new-member.component.css']
})
export class NewMemberComponent implements OnInit {
  newMemberForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {
    this.newMemberForm = this.formBuilder.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      mail: ['', [Validators.required, Validators.email]],
      mdp: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    // Logique d'initialisation si nécessaire
  }

  onSubmit(): void {
    if (this.newMemberForm.valid) {
      const newMemberData = this.newMemberForm.value;

      // Appelez le service pour ajouter un nouveau membre
      this.loginService.addNewMember(newMemberData).subscribe(
        (response) => {
          console.log('Membre ajouté avec succès : ', response);
          // Redirigez ou effectuez d'autres actions après l'ajout réussi
          this.router.navigate(['/adminPage']);
        },
        (error) => {
          console.error('Erreur lors de l\'ajout du membre : ', error);
          // Gérez les erreurs ici
        }
      );
    }
  }
}

