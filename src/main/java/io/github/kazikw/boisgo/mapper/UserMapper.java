package io.github.kazikw.boisgo.mapper;

import io.github.kazikw.boisgo.dto.request.CreateNewFacilityRequest;
import io.github.kazikw.boisgo.dto.response.UserResponse;
import io.github.kazikw.boisgo.entity.Facility;
import io.github.kazikw.boisgo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse entityToResponse(User user);

}
