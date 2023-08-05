package com.example.pharmacy.service;

import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.ServiceException;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface UserService {
    Optional<User>  authenticate(String userName, String password) throws ServiceException;

    Optional<User> findUserByLogin(String login) throws ServiceException;

    boolean existsByLogin(String login) throws ServiceException;

    void createUser(User user) throws ServiceException;

    void createCredentials(Credentials credentials) throws ServiceException, NoSuchAlgorithmException;
}
