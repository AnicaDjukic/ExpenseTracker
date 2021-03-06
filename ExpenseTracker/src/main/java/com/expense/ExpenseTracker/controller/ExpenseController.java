package com.expense.ExpenseTracker.controller;

import com.expense.ExpenseTracker.dto.ExpenseRequestDto;
import com.expense.ExpenseTracker.dto.ExpenseResponseDto;
import com.expense.ExpenseTracker.exception.NotFoundException;
import com.expense.ExpenseTracker.model.Expense;
import com.expense.ExpenseTracker.security.SecurityUtil;
import com.expense.ExpenseTracker.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class ExpenseController {

    private final ExpenseService expenseService;

    private final ModelMapper modelMapper = new ModelMapper();

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("expenses")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ExpenseResponseDto create(@RequestBody @Valid ExpenseRequestDto newExpenseDto) {
        String username = SecurityUtil.getLoggedIn().getName();
        Expense expense = modelMapper.map(newExpenseDto, Expense.class);
        Expense savedExpense = expenseService.addNew(expense, newExpenseDto.getExpenseGroupId(), username);
        return modelMapper.map(savedExpense, ExpenseResponseDto.class);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("expenses")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ExpenseResponseDto> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "5") int size) {
        String username = SecurityUtil.getLoggedIn().getName();
        Page<Expense> expenses = expenseService.getAll(page, size, username);
        return expenses.map(expense -> modelMapper.map(expense, ExpenseResponseDto.class));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("expense-groups/{id}/expenses")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ExpenseResponseDto> getLastFewForExpenseGroup(@PathVariable UUID id,
                                                              @RequestParam(required = false, defaultValue = "5") int size) {
        String username = SecurityUtil.getLoggedIn().getName();
        List<Expense> expenses = expenseService.getByExpenseGroupId(id, size, username);
        return expenses.stream().map(expense -> modelMapper.map(expense, ExpenseResponseDto.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("expenses/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ExpenseResponseDto getById(@PathVariable UUID id) {
        String username = SecurityUtil.getLoggedIn().getName();
        Expense expense = expenseService.getByIdAndUsername(id, username);
        return modelMapper.map(expense, ExpenseResponseDto.class);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("expenses/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ExpenseResponseDto update(@PathVariable UUID id,
                                     @RequestBody @Valid ExpenseRequestDto updateDto) {
        String username = SecurityUtil.getLoggedIn().getName();
        Expense updatedExpense = expenseService.update(id, updateDto, username);
        return modelMapper.map(updatedExpense, ExpenseResponseDto.class);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("expenses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable UUID id) throws NotFoundException {
        String username = SecurityUtil.getLoggedIn().getName();
        expenseService.deleteById(id, username);
    }

}
