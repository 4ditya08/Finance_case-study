package com.oracle.service;

import com.oracle.beans.User;

public interface UserService {
    User login(String username, String password);
    void registerNewUser(User user, String cardType);
}
