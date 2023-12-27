package univ.rouen.backend.DAO;


import java.io.Serializable;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class user implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, unique = false)
    private Role role;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private Collection<concept> concepts;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private Collection<traduction> traductions;

    public boolean isAdmin() {
        return this.role != null && userRole.Role_admin.equals(this.role.getName());
    }
}
