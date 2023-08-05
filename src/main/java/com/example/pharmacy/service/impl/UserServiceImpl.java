package com.example.pharmacy.service.impl;


import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.User;

import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.repository.CredentialsRepository;
import com.example.pharmacy.repository.UserRepository;
import com.example.pharmacy.service.UserService;
import com.example.pharmacy.util.PasswordEncryptor;
import com.example.pharmacy.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CredentialsRepository credentialsRepository) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public boolean authenticate(String userName, String password) throws ServiceException {
        log.info("Authenticate user " + userName);
        boolean match;
        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(password);
            match = credentialsRepository.existsByLoginAndPassword(userName, encryptedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error encrypting password", e);
        }
        return match;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        log.info("Get name for the user with login " + login);
        Optional<User> userOptional = credentialsRepository.findUserByLogin(login);
        return userOptional;
    }

    @Override
    public boolean existsByLogin(String login) throws ServiceException {
        log.info("Check if user " + login + " already exists.");
        return findUserByLogin(login).isPresent();
    }

    @Override
    public void createUser(User user) throws ServiceException {
        userRepository.save(user);

    }

    @Override
    public void createCredentials(Credentials credentials) throws ServiceException, NoSuchAlgorithmException {
        if (!Validator.validateUsername(credentials.getLogin())) {
            throw new ServiceException("Invalid username.");
        }
            String encryptedPassword = PasswordEncryptor.encryptPassword(credentials.getPassword());
            credentials.setPassword(encryptedPassword);
            credentialsRepository.save(credentials);
    }

}
