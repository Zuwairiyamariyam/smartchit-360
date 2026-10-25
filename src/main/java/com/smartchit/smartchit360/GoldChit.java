package com.smartchit.smartchit360;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "gold_chit_ledger")
public class GoldChit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String shopName;
    private BigDecimal monthlyDeposit;
    private BigDecimal spotRate;
    private BigDecimal gramsCredited;
    private String receiptHash;
    private LocalDateTime timestamp;

    public GoldChit() {}

    public GoldChit(String customerName, String shopName, BigDecimal monthlyDeposit, 
                           BigDecimal spotRate, BigDecimal gramsCredited, String receiptHash, LocalDateTime timestamp) {
        this.customerName = customerName;
        this.shopName = shopName;
        this.monthlyDeposit = monthlyDeposit;
        this.spotRate = spotRate;
        this.gramsCredited = gramsCredited;
        this.receiptHash = receiptHash;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getShopName() { return shopName; }
    public BigDecimal getMonthlyDeposit() { return monthlyDeposit; }
    public BigDecimal getSpotRate() { return spotRate; }
    public BigDecimal getGramsCredited() { return gramsCredited; }
    public String getReceiptHash() { return receiptHash; }
    public LocalDateTime getTimestamp() { return timestamp; }
}