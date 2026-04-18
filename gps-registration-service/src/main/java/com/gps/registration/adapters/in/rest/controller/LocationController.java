package com.gps.registration.adapters.in.rest.controller;


import com.gps.registration.adapters.in.rest.dto.RegisterGpsLocationRequest;
import com.gps.registration.application.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/devices/{deviceId}/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Void> sendLocation(
            @PathVariable String deviceId,
            @Valid @RequestBody RegisterGpsLocationRequest req) {
        locationService.send(deviceId, req.lat(), req.lon(), req.timestamp());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}