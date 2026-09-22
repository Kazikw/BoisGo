//package io.github.kazikw.boisgo.mapper;
//
//public interface FacilityMapper     {
//}


package io.github.kazikw.boisgo.mapper;

import io.github.kazikw.boisgo.dto.request.CreateNewFacilityRequest;
import io.github.kazikw.boisgo.entity.Facility;
import io.github.kazikw.boisgo.dto.response.FacilityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacilityMapper {
    // Faclility ma id którego nie ma facility response; Zostanie zignorowane. W ta strone to ok
    FacilityResponse toResponse(Facility facility);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservationList", ignore = true)
    Facility facilityDtoToEntity(CreateNewFacilityRequest newFacility);

}