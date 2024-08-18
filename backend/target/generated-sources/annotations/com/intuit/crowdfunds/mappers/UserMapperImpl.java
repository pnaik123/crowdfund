package com.intuit.crowdfunds.mappers;

import com.intuit.crowdfunds.dtos.SignUpDTO;
import com.intuit.crowdfunds.dtos.UserDTO;
import com.intuit.crowdfunds.entites.Role;
import com.intuit.crowdfunds.entites.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-12T19:46:58+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.firstName( user.getFirstName() );
        userDTO.lastName( user.getLastName() );
        userDTO.login( user.getLogin() );
        if ( user.getRole() != null ) {
            userDTO.role( user.getRole().name() );
        }

        return userDTO.build();
    }

    @Override
    public User signUpToUser(SignUpDTO signUpDto) {
        if ( signUpDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( signUpDto.firstName() );
        user.lastName( signUpDto.lastName() );
        user.login( signUpDto.login() );
        user.email( signUpDto.email() );
        if ( signUpDto.role() != null ) {
            user.role( Enum.valueOf( Role.class, signUpDto.role() ) );
        }

        return user.build();
    }
}
