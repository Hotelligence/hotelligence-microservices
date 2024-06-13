package com.hotelligence.paymentservice.controller;

import com.hotelligence.paymentservice.dto.VNPayRequest;
import com.hotelligence.paymentservice.dto.VNPayResponse;
import com.hotelligence.paymentservice.model.VNPay;
import com.hotelligence.paymentservice.repository.VNPayRepository;
import com.hotelligence.paymentservice.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final VNPayService vnpayService;
    private final VNPayRepository vnpRepository;

    @GetMapping({"", "/"})
    public String home(){
        return "createOrder";
    }

    @PostMapping("/submitOrder/{bookingId}")
    public ResponseEntity<String> submitOrder(@RequestBody VNPayRequest vnPayRequest,
                                              @PathVariable("bookingId") String bookingId,
                                              HttpServletRequest request){
//        String baseUrl = request.getScheme() + "://" + "localhost" + ":" + "3000";
//        return vnpayService.createOrder(vnPayRequest.getAmount(), vnPayRequest.getOrderInfo(), baseUrl);

        String baseUrl = request.getScheme() + "://" + "localhost" + ":" + "3000";
        String paymentUrl = vnpayService.createOrder(vnPayRequest.getAmount(), vnPayRequest.getOrderInfo(), bookingId, baseUrl);

        // Save order details (before payment is completed)
        VNPay vnPay = VNPay.builder()
                .amount(vnPayRequest.getAmount())
                .orderInfo(vnPayRequest.getOrderInfo())
                .paymentStatus("Pending")
                .build();
        vnpayService.saveVNPay(vnPay);

        return ResponseEntity.ok(paymentUrl);

    }

    @GetMapping("/paymentStatus")
    public void paymentReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int paymentStatus = vnpayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String transactionStatus = request.getParameter("vnp_TransactionStatus");

        Optional<VNPay> vnPayOpt = vnpRepository.findByOrderInfo(orderInfo);
        if (vnPayOpt.isPresent()) {
            VNPay vnPay = vnPayOpt.get();
            vnPay.setPaymentTime(paymentTime);
            vnPay.setTxnRef(transactionId);
            vnPay.setPaymentStatus(transactionStatus.equals("00") ? "Thành công" : "Thất bại");
            vnpayService.saveVNPay(vnPay);
        }


        String redirectUrl = String.format(
                "http://localhost:3000/paymentStatus/%s?vnp_Amount=%s&vnp_OrderInfo=%s&vnp_PayDate=%s&vnp_TransactionNo=%s&vnp_TransactionStatus=%s",
                URLEncoder.encode(totalPrice, StandardCharsets.UTF_8),
                URLEncoder.encode(orderInfo, StandardCharsets.UTF_8),
                URLEncoder.encode(paymentTime, StandardCharsets.UTF_8),
                URLEncoder.encode(transactionId, StandardCharsets.UTF_8),
                URLEncoder.encode(transactionStatus, StandardCharsets.UTF_8)
        );

        response.sendRedirect(redirectUrl);
    }
//    public String GetMapping(HttpServletRequest request, Model model){
//        int paymentStatus =vnpayService.orderReturn(request);
//
//        String orderInfo = request.getParameter("vnp_OrderInfo");
//        String paymentTime = request.getParameter("vnp_PayDate");
//        String transactionId = request.getParameter("vnp_TransactionNo");
//        String totalPrice = request.getParameter("vnp_Amount");
//
//        model.addAttribute("orderId", orderInfo);
//        model.addAttribute("totalPrice", totalPrice);
//        model.addAttribute("paymentTime", paymentTime);
//        model.addAttribute("transactionId", transactionId);
//
//        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
//    }

    @GetMapping("/getAllVNPay")
    @ResponseStatus(HttpStatus.OK)
    public List<VNPayResponse> getAllVNPay(){
        return vnpayService.getAllVNPay();
    }
}
