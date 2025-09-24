package com.eam.LevelUpCorp.businessLayer.validate;

import com.eam.LevelUpCorp.businessLayer.dto.UserDTO;

public class UserValidate {

    public static void validateCreate(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("The user is null");
        }

        if (userDTO.getFullName() == null) {
            throw new IllegalArgumentException("The full name is null");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().length() < 6) {
            throw new IllegalArgumentException("The password is null and it must be less than 6 characters");
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("The email is required");
        }

        if (userDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("The email address contains an underscore character");
        }
    }

    public static void validateSearch(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("");
        }
    }


    public static void validateUpdate(Long id, UserDTO userDTO) {
        validateSearch(id);
        validateCreate(userDTO);
    }

    public static void validateDelete(Long id) {
        validateSearch(id);
    }

    public static void validateLogin(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("The user is null");
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("The email is required");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("The password is required");

        }
    }
}