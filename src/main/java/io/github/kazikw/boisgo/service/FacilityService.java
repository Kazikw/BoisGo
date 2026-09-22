package io.github.kazikw.boisgo.service;

import io.github.kazikw.boisgo.dto.request.CreateNewFacilityRequest;
import io.github.kazikw.boisgo.dto.response.FacilityResponse;
import io.github.kazikw.boisgo.entity.Facility;
import io.github.kazikw.boisgo.mapper.FacilityMapper;
import io.github.kazikw.boisgo.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;
    public FacilityResponse addNewFacility(CreateNewFacilityRequest newFacilityRequest){
        Facility newFacility = facilityMapper.facilityDtoToEntity(newFacilityRequest);
        Facility savedFacility = facilityRepository.save(newFacility);
        return facilityMapper.toResponse(savedFacility);//TO DO; DODANO ZWRACANA WARTOSC
    }

}
