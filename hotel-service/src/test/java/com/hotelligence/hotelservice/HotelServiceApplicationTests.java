package com.hotelligence.hotelservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelligence.hotelservice.dto.HotelRequest;
import com.hotelligence.hotelservice.repository.HotelRepository;
import com.hotelligence.hotelservice.service.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class HotelServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HotelRepository hotelRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateHotel() throws Exception {
        HotelRequest hotelRequest = getHotelRequest();
        String hotelRequestString = objectMapper.writeValueAsString(hotelRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/hotel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hotelRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, hotelRepository.findAll().size());
    }

    private HotelRequest getHotelRequest() {
        return HotelRequest.builder()
                .name("Fusion Suites")
                .address("2 Trương Công Định")
                .star(4)
                .description("Khách sạn gần biển")
                .build();
    }

//    @Test
//    void shouldSortHotelListByStar() throws Exception {
//        HotelRequest hotelRequest = getHotelRequest();
//        String hotelRequestString = objectMapper.writeValueAsString(hotelRequest);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("api/hotel")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(hotelRequestString))
//                .andExpect(status().isOk());
//        Assertions.
//    }
//
//    private List<HotelRequest> sortHotelListByStar() {
//        return
//
//    }
}
