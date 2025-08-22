package com.oracle.service;

public interface AdminService {
    boolean login(String username, String password);
    void listPendingUsers();
    void approveUserAndCard(long userId);
}
