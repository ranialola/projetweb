package univ.rouen.backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import univ.rouen.backend.DTO.LoginDTO;
import univ.rouen.backend.DTO.UsersDTO;
import univ.rouen.backend.Response.LogResponse;
import univ.rouen.backend.Service.UsersService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UsersService userservice;

    @PostMapping("/login/saveLogin")
    public String saveUsers(@RequestBody UsersDTO usersdto) {
        String id = userservice.addUsers(usersdto);
        return id;
    }
    @PostMapping("/LogIn")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){
        //LogResponse LoginResponse=userservice.loginInUser(loginDTO);
        //return ResponseEntity.ok(LoginResponse);
        LogResponse authToken = userservice.loginInUser(loginDTO);
        return ResponseEntity.ok(authToken);
    }
    @PostMapping("/admin/addUser")
    public ResponseEntity<String> addUserByAdmin(@RequestBody UsersDTO usersDTO, @RequestHeader("Authorization") String token) {
        String response = userservice.addUsersByAdmin(usersDTO, token);
        if (response.startsWith("Utilisateur ajouté avec succès")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @DeleteMapping("/admin/deleteUser/{id}")
    public ResponseEntity<String> deleteUserByAdmin(@PathVariable int id, @RequestHeader("Authorization") String token) {
        String response = userservice.deleteUserByAdmin(id, token);
        if (response.startsWith("Utilisateur supprimé avec succès")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UsersDTO usersDTO) {
        String response = userservice.updateUser(id, usersDTO);
        if (response.startsWith("Informations mises à jour avec succès")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        String response = userservice.deleteUserById(id);
        if (response.startsWith("Utilisateur supprimé avec succès")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/members")
    public ResponseEntity<List<UsersDTO>> getAllMembers() {
        List<UsersDTO> members = userservice.getAllMembers();
        
        return ResponseEntity.ok(members);
    }
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UsersDTO> getUser(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (userservice.isAdmin(token)) {
            Optional<UsersDTO> user = userservice.getUserId(id);

            return user.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    
}

   


    
    
   
   
    


