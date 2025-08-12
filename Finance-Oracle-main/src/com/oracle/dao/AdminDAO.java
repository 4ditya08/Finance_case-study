package com.oracle.dao;

import com.oracle.beans.Admin;

public interface AdminDAO {
    Admin login(String username, String password);

	void printAllUsers();
}
