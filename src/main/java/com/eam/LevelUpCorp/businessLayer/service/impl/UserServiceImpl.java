package com.eam.LevelUpCorp.businessLayer.service.impl;

import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserRegisterDTO;
import com.eam.LevelUpCorp.businessLayer.service.UserService;
import com.eam.LevelUpCorp.businessLayer.validate.UserValidate;
import com.eam.LevelUpCorp.persistenceLayer.dao.UserDAO;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserDAO userDAO;
    private final UserValidate valiUser;


    /*
    Method for create user.
     */
    @Override
    public UserDTO createUser(UserDTO createDTO) {
        log.info("Create a new User: {}", createDTO.getFullName());
        valiUser.validateCreate(createDTO);
        UserDTO createdUser = userDAO.save(createDTO);
        if (createdUser == null) {
            throw new RuntimeException("Error while saving user");
        }
        log.info("Create user successfully with ID: {}", createdUser.getId());
        return createdUser;
    }



    /*
    Method for search a user.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        log.info("Get user by ID: {}", id);
        return userDAO.findById(id).orElseThrow(() -> {
            log.warn("Get user by ID failure: {}", id);
            return new RuntimeException("User not found with ID:" + id);
        });
    }


    /*
    Method for get all users.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Get all users");
        List<UserDTO> users = userDAO.findAll();
        if (users.isEmpty()) {
            log.warn("No users found");
            throw new RuntimeException("No users available");
        }

        log.info("Found {} users", users.size());
        return users;
    }


    /*
    Method for logger a user.
     */
    @Transactional(readOnly = true)
    @Override
    public UserDTO login(String email, String password) {
        log.info("Login User: {}", email);
        UserDTO user = userDAO.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Login User failure: {}", email);
                    return new RuntimeException("Invalid credentials");
                });
        if (!user.getPassword().equals(password)) {
            log.warn("Login failed - invalid password for email: {}", email);
            throw new RuntimeException("Invalid credentials");
        }
        log.info("Login successful for email: {}", email);
        return user;
    }


    @Override
    public UserDTO register(UserRegisterDTO registerDTO) {
        log.info("Registering new user: {}", registerDTO.getName());

        valiUser.validateRegister(registerDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setFullName(registerDTO.getName());
        userDTO.setEmail(registerDTO.getEmail());
        userDTO.setPassword(registerDTO.getPassword());
        userDTO.setRole(registerDTO.getRole());

        UserDTO createdUser = userDAO.saveRegister(registerDTO);
        log.info("User registered successfully with ID: {}", createdUser.getId());

        return createdUser;
    }



    /*
    Method for update a user.
     */
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Update user by ID: {}", id);
        getUserById(id);

        valiUser.validateUpdate(id, userDTO);
        UserDTO userUpdated = userDAO.update(id, userDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar"));
        log.info("User successfully updated ID: {}", id);
        return userUpdated;

    }


    /*
    Method for delete a user.
     */
    @Override
    public void deleteUser(Long id) {
        log.info("Delete user by ID: {}", id);

        getUserById(id);
        valiUser.validateDelete(id);
        boolean deleted = userDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar el user");
        }
        log.info("Delete user successfully deleted ID: {}", id);
    }



}
