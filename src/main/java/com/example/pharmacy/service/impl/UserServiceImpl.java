package com.example.pharmacy.service.impl;


import com.example.pharmacy.dto.UserDto;
import com.example.pharmacy.entity.Credentials;
import com.example.pharmacy.entity.User;

import com.example.pharmacy.exception.IncorrectDataOfBirthFormat;
import com.example.pharmacy.exception.NotValidLoginException;
import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.exception.UserWithThisLoginAlreadyExists;
import com.example.pharmacy.repository.CredentialsRepository;
import com.example.pharmacy.repository.UserRepository;
import com.example.pharmacy.service.UserService;
import com.example.pharmacy.util.PasswordEncryptor;
import com.example.pharmacy.util.Role;
import com.example.pharmacy.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CredentialsRepository credentialsRepository) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public Optional<User> authenticate(String userName, String password) throws ServiceException {
        log.info("Authenticate and get user if exists: " + userName);
            String encryptedPassword = PasswordEncryptor.encryptPassword(password);
            return userRepository.findByCredentials_LoginAndCredentials_Password(userName, encryptedPassword);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        log.info("Get name for the user with login " + login);
        Optional<User> userOptional = userRepository.findByCredentials_Login(login);
        return userOptional;
    }

    @Override
    public boolean existsByLogin(String login) {
        log.info("Check if user " + login + " already exists.");
        return findUserByLogin(login).isPresent();
    }

    @Override
    public void createUser(User user) {
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

    @Override
    public void registerUser(UserDto userDto) throws NotValidLoginException, UserWithThisLoginAlreadyExists, IncorrectDataOfBirthFormat {
        String login = userDto.getLogin();
        if (!Validator.validateUsername(login)) {
            throw new NotValidLoginException();
        }
        if(existsByLogin(login)){
            throw new UserWithThisLoginAlreadyExists(String.format("User with login %s already exists. ", login));
        }
        Credentials credentials = Credentials.newBuilder().setLogin(login).setPassword(PasswordEncryptor.encryptPassword(userDto.getPassword())).setRole(Role.CUSTOMER).build();
        User user = User.newBuilder().setFirstName(userDto.getFirstName()).setLastName(userDto.getLastName()).setDateOfBirth(getLocalDate(userDto.getDateOfBirth())).setCredentials(credentials).build();
        credentialsRepository.save(credentials);
        userRepository.save(user);
    }

    private static LocalDate getLocalDate(String dateOfBirthStr) throws IncorrectDataOfBirthFormat {
        LocalDate dateOfBirth;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            dateOfBirth = LocalDate.parse(dateOfBirthStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IncorrectDataOfBirthFormat(e);
        }
        return dateOfBirth;
    }
}
