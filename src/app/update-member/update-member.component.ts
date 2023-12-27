import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-member',
  templateUrl: './update-member.component.html',
  styleUrls: ['./update-member.component.css']
})
export class UpdateMemberComponent implements OnInit {
  memberId: number;
  memberInfoForm: FormGroup;

  constructor(
    private loginService: LoginService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.memberId = this.route.snapshot.params['id'];
    this.memberInfoForm = this.formBuilder.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      mail: ['', [Validators.required, Validators.email]],
      mdp: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loginService.getMemberById(this.memberId).subscribe(
      (data) => {
        // Utilisez setValue au lieu de patchValue pour remplir le formulaire avec des valeurs complètes
        this.memberInfoForm.setValue({
          nom: data.nom || '',
          prenom: data.prenom || '',
          mail: data.mail || '',
          mdp: data.mdp || ''
        });
      },
      (error) => {
        console.error('Erreur lors de la récupération des informations du membre : ', error);
      }
    );
  }
  

  onSubmit(): void {
    if (this.memberInfoForm.valid) {
      const updatedData = this.memberInfoForm.value;

      this.loginService.updateMember(this.memberId, updatedData).subscribe(
        (response) => {
          console.log('Mise à jour réussie : ', response);
          this.router.navigate(['/adminPage']);
        },
        (error) => {
          console.error('Erreur lors de la mise à jour : ', error);

          if (typeof error.error === 'string') {
            console.log('Réponse du serveur en tant que chaîne de texte : ', error.error);
          } else {
            console.log('Corps de la réponse en cas d\'erreur : ', error.error);
          }
        }
      );
    }
  }
}
