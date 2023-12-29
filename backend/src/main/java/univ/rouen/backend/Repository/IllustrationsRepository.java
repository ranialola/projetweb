// IllustrationsRepository.java
package univ.rouen.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.rouen.backend.DAO.Illustrations;

@Repository
public interface IllustrationsRepository extends JpaRepository<Illustrations, Long> {
}