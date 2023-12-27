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
public class composant implements  Serializable{
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id_Composant;
    private String localisation;
    private String terminologie;
    @ManyToOne
    private illustration illustrations;

}
