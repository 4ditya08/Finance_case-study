package com.oracle.dao;

import com.oracle.beans.User;

public interface UserDAO {
    User login(String username, String password);
    void registerNewUser(User user, String cardType); // Registration with EMI Card creation
}
