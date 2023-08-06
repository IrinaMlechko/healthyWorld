package com.example.pharmacy.service;

import com.example.pharmacy.dto.UserDto;
import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.User;
import com.example.pharmacy.exception.IncorrectDataOfBirthFormat;
import com.example.pharmacy.exception.NotValidLoginException;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.exception.UserWithThisLoginAlreadyExists;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface UserService {
    Optional<User>  authenticate(String userName, String password) throws ServiceException;

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserById(int id);

    boolean existsByLogin(String login) throws ServiceException;

    void createUser(User user) throws ServiceException;

    void createCredentials(Credentials credentials) throws ServiceException, NoSuchAlgorithmException;

    void registerUser(UserDto userDto) throws NotValidLoginException, ServiceException, UserWithThisLoginAlreadyExists, IncorrectDataOfBirthFormat;
}
