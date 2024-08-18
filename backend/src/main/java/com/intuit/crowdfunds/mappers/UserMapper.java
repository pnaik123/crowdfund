package com.intuit.crowdfunds.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.intuit.crowdfunds.dtos.SignUpDTO;
import com.intuit.crowdfunds.dtos.UserDTO;
import com.intuit.crowdfunds.entites.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDTO signUpDto);

}
