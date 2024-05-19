package com.hotelligence.hotelservice.service;

import com.hotelligence.hotelservice.dto.ProvinceResponse;
import com.hotelligence.hotelservice.model.Province;
import com.hotelligence.hotelservice.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProvinceService {
    private final ProvinceRepository provinceRepository;

    public List<ProvinceResponse> getAllProvinces() {
        List<Province> provinces = provinceRepository.findAll();

        return provinces.stream().map(this::mapToProvinceResponse).toList();
    }

    private ProvinceResponse mapToProvinceResponse(Province province) {
        return ProvinceResponse.builder()
                .id(province.getId())
                .province(province.getProvince())
                .city(province.getCity())
                .build();
    }
}
