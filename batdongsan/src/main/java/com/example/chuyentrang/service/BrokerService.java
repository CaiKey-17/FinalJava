//package com.example.chuyentrang.service;
//
//import com.example.chuyentrang.model.Broker;
//import com.example.chuyentrang.repository.BrokerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BrokerService {
//    @Autowired
//    private BrokerRepository brokerRepository;
//
//    public Broker findByName(String name){
//        return brokerRepository.findByName(name);
//    }
//
//    public Iterable<Broker> findAll(){
//        return brokerRepository.findAll();
//    }
//
//    public Broker save(Broker broker){
//        return brokerRepository.save(broker);
//    }
//
//    public void delete(Broker broker){
//        brokerRepository.delete(broker);
//    }
//
//    public void deleteAll(){
//        brokerRepository.deleteAll();
//    }
//}
