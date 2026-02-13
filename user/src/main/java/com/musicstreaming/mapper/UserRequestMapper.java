package com.musicstreaming.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.musicstreaming.dto.UserRequest;
import com.musicstreaming.entities.User;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    @Mapping(target = "id", ignore = true)
    User UserRequestToUser(UserRequest userRequest); 

}
