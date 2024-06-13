package com.hotelligence.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "payments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VNPay {
    @Id
    private String id;
    private String userId;
    private int amount;
    private String orderInfo;
    private String paymentTime;
    private String paymentStatus;
    private String txnRef;
}
