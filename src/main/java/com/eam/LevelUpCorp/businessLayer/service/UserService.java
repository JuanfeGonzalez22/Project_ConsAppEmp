package com.eam.LevelUpCorp.businessLayer.service;

import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;
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

    //Update.
    UserDTO updateUser(Long id, UserDTO userDTO);
}
