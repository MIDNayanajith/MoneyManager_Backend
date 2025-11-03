package com.example.moneyManager.service;


import com.example.moneyManager.dto.ExpenseDto;
import com.example.moneyManager.dto.IncomeDto;
import com.example.moneyManager.dto.RecentTransactionDto;
import com.example.moneyManager.entity.ProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Stream.concat;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final ProfileService profileService;

    public Map<String,Object> getDashboardData(){
        ProfileEntity profile = profileService.getCurrentProfile();
        Map<String,Object> returnValue = new LinkedHashMap<>();
        List<IncomeDto> latestIncome = incomeService.getLatest5IncomesForCurrentUser();
        List<ExpenseDto> latestExpense = expenseService.getLatest5ExpensesForCurrentUser();
        List<RecentTransactionDto> recentTransactions = concat(latestIncome.stream().map(income ->
                        RecentTransactionDto.builder()
                                .id(income.getId())
                                .profileId(profile.getId())
                                .icon(income.getIcon())
                                .name(income.getName())
                                .amount(income.getAmount())
                                .date(income.getDate())
                                .createdAt(income.getCreatedAt())
                                .updatedAt(income.getUpdatedAt())
                                .type("income")
                                .build()),
                latestExpense.stream().map(expense ->
                        RecentTransactionDto.builder()
                                .id(expense.getId())
                                .profileId(profile.getId())
                                .icon(expense.getIcon())
                                .name(expense.getName())
                                .amount(expense.getAmount())
                                .date(expense.getDate())
                                .createdAt(expense.getCreatedAt())
                                .updatedAt(expense.getUpdatedAt())
                                .type("expense")
                                .build()))
                .sorted((a,b)->{
                    int cmp = b.getDate().compareTo(a.getDate());
                    if(cmp == 0 && a.getCreatedAt() != null && b.getCreatedAt() != null){
                        return b.getCreatedAt().compareTo(a.getCreatedAt());
                    }
                    return cmp;
                }).collect(Collectors.toList());
        returnValue.put("totalBalance",incomeService.getTotalIncomeForCurrentUser().subtract(expenseService.getTotalExpensesForCurrentUser()));
        returnValue.put("totalIncome",incomeService.getTotalIncomeForCurrentUser());
        returnValue.put("recent5Expenses",expenseService.getLatest5ExpensesForCurrentUser());
        returnValue.put("recent5Incomes",latestIncome);
        returnValue.put("recentTrnasactions",recentTransactions);
        return  returnValue;

    }
}
