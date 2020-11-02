package com.example.ttd.NameValidator;

public class UserInputValidator {

    public boolean isValidFullName(String fullName) {
        return FullNameValidator.isValidFullName(fullName);
    }

    public boolean isValidUsername(String username) {
        return ServerUsernameValidator.isValidUsername(username);
    }
}
