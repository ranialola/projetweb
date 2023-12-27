package univ.rouen.backend.Repository;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import univ.rouen.backend.DAO.Role;
import univ.rouen.backend.DAO.user;
import univ.rouen.backend.DTO.UsersDTO;


@Repository

public interface UserLoginRep extends JpaRepository<user,Integer>{
   
    Optional<user> findOneBymailAndMdp(String mail, String mdp);
    user findBymail(String mail);
    @Modifying
    @Transactional
    @Query("UPDATE user u SET u.nom = :nom, u.prenom = :prenom, u.mail = :mail, u.mdp = :mdp WHERE u.id = :id")
    void updateUser(@Param("id") int id, @Param("nom") String nom, @Param("prenom") String prenom, @Param("mail") String mail, @Param("mdp") String mdp);
    @Modifying
    @Transactional
    @Query("DELETE FROM user u WHERE u.id = :id")
    void deleteUserById(@Param("id") int id);
    List<user> findAll();
    Optional<user> findById(int id);





}
