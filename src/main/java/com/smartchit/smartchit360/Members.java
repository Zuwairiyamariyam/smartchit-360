package com.smartchit.smartchit360;

import jakarta.persistence.*;

@Entity
@Table(name = "chit_participants")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chitId;
    private String memberName;
    private Integer turnOrder;
    private String assignedMonth;
    private String leaderName;

    public Members() {}

    public Members(Long chitId, String memberName, Integer turnOrder, String assignedMonth, String leaderName) {
        this.chitId = chitId;
        this.memberName = memberName;
        this.turnOrder = turnOrder;
        this.assignedMonth = assignedMonth;
        this.leaderName = leaderName;
    }

    public Long getId() { return id; }
    public Long getChitId() { return chitId; }
    public String getMemberName() { return memberName; }
    public Integer getTurnOrder() { return turnOrder; }
    public void setTurnOrder(Integer turnOrder) { this.turnOrder = turnOrder; }
    public String getAssignedMonth() { return assignedMonth; }
    public void setAssignedMonth(String assignedMonth) { this.assignedMonth = assignedMonth; }
    public String getLeaderName() { return leaderName; }
}