package com.bci.sign.repository;

import com.bci.sign.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

   @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
   Optional<UserEntity> findUserByEmailNamedParam(
            @Param("email") String email);

   @Modifying
   @Transactional
   @Query(value = "UPDATE Users set token = :token WHERE email = :email", nativeQuery = true)
   void updateUserByEmailNamedParam(
           @Param("token") String token,
           @Param("email") String email);
}
