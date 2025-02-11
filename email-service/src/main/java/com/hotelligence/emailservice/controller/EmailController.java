package com.hotelligence.emailservice.controller;

import com.hotelligence.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping(path = "/sendOtpEmail/{toEmail}/{otp}")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendOtpEmail(@PathVariable String toEmail, @PathVariable String otp) {
        emailService.sendOtpEmail(toEmail, otp);
    }

    @PostMapping(path = "/sendCongratulationsEmail/{toEmail}/{fullName}/{bookingId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendCongratulationsEmail(@PathVariable String toEmail, @PathVariable String fullName, @PathVariable String bookingId) {
        emailService.sendCongratulationsEmail(toEmail, fullName, bookingId);
    }
}
