package com.example.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /*
     * THROW EXCEPTION INSTEAD OF RETURN NULL??
     * ???????????????????????????????????????
     * _________________________________________________
     */
    public Account createAccount(Account newAccount) {
        if (accountRepository.findByUsername(newAccount.getUsername()) != null)
            return null;
        return accountRepository.save(newAccount);
    }

    public Account verifyAccount(Account verify) {
        Account account = accountRepository.findByUsername(verify.getUsername());
        if (account == null || !account.getPassword().equals(verify.getPassword()))   
            return null;
        return account;
    }

    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }
}
