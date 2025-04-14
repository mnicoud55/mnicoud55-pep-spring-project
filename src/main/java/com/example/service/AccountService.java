package com.example.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

/*
 * Service class for the Account entity
 */
@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account newAccount) throws DataIntegrityViolationException {
        if (accountRepository.findByUsername(newAccount.getUsername()) != null)
            throw new DataIntegrityViolationException("Duplicate username detected.");
        return accountRepository.save(newAccount);
    }

    public Account verifyAccount(Account verify) throws SecurityException {
        Account account = accountRepository.findByUsername(verify.getUsername());
        if (account == null || !account.getPassword().equals(verify.getPassword()))   
            throw new SecurityException("Unsuccessful login.");
        return account;
    }

    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }
}
