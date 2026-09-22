package io.github.kazikw.boisgo.mapper;

import io.github.kazikw.boisgo.dto.response.ReservationParticipantResponse;
import io.github.kazikw.boisgo.entity.ReservationParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationParticipantMapper {

    @Mapping(target = "reservationId", source = "reservation.id")
    @Mapping(target = "userId", source = "participant.id")
    ReservationParticipantResponse toResponse(ReservationParticipant entity);



}
