package com.oracle.dao;

import com.oracle.beans.EMICard;

public interface EMICardDAO {
    void approveUserAndCard(Long userId);
    void listPendingUsers();

    EMICard getCardByUserId(Long userId); // ✅ new method
}
