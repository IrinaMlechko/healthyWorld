package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
    boolean existsByLoginAndPassword(String userName, String encryptedPassword);

}
