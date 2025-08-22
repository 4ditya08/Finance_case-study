package com.oracle.controller;

import com.oracle.beans.Purchase;
import com.oracle.factory.ServiceFactory;
import com.oracle.service.PurchaseService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/v1/purchase")
public class PurchaseRESTController {

    private PurchaseService purchaseService = ServiceFactory.getPurchaseService();

    @GET
    @Path("/ongoing/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOngoing(@PathParam("userId") Long userId) {
        List<Purchase> purchases = purchaseService.getOngoingPurchasesByUser(userId);
        return Response.ok(purchases).build();
    }
}
