package io.github.kazikw.boisgo.dto.response;

import io.github.kazikw.boisgo.entity.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        Long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        int capacity,
        Boolean isPublic,
        ReservationStatus status,
        FacilityResponse facility
) {
}
//TODO ZAIERA POLE END TIME. SERVICE POWINIEN JA OBLICZYC