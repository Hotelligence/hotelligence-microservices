package com.hotelligence.bookingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelligence.bookingservice.dto.BookingRequest;
import com.hotelligence.bookingservice.repository.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class BookingServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookingRepository hotelRepository;

    @Test
    void shouldCreateBooking() throws Exception {
        BookingRequest bookingRequest = getBookingRequest();
        String hotelRequestString = objectMapper.writeValueAsString(bookingRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hotelRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, hotelRepository.findAll().size());
    }

    private BookingRequest getBookingRequest() {
        String checkinDateString = "2024-05-20T10:03:46.000+00:00";
        String checkoutDateString = "2024-05-21T10:03:46.000+00:00";
        String cancelDueString = "2024-05-28T10:03:46.000+00:00";
        String unCancelDueString = "2024-05-30T10:03:46.000+00:00";

        // Use a formatter to
        // parse the strings into LocalDateTime objects
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        LocalDateTime checkinDate = LocalDateTime.parse(checkinDateString, formatter);
        LocalDateTime checkoutDate = LocalDateTime.parse(checkoutDateString, formatter);
        LocalDateTime cancelDue = LocalDateTime.parse(cancelDueString, formatter);
        LocalDateTime unCancelDue = LocalDateTime.parse(unCancelDueString, formatter);

        return BookingRequest.builder()
                .roomId("664a3c9f35cb6f73daad0407")
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .bookingStatus("Đã hoàn tất")
                .cancelDue(cancelDue)
                .unCancelDue(unCancelDue)
                .build();
    }
}