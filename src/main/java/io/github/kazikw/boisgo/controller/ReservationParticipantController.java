package io.github.kazikw.boisgo.controller;

import io.github.kazikw.boisgo.dto.response.ReservationParticipantResponse;
import io.github.kazikw.boisgo.service.ReservationParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationParticipantController {

    private final ReservationParticipantService reservationParticipantService;

    @PostMapping("/{reservationId}/participants")
    public ResponseEntity<ReservationParticipantResponse> joinReservation(
            @PathVariable Long reservationId) {

        ReservationParticipantResponse response =
                reservationParticipantService.createReservationParticipant(reservationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //TODO dodac opcje przekazania admina innemu userowi
    @DeleteMapping("/{reservationId}/participants")
    public ResponseEntity<Void> deleteReservationParticipant( @PathVariable Long reservationId){
        reservationParticipantService.deleteReservationParticipant(reservationId);
        return ResponseEntity.noContent().build();
    }


}