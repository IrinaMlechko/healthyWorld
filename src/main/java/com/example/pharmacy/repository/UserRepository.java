package com.example.pharmacy.repository;

import com.example.pharmacy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query("SELECT u " +
//            "FROM User u " +
//            "JOIN Credentials c " +
//            "ON u.credentials = c " +
//            "WHERE c.login = :login")
//    Optional<User> findUserFirstNameByLogin(String login);

    Optional<User> findByCredentials_Login(String login);

    Optional<User> findByCredentials_LoginAndCredentials_Password(String userName, String encryptedPassword);
}
