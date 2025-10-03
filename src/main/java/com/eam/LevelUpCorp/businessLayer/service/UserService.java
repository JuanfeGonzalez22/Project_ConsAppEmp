package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
import com.eam.LevelUpCorp.businessLayer.dto.UserRegisterDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface UserService {

    //Create.
    UserDTO createUser(UserDTO userDTO);

    //Search for ID.
    UserDTO getUserById(Long id);

    //Search for everyone.
    List<UserDTO> getAllUsers();

    //Delete.
    void deleteUser(Long id);

    //Login.
    UserDTO login(String email, String password);

    //Register a new user (aprendiz o instructor)
    UserDTO register(UserRegisterDTO registerDTO);

    //Update.
    UserDTO updateUser(Long id, UserDTO userDTO);
}
