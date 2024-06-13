package com.hotelligence.paymentservice.repository;

import com.hotelligence.paymentservice.model.VNPay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VNPayRepository extends MongoRepository<VNPay, String> {

    Optional<VNPay> findByOrderInfo(String orderInfo);
}

