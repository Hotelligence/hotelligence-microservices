package com.hotelligence.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VNPayResponse {
    private String id;
    private String userId;
    private int amount;
    private String orderInfo;
    private String paymentTime;
    private String paymentStatus;
    private String txnRef;
}
