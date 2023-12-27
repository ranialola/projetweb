package univ.rouen.backend.DAO;
import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class concept implements  Serializable{
@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String nomConcept;
private Domaine domaine;
@ManyToOne
private user users;
@OneToMany(mappedBy ="concepts")
private Collection<illustration> illustrations;
}
