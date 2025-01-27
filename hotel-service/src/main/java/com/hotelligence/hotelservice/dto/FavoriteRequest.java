package com.hotelligence.hotelservice.dto;

import com.hotelligence.hotelservice.model.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequest {
    private String userId;
    private String hotelId;
}
