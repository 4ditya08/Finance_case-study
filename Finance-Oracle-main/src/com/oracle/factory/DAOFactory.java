package com.oracle.factory;

import com.oracle.dao.*;
import com.oracle.dao.impl.*;

public class DAOFactory {

    public static AdminDAO getAdminDAO() {
        return new AdminDAOImpl();
    }

    public static UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    public static EMICardDAO getEMICardDAO() {
        return new EMICardDAOImpl();
    }

    public static ProductDAO getProductDAO() {
        return new ProductDAOImpl();
    }

    public static PurchaseDAO getPurchaseDAO() {
        return new PurchaseDAOImpl();
    }

    public static TransactionDAO getTransactionDAO() {
        return new TransactionDAOImpl();
    }
}
