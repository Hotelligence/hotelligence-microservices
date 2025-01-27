package com.hotelligence.hotelservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "favorites")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Favorite {
    @Id
    private String id;
    private String userId;
    private List<Hotel> favoriteHotels;
}
