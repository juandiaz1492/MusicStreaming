package com.musicstreaming.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.musicstreaming.dto.UserResponse;
import com.musicstreaming.entities.User;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponse toUserResonse(User user); 
    
    List<UserResponse> toListUserResponse(List<User> users); 

}
