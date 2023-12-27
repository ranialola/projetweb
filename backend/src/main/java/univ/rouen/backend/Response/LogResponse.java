package univ.rouen.backend.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LogResponse {
    private String token;
    String message;
    Boolean status;
}
