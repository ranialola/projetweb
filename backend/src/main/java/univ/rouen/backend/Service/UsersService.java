package univ.rouen.backend.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import univ.rouen.backend.DAO.Role;
import univ.rouen.backend.DAO.Token;
import univ.rouen.backend.DAO.concept;
import univ.rouen.backend.DAO.traduction;
import univ.rouen.backend.DAO.user;
import univ.rouen.backend.DAO.userRole;
import univ.rouen.backend.DTO.LoginDTO;
import univ.rouen.backend.DTO.UsersDTO;
import univ.rouen.backend.Repository.RoleRepository;
import univ.rouen.backend.Repository.UserLoginRep;
import univ.rouen.backend.Response.LogResponse;


@Service
public class UsersService {

    @Autowired
    private UserLoginRep userlogin; 

    @Autowired
    private RoleRepository roleRepository;

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); 
    private final long validityInMilliseconds = 3600000; // 1 hour

    public String addUsers(UsersDTO usersdto) {
        try {
            System.out.println("Nom: " + usersdto.getNom());
            System.out.println("Prenom: " + usersdto.getPrenom());
            System.out.println("Mail: " + usersdto.getMail());
            System.out.println("Mdp: " + usersdto.getMdp());
            System.out.println("Role: " + usersdto.getRole());
            Optional<Role> optionalRole = roleRepository.findByName(userRole.Role_membre);
    
        if (optionalRole.isEmpty()) {
            // this means the role table is empty IMPOSSIBLE
            return null;
        }
    
            user users = new user();
            users.setNom(usersdto.getNom());
            users.setPrenom(usersdto.getPrenom());
            users.setMail(usersdto.getMail());
            users.setMdp(usersdto.getMdp());
            users.setRole(optionalRole.get());
            users.setConcepts(null);
            users.setTraductions(null);
    
            userlogin.save(users);
            System.out.println("Utilisateur ajouté avec succès: " + users.getNom());
            return users.getNom();
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    

    public LogResponse loginInUser(LoginDTO loginDTO) {
        user user1 = userlogin.findBymail(loginDTO.getMail());
    
        if (user1 != null) {
            String password = loginDTO.getMdp();
            String passwd = user1.getMdp();
    
            if (password.equals(passwd)) {
                Token token = generateToken(user1);
                Optional<user> user = userlogin.findOneBymailAndMdp(loginDTO.getMail(), loginDTO.getMdp());
    
                if (user.isPresent()) {
                    return new LogResponse(token.getToken(), "login success", true);
                } else {
                    return new LogResponse(null, "login failed", false);
                }
            } else {
                return new LogResponse(null, "password not match", false);
            }
        } else {
            return new LogResponse(null, "Email not exists", false);
        }
    }


    private Token generateToken(user user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String tokenValue = Jwts.builder()
                .setSubject(user.getMail())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();

        return new Token(tokenValue);
    }
    public boolean isAdmin(String token) {
        String loggedInUserMail = extractUserMailFromToken(token);
        user loggedInUser = userlogin.findBymail(loggedInUserMail);
        return loggedInUser != null && loggedInUser.isAdmin();
    }
    
    private String extractUserMailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            
            // Ajoutez ces lignes pour vérifier le contenu du token et des revendications
            System.out.println("Token content: " + token);
            System.out.println("Claims: " + claims);
            
            return claims.getSubject();
        } catch (Exception e) {
            // Gérer les erreurs lors de l'extraction du token
            return null;
        }
    }
    

    public String addUsersByAdmin(UsersDTO usersdto, String token) {
        try {
            // Ajoutez ce log pour vérifier le contenu de usersdto
            System.out.println("UsersDTO content: " + usersdto);

    
            if (isAdmin(token)) {
                Optional<Role> optionalRole = roleRepository.findByName(userRole.Role_membre);
                if (optionalRole.isEmpty()) {
                    return null;
                }
                user user = new user();
                user.setNom(usersdto.getNom());
                user.setPrenom(usersdto.getPrenom());
                user.setMail(usersdto.getMail());
                user.setMdp(usersdto.getMdp());
                user.setRole(optionalRole.get());
                user.setConcepts(null);
                user.setTraductions(null);
                userlogin.save(user);
                return "Utilisateur ajouté avec succès";
            } else {
                return "Vous n'avez pas les autorisations nécessaires pour effectuer cette action";
            }
        } catch (Exception e) {
            // Ajoutez ce log pour afficher les détails de l'exception
            e.printStackTrace();
            return "Erreur lors de l'ajout de l'utilisateur: " + e.getMessage();
        }
    }

    public String deleteUserByAdmin(int id, String token) {
        try {
            if (isAdmin(token)) {
                Optional<user> userToDelete = userlogin.findById(id);

                if (userToDelete.isPresent() && userToDelete.get().getMail() != null) {
                    // 1. Récupérez le rôle avant de supprimer l'utilisateur
                    Role userRole = userToDelete.get().getRole();

                    // 2. Supprimez tous les utilisateurs avec ce rôle
                   // userlogin.deleteByRoleId(userRole.getId());

                    // 3. Supprimez le rôle une fois que les utilisateurs associés ont été supprimés
                    roleRepository.deleteById(userRole.getId());

                    return "Utilisateur supprimé avec succès";
                } else {
                    return "Utilisateur introuvable ou e-mail null";
                }
            } else {
                return "Vous n'avez pas les autorisations nécessaires pour effectuer cette action";
            }
        } catch (Exception e) {
            return "Erreur lors de la suppression de l'utilisateur: " + e.getMessage();
        }
    }
    public String updateUser(int id, UsersDTO usersdto) {
        try {
            // Récupérer l'utilisateur existant
            Optional<user> optionalUser = userlogin.findById(id);
    
            if (optionalUser.isPresent()) {
                user existingUser = optionalUser.get();
    
                // Vérifier si l'utilisateur est membre
                if (!existingUser.isAdmin()) {
                    // Mettre à jour les informations
                    existingUser.setNom(usersdto.getNom());
                    existingUser.setPrenom(usersdto.getPrenom());
                    existingUser.setMail(usersdto.getMail());
                    existingUser.setMdp(usersdto.getMdp());
    
                    // Enregistrer les modifications
                    userlogin.save(existingUser);
    
                    return "Informations mises à jour avec succès";
                } else {
                    return "Vous n'avez pas les autorisations nécessaires pour mettre à jour les informations en tant que membre";
                }
            } else {
                return "Utilisateur introuvable";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la mise à jour des informations : " + e.getMessage();
        }
    }
    public String deleteUserById(int id) {
        try {
            userlogin.deleteUserById(id);
            return "Utilisateur supprimé avec succès";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la suppression de l'utilisateur : " + e.getMessage();
        }
    }
    public List<UsersDTO> getAllMembers() {
        List<user> members = userlogin.findAll();
        return members.stream()
            .filter(member -> !member.isAdmin()) 
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private UsersDTO convertToDTO(user member) {
        return new UsersDTO(
                member.getId(),
                member.getNom(),
                member.getPrenom(),
                member.getMail(),
                member.getMdp(),
                member.getRole().getName()
        );
    }
    public Optional<UsersDTO> getUserId(int id) {
        Optional<user> optionalUser = userlogin.findById(id);
        
        return optionalUser.map(this::convertToDTO);
    }
    
   
}


    
    
    
    
    
    

    
    
    
    
    
    
    

