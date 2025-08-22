package com.oracle.controller;

import java.util.List;
import com.oracle.beans.*;
import com.oracle.factory.ServiceFactory;
import com.oracle.service.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/user")
public class UserRESTController {

    private UserService userService = ServiceFactory.getUserService();
    private EMICardService emiCardService = ServiceFactory.getEMICardService();
    private ProductService productService = ServiceFactory.getProductService();
    private PurchaseService purchaseService = ServiceFactory.getPurchaseService();
    private TransactionService transactionService = ServiceFactory.getTransactionService();

    // ================= Register New User =================
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User newUser, @QueryParam("cardType") String cardType) {
        userService.registerNewUser(newUser, cardType);
        return Response.status(Response.Status.CREATED).entity(newUser).build();
    }

    // ================= Login =================
    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User credentials) {
        User loggedIn = userService.login(credentials.getUsername(), credentials.getPassword());
        return loggedIn != null
                ? Response.ok(loggedIn).build()
                : Response.status(Response.Status.UNAUTHORIZED)
                          .entity("Invalid credentials or inactive account.").build();
    }

    // ================= Get EMI Card Details =================
    @Path("/{userId}/card")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCard(@PathParam("userId") Long userId) {
        EMICard card = emiCardService.getCardByUserId(userId);
        return card != null
                ? Response.ok(card).build()
                : Response.status(Response.Status.NOT_FOUND).entity("No EMI card found.").build();
    }

    // ================= Get All Products =================
    @Path("/products")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        return Response.ok(productService.getAllProducts()).build();
    }

    // ================= Buy Product =================
    @Path("/{userId}/buy")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyProduct(@PathParam("userId") Long userId,
                               @QueryParam("productId") Long productId,
                               @QueryParam("tenureMonths") int tenure) {

        User user = userService.findUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found.").build();
        }

        EMICard card = emiCardService.getCardByUserId(userId);
        if (card == null || !"Active".equalsIgnoreCase(card.getStatus())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User does not have an active EMI card.").build();
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found.").build();
        }

        purchaseService.createPurchase(user, card, List.of(product), tenure);
        return Response.status(Response.Status.CREATED).entity("Purchase successful").build();
    }

    // ================= View My Transactions (Purchases only) =================
    @Path("/{userId}/transactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyTransactions(@PathParam("userId") Long userId) {
        List<Transaction> txns = transactionService.getTransactionsByUserId(userId);
        return Response.ok(txns).build();
    }

    // ================= Pay EMI =================
    @Path("/{userId}/payEmi")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response payNextEmi(@PathParam("userId") Long userId,
                               @QueryParam("purchaseId") Long purchaseId) {
        List<Purchase> ongoing = purchaseService.getOngoingPurchasesByUser(userId);

        Purchase selected = ongoing.stream()
                                   .filter(p -> p.getPurchaseId().equals(purchaseId))
                                   .findFirst()
                                   .orElse(null);

        if (selected == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Purchase not found or already completed.").build();
        }

        purchaseService.payNextEMI(selected);
        return Response.ok("EMI payment successful").build();
    }
}
