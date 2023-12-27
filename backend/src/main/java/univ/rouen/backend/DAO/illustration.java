package univ.rouen.backend.DAO;



import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class illustration implements Serializable{
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_Illustration;
    private String titre;
    private String cheminFichier;
    private Langue langueParDefaut;
    @ManyToOne
    private concept concepts;
    @OneToMany(mappedBy ="illustrations" )
    private Collection<traduction> traductions;
    @OneToMany(mappedBy = "illustrations")
    private Collection<composant> composants;
}
