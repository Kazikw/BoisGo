package io.github.kazikw.boisgo.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequest(

        @NotNull(message = "Data rezerwacji jest wymagana")
        @Future(message = "Data rezerwacji musi być w przyszłości")
        LocalDate date,

        @NotNull(message = "Godzina rozpoczęcia jest wymagana")
        LocalTime startTime,

        @NotNull(message = "Pojemność jest wymagana")
        @Min(value = 1, message = "Rezerwacja musi mieć co najmniej 1 miejsce")
        int capacity,

        @NotNull(message = "Musisz określić, czy rezerwacja jest publiczna")
        Boolean isPublic,

        @NotNull(message = "Musisz wskazać boisko")
        Long facilityId
) {
}
