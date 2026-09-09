package com.example.lab9.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lab9.model.Account;
import com.example.lab9.model.DepositTransaction;
import com.example.lab9.repository.AccountRepository;
import com.example.lab9.repository.DepositRepository;

@Service
public class DepositService {

    private final AccountRepository accountRepository;
    private final DepositRepository depositRepository;

    public DepositService(
            AccountRepository accountRepository,
            DepositRepository depositRepository) {

        this.accountRepository = accountRepository;
        this.depositRepository = depositRepository;
    }

    @Transactional
    public void deposit(Long accountId, Double amount) {

        // 1. ค้นหา Account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 2. เพิ่มเงินใน Account
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // 3. สร้างประวัติการฝากเงิน
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setAccount(account);

        depositRepository.save(transaction);

        
    }
}