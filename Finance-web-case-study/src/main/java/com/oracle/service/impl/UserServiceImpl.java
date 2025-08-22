package com.oracle.service.impl;

import com.oracle.beans.User;
import com.oracle.dao.UserDAO;
import com.oracle.factory.DAOFactory;
import com.oracle.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = DAOFactory.getUserDAO();

    @Override
    public User login(String username, String password) {
        return userDAO.login(username, password);
    }

    @Override
    public void registerNewUser(User user, String cardType) {
        userDAO.registerNewUser(user, cardType);
    }

    @Override
    public User findUserById(Long userId) {
        return userDAO.findUserById(userId);
    }
}
