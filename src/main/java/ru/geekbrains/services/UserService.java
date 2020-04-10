package ru.geekbrains.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.UserDTO;
import ru.geekbrains.entities.PasswordResetToken;
import ru.geekbrains.entities.Role;
import ru.geekbrains.entities.User;
import ru.geekbrains.repositories.PasswordResetTokenRepository;
import ru.geekbrains.repositories.RoleRepository;
import ru.geekbrains.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private PasswordResetTokenRepository passwordTokenRepo;
    private PasswordEncoder pswEncoder;

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String USER_ROLE_TITLE = ROLE_PREFIX + "USER";


    @Autowired
    public void setUserRepository(UserRepository repo) {
        userRepo = repo;
    }


    @Autowired
    public void setRoleRepository(RoleRepository repo) {
        roleRepo = repo;
    }


    @Autowired
    public void setPasswordTokenRepo(PasswordResetTokenRepository repo) {
        passwordTokenRepo = repo;
    }


    @Autowired
    public void setPasswordEncoder(PasswordEncoder enc) {
        pswEncoder = enc;
    }


    public void registrateUser(UserDTO user) {
        Role userRole = roleRepo.findByTitle(USER_ROLE_TITLE)
                .orElseThrow(UserRoleNotFoundInDB::new);

        User newUser = new User();
        newUser.setUsername(user.username);
        String pswhash = pswEncoder.encode(user.password);
        newUser.setPassword(pswhash);
        newUser.setFirstname(user.firstname);
        newUser.setLastname(user.lastname);
        if (!user.email.isEmpty()) newUser.setEmail(user.email);
        newUser.setRole(userRole);

        userRepo.save(newUser);
    }


    public boolean isExistUser(UserDTO user) {
        return isExistUser(user.username);
    }


    public boolean isExistUser(String username) {
        Optional<User> userFromDB = userRepo.findByUsername(username);
        return userFromDB.isPresent();
    }


    public User getUser(String username) {
        return userRepo.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findById(Long id) {
        return userRepo.findById(id).get();
    }

    public boolean checkPassword(User user, String password) {
        String pwd = user.getPassword();
        return pswEncoder.matches(password, pwd);
    }


    public void updatePassword(User user, String newPassword) {
        String pswhash = pswEncoder.encode(newPassword);
        user.setPassword(pswhash);
        userRepo.save(user);
    }


    public void createPasswordResetToken(User user, String token) {
        PasswordResetToken prt = new PasswordResetToken(token, user);
        passwordTokenRepo.save(prt);
    }


    private static class UserRoleNotFoundInDB
            extends RuntimeException {

        UserRoleNotFoundInDB() {
            super("роль " + USER_ROLE_TITLE + " отсутствует в БД");
        }

    }


    private static class UserNotFoundException
            extends RuntimeException {

    }

}