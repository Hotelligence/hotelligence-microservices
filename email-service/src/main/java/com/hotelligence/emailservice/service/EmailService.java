package com.hotelligence.emailservice.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Hotelligence");
        message.setTo(toEmail);
        message.setSubject("[Hotelligence] Mã OTP xác thực đặt phòng");
        message.setText("Mã OTP xác thực của Quý khách là: " + otp + "\n\n" +
                "Mã OTP có hiệu lực trong 5 phút.\n" +
                "Vui lòng không chia sẻ mã OTP này với bất kỳ ai.\n\n" +
                "Hotelligence Co., Ltd");

        mailSender.send(message);
        System.out.println("Email gửi thành công!");
    }

    public void sendCongratulationsEmail(String toEmail, String fullName, String bookingId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Hotelligence");
        message.setTo(toEmail);
        message.setSubject("[Hotelligence] Xác nhận đặt phòng thành công");
        message.setText("Kính chào Quý khách " + fullName + ",\n\n" +
                "Mã đặt phòng của Quý khách là: " + bookingId + "\n" +
                "Cảm ơn Quý khách đã đặt phòng tại hệ thống chúng tôi.\n" +
                "Chúc Quý khách có một trải nghiệm tuyệt vời!\n\n" +
                "Hotelligence Co., Ltd");

        mailSender.send(message);
        System.out.println("Email gửi thành công!");
    }

}
