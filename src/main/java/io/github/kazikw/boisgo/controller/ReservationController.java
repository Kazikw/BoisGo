package io.github.kazikw.boisgo.controller;

import io.github.kazikw.boisgo.dto.request.CreateReservationRequest;
import io.github.kazikw.boisgo.dto.response.ReservationResponse;
import io.github.kazikw.boisgo.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody CreateReservationRequest request) {

        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId){
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public Page<ReservationResponse> getMyReservations(Pageable pageable){
        return reservationService.getMyReservations(pageable);
    }
    // I'm adding getFutureReservationsInMyTown. W wonna be informed about future reserwations that's is possible to join.
    // Infomration about reserwation far from us is usless

    @GetMapping("/facility/{facilityId}")
    public List<ReservationResponse> getReservationsByacilityAndDate(
            @PathVariable Long facilityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        //todo Ogarnąc validacje dla daty. To do 2 ogarnac sewis i mapper
        return reservationService.getReservationsForFacilityByDate(facilityId, date);
    }

    @GetMapping("/participant")
    public Page<ReservationResponse> getParticipantReservations(Pageable pageable){
        return reservationService.getParticipantReservation(pageable);
    }

    @GetMapping("/inTown")
    public Page<ReservationResponse> getJoinableReservationsByTown(Pageable pageable, String city){
        //Do zastanowienia sie czy ciy to powinno byc osobna tabela
        return reservationService.getJoinableReservationsByTown(pageable, city);
    }
}