package com.expense.ExpenseTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class IncomeRequestDto {

    @NotBlank
    private String description;

    private double amount;
}
