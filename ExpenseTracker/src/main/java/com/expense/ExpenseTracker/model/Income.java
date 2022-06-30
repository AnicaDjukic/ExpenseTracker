package com.expense.ExpenseTracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Income {

    @Id
    @GeneratedValue
    private UUID id;

    @Setter
    private String description;

    @Setter
    private double amount;

    @Setter
    private Date creationTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private IncomeGroup incomeGroup;

    @PrePersist
    protected void onCreate() {
        creationTime = new Date();
    }
}
