package univ.rouen.backend.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Component {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String componentName;
private double componentX;
private double componentY;

}