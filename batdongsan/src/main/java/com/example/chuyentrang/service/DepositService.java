//package com.example.chuyentrang.service;
//
//import com.example.chuyentrang.model.Deposit;
//import com.example.chuyentrang.repository.DepositRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class DepositService {
//    @Autowired
//    private DepositRepository depositRepository;
//
//    public Deposit saveDeposit(Deposit deposit) {
//        return depositRepository.save(deposit);
//    }
//
//    public Iterable<Deposit> getAllDeposits() {
//        return depositRepository.findAll();
//    }
//
//    public void deleteDeposit(Deposit deposit) {
//        depositRepository.delete(deposit);
//    }
//
//    public Deposit update(int id, Deposit updatedDeposit) {
//        Optional<Deposit> existingDeposit = depositRepository.findById(id);
//        if (existingDeposit.isPresent()) {
//            Deposit deposit = existingDeposit.get();
//            deposit.setStatus(updatedDeposit.getStatus());
//            return depositRepository.save(deposit);
//        }
//        else {
//            throw new RuntimeException("Deposit not found");
//        }
//    }
//}
