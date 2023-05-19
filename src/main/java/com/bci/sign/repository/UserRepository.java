package com.bci.sign.repository;

import com.bci.sign.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

   @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    //@Query(value = "select u.email from Users u where u.email in :email", nativeQuery = true)
    UserEntity findUserByEmailNamedParam(
            @Param("email") String email);
}
