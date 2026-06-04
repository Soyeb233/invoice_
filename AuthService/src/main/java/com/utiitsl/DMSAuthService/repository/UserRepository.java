package com.utiitsl.DMSAuthService.repository;

import com.utiitsl.DMSAuthService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsernameOrEmail(
            String username,
            String email
    );

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
//    void deleteByUsername(String username);

    Optional<User> existsByEmailAndIdNot(String email, Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.activeStatus = :activeStatus WHERE u.id = :userId")
    int updateUserActiveStatus(Integer userId, boolean activeStatus);

    //    @Query("SELECT u FROM USER u WHERE u.email = :email AND u.activeStatus = :isActivated ")
    Optional<User> findByEmailAndActiveStatus(
            @Param("email") String email,
            @Param("deactivated") boolean isActivated
    );

    @Query("SELECT COUNT(u) FROM User u WHERE u.activeStatus = true")
    Long findTotalActiveUserCount();
}
