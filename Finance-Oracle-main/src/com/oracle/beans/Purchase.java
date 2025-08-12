package com.oracle.beans;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private EMICard card;

    @Column(name = "purchase_date", insertable = false, updatable = false)
    private Date purchaseDate;

    @Column(name = "tenure_months", nullable = false)
    private int tenureMonths;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "processing_fee", nullable = false)
    private double processingFee;

    @Column(name = "status", nullable = false)
    private String status; // Ongoing / Completed
    
    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY)
    private List<PurchaseItem> items;

    public List<PurchaseItem> getItems() {
        return items;
    }
    
    
    
    public void setItems(List<PurchaseItem> items) {
        this.items = items;
    }
    
    public Long getPurchaseId() { return purchaseId; }
    public void setPurchaseId(Long purchaseId) { this.purchaseId = purchaseId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public EMICard getCard() { return card; }
    public void setCard(EMICard card) { this.card = card; }
    public Date getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }
    public int getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(int tenureMonths) { this.tenureMonths = tenureMonths; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public double getProcessingFee() { return processingFee; }
    public void setProcessingFee(double processingFee) { this.processingFee = processingFee; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
