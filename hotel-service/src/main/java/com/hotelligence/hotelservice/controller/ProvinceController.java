package com.hotelligence.hotelservice.controller;

import com.hotelligence.hotelservice.dto.ProvinceResponse;
import com.hotelligence.hotelservice.model.Province;
import com.hotelligence.hotelservice.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping(path = "/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ProvinceResponse> getAllProvinces() {
        return provinceService.getAllProvinces();
    }
}
