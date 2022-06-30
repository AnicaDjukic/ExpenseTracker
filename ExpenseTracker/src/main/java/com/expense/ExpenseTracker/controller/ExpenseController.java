package com.expense.ExpenseTracker.controller;

import com.expense.ExpenseTracker.dto.ExpenseRequestDto;
import com.expense.ExpenseTracker.dto.ExpenseResponseDto;
import com.expense.ExpenseTracker.exception.NotFoundException;
import com.expense.ExpenseTracker.model.Expense;
import com.expense.ExpenseTracker.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ModelMapper modelMapper = new ModelMapper();

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("expenses")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ExpenseResponseDto create(@RequestBody @Valid ExpenseRequestDto newExpenseDto) {
        Expense expense = modelMapper.map(newExpenseDto, Expense.class);
        Expense savedExpense = expenseService.addNew(expense, newExpenseDto.getExpenseGroupId());
        return modelMapper.map(savedExpense, ExpenseResponseDto.class);
    }

    @GetMapping("expenses/{pageNo}/{size}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ExpenseResponseDto> getAll(@PathVariable int pageNo, @PathVariable int size) {
        Page<Expense> expenses = expenseService.getAll(pageNo, size);
        List<ExpenseResponseDto> expenseDtos = new ArrayList<>();
        for(Expense expense : expenses) {
            expenseDtos.add(modelMapper.map(expense, ExpenseResponseDto.class));
        }
        return expenseDtos;
    }

    @GetMapping("expense-groups/{id}/expenses")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ExpenseResponseDto> getLastFiveForExpenseGroup(@PathVariable UUID id) {
        List<Expense> expenses = expenseService.getByExpenseGroupId(id);
        List<ExpenseResponseDto> expenseDtos = new ArrayList<>();
        for(Expense expense : expenses) {
            expenseDtos.add(modelMapper.map(expense, ExpenseResponseDto.class));
        }
        return expenseDtos;
    }

    @GetMapping("expenses/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ExpenseResponseDto getById(@PathVariable UUID id) {
        Expense expense = expenseService.getById(id);
        return modelMapper.map(expense, ExpenseResponseDto.class);
    }

    @PutMapping("expenses/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ExpenseResponseDto update(@PathVariable UUID id, @RequestBody @Valid ExpenseRequestDto updateDto) {
        Expense updatedExpense = expenseService.update(id, updateDto);
        return modelMapper.map(updatedExpense, ExpenseResponseDto.class);
    }

    @DeleteMapping("expenses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable UUID id) throws NotFoundException {
        expenseService.deleteById(id);
    }

}
