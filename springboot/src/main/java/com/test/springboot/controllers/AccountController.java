package com.test.springboot.controllers;

import com.test.springboot.models.Account;
import com.test.springboot.models.dto.TransactionRequestDto;
import com.test.springboot.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable("id") Long id) {
        Account account = accountService.findById(id);

        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        List<Account> accounts = accountService.findAll();

        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Account account) {
        Account createdAccount = accountService.save(account);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransactionRequestDto req) {
        accountService.makeTransfer(req.getAccountOrigin(), req.getAccountDestiny(), req.getAmount(), req.getBankId());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Transfer with success");

        return ResponseEntity.ok(response);
    }

}
