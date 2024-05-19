package com.hotelligence.hotelservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "provinces")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Province {
    @Id
    private String id;
    private String province;
    private List<String> city;
}
