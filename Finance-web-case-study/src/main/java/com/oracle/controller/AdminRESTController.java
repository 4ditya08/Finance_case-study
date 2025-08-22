package com.oracle.controller;

import com.oracle.factory.ServiceFactory;

import com.oracle.service.AdminService;
import com.oracle.service.ProductService;
import com.oracle.service.TransactionService;
import com.oracle.beans.Product;
import com.oracle.beans.Transaction;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/v1/admin")
public class AdminRESTController {

    private AdminService adminService = ServiceFactory.getAdminService();
    private ProductService productService = ServiceFactory.getProductService();
    private TransactionService transactionService = ServiceFactory.getTransactionService();

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(com.oracle.beans.Admin admin) {
        boolean ok = adminService.login(admin.getUsername(), admin.getPassword());
        return ok ? Response.ok("Admin login successful").build()
                  : Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
    }

    @Path("/pendingUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPendingUsers() {
        adminService.listPendingUsers();
        return Response.ok("See server logs for pending users list (or implement return type)").build();
    }

    @Path("/approve/{userId}")
    @POST
    public Response approveUser(@PathParam("userId") Long userId) {
        adminService.approveUserAndCard(userId);
        return Response.ok("User approved.").build();
    }

    @Path("/products")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        return Response.ok(productService.getAllProducts()).build();
    }

    @Path("/product")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {
        productService.addProduct(product);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @Path("/product/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") Long id, Product updated) {
        updated.setProductId(id);
        productService.updateProduct(updated);
        return Response.ok(updated).build();
    }

    @Path("/product/{id}")
    @DELETE
    public Response deleteProduct(@PathParam("id") Long id) {
        productService.deleteProduct(id);
        return Response.ok("Product deleted").build();
    }

    @Path("/transactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllTransactions() {
        List<Transaction> txns = transactionService.getAllTransactions();
        return Response.ok(txns).build();
    }
}
