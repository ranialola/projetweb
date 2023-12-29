package univ.rouen.backend.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LogResponsemember {
    private String token;
    private String message;
    private Boolean status;
    private String role;
}
