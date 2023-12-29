package univ.rouen.backend.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Illustrations {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String description;
private double x;
private double y;

@Lob
private byte[] image;

@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
@JoinColumn(name = "illustration_id")
private List<Component> components;


}