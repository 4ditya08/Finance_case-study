package com.oracle.factory;

import com.oracle.service.*;
import com.oracle.service.impl.*;

public class ServiceFactory {

    public static AdminService getAdminService() {
        return new AdminServiceImpl();
    }

    public static UserService getUserService() {
        return new UserServiceImpl();
    }

    public static EMICardService getEMICardService() {
        return new EMICardServiceImpl();
    }

    public static ProductService getProductService() {
        return new ProductServiceImpl();
    }

    public static PurchaseService getPurchaseService() {
        return new PurchaseServiceImpl();
    }

    public static TransactionService getTransactionService() {
        return new TransactionServiceImpl();
    }
}
