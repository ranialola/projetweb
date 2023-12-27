package univ.rouen.backend.DAO;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class traduction implements  Serializable{
 @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_Traduction;
    private String texte;
    private Langue langue;
    @ManyToOne
    private user users;
    @ManyToOne
    private illustration illustrations;
}
