package univ.rouen.backend.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LoginDTO {
    private String mail;
    private String mdp;
}
