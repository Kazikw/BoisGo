package io.github.kazikw.boisgo.controller;

import io.github.kazikw.boisgo.dto.request.CreateNewFacilityRequest;
import io.github.kazikw.boisgo.dto.response.FacilityResponse;
import io.github.kazikw.boisgo.service.FacilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
//@Controller("/api/v1/")
//@RestController("/api/v1")//to jest tylko adnotacja ze to cnotroler; Sciezke daje sie gdzie inndziej
@RestController
@RequestMapping("/api/v1/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @PostMapping
    ResponseEntity<Object> insertFacility(@Valid @RequestBody CreateNewFacilityRequest request){
//        log.info("Dodaję facility: {}", request.city()); // W logach używaj klamerek {}, to bezpieczniejsze niż plusy
//        log.info("Ddaje facility: " + request.toString());
//        log.info("Ddaje facility: {} do DB", request.toString());//POPRAWNY DEBBUG
        FacilityResponse response = facilityService.addNewFacility(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
