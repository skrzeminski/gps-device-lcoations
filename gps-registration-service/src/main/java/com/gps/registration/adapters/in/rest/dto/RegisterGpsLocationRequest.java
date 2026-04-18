package com.gps.registration.adapters.in.rest.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.Instant;
import java.util.UUID;

public record RegisterGpsLocationRequest(

        @NotNull
        UUID deviceId,
        @DecimalMin(value = "-90.0")
        @DecimalMax(value = "90.0")
        double lat,
        @DecimalMin(value = "-180.0")
        @DecimalMax(value = "180.0")
        double lon,
        @NotNull
        @PastOrPresent
        Instant timestamp
) {}