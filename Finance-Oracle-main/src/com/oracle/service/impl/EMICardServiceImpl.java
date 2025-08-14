package com.oracle.service.impl;

import com.oracle.beans.EMICard;
import com.oracle.dao.EMICardDAO;
import com.oracle.factory.DAOFactory;
import com.oracle.service.EMICardService;

public class EMICardServiceImpl implements EMICardService {
    private EMICardDAO emicardDAO = DAOFactory.getEMICardDAO();

    @Override
    public EMICard getCardByUserId(Long userId) {
        return emicardDAO.getCardByUserId(userId);
    }
}
