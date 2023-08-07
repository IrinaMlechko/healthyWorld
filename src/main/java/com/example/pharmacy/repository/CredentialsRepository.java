package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
    boolean existsByLoginAndPassword(String userName, String encryptedPassword);

}
