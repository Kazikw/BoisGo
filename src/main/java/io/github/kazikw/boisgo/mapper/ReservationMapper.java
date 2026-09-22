package io.github.kazikw.boisgo.mapper;

import io.github.kazikw.boisgo.entity.Reservation;
import io.github.kazikw.boisgo.dto.request.CreateReservationRequest;
import io.github.kazikw.boisgo.dto.response.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//Będzie zawiertał w sobie obiekt facility który musze wiedziec jak zmapowac
@Mapper(componentModel = "spring", uses = FacilityMapper.class)
public interface ReservationMapper {
// tych danych user nie przekazuje wiec je zlewamy; serwis to ogarnie
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "facility", ignore = true)
    @Mapping(target = "reservationOwner", ignore = true)
    @Mapping(target = "status", ignore = true)
    Reservation toEntity(CreateReservationRequest request);

// trzeba w locie wyliczyc endTime
    @Mapping(target = "endTime", expression = "java(reservation.getStartTime().plusHours(1))")
    ReservationResponse toResponse(Reservation reservation);
//Page<Reservation> reservations
}