package com.oracle.beans;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emi_card") // match your actual DB table name
public class EMICard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber; // Newly added unique card number

    @Column(name = "card_type", nullable = false)
    private String cardType; // Gold / Titanium

    @Column(name = "card_limit", nullable = false)
    private double cardLimit;

    @Column(name = "remaining_limit", nullable = false)
    private double remainingLimit;

    @Column(name = "joining_fee", nullable = false)
    private double joiningFee;

    @Column(name = "valid_till", nullable = false)
    private LocalDate validTill;

    @Column(name = "STATUS", nullable = false)
    private String status; // Pending, Active, Blocked

    // ===== Getters & Setters =====

    public Long getCardId() {
        return cardId;
    }
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public double getCardLimit() {
        return cardLimit;
    }
    public void setCardLimit(double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public double getRemainingLimit() {
        return remainingLimit;
    }
    public void setRemainingLimit(double remainingLimit) {
        this.remainingLimit = remainingLimit;
    }

    public double getJoiningFee() {
        return joiningFee;
    }
    public void setJoiningFee(double joiningFee) {
        this.joiningFee = joiningFee;
    }

    public LocalDate getValidTill() {
        return validTill;
    }
    public void setValidTill(LocalDate validTill) {
        this.validTill = validTill;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
