package com.oracle.controller;

import com.oracle.beans.Transaction;
import com.oracle.factory.ServiceFactory;
import com.oracle.service.TransactionService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/v1/transaction")
public class TransactionRESTController {

    private TransactionService transactionService = ServiceFactory.getTransactionService();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransactions() {
        List<Transaction> list = transactionService.getAllTransactions();
        return Response.ok(list).build();
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserTransactions(@PathParam("userId") Long userId) {
        return Response.ok(transactionService.getTransactionsByUserId(userId)).build();
    }
}
