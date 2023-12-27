package univ.rouen.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import univ.rouen.backend.DAO.Role;
import univ.rouen.backend.DAO.user;
import univ.rouen.backend.DAO.userRole;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(userRole name);
 
}
