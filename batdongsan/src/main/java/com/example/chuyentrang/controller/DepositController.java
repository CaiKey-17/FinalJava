package com.example.chuyentrang.controller;

import com.example.chuyentrang.model.Deposit;
import com.example.chuyentrang.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/deposits")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @PostMapping("/user/{userId}")
    public String depositMoney(
            @PathVariable Long userId,
            @RequestParam Double amount) {
        try {
            depositService.createDeposit(userId, amount);
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
