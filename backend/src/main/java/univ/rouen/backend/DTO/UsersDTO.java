package univ.rouen.backend.DTO;

import univ.rouen.backend.DAO.Role;
import univ.rouen.backend.DAO.userRole;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UsersDTO {
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    private userRole role;
}
