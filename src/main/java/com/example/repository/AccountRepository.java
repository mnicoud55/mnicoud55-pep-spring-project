package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    // Query to find Account by username rather than primary key id
    Account findByUsername(String username);
}
