package io.github.kazikw.boisgo.dto.response;

public record ReservationParticipantResponse(
        Long id, Long reservationId, Long userId
) {
}
