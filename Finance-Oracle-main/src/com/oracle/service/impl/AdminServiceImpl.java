package com.oracle.service.impl;

import com.oracle.beans.Admin;
import com.oracle.dao.AdminDAO;
import com.oracle.dao.EMICardDAO;
import com.oracle.factory.DAOFactory;
import com.oracle.service.AdminService;

public class AdminServiceImpl implements AdminService {
    private AdminDAO adminDAO = DAOFactory.getAdminDAO();
    private EMICardDAO emicardDAO = DAOFactory.getEMICardDAO();
    private Admin loggedAdmin;

    @Override
    public boolean login(String username, String password) {
        Admin admin = adminDAO.login(username, password);
        if (admin != null) {
            this.loggedAdmin = admin;
            return true;
        }
        return false;
    }

    @Override
    public void listPendingUsers() {
        emicardDAO.listPendingUsers();
    }

    @Override
    public void approveUserAndCard(long userId) {
        emicardDAO.approveUserAndCard(userId);
    }
}
