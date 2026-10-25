package com.smartchit.smartchit360;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "chit_records")
public class GroupDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String organizerName;
    private Integer totalMembers;
    private BigDecimal totalPoolAmount;
    private BigDecimal monthlyInstallment;
    private Integer durationMonths;

    public GroupDetails() {}

    public GroupDetails(String category, String organizerName, Integer totalMembers, 
                      BigDecimal totalPoolAmount, BigDecimal monthlyInstallment, Integer durationMonths) {
        this.category = category;
        this.organizerName = organizerName;
        this.totalMembers = totalMembers;
        this.totalPoolAmount = totalPoolAmount;
        this.monthlyInstallment = monthlyInstallment;
        this.durationMonths = durationMonths;
    }

    public Long getId() { return id; }
    public String getCategory() { return category; }
    public String getOrganizerName() { return organizerName; }
    public Integer getTotalMembers() { return totalMembers; }
    public BigDecimal getTotalPoolAmount() { return totalPoolAmount; }
    public BigDecimal getMonthlyInstallment() { return monthlyInstallment; }
    public Integer getDurationMonths() { return durationMonths; }
}