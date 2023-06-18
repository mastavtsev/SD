package com.kpo.springshaurma.mapper;

import com.kpo.springshaurma.api.model.User;
import com.kpo.springshaurma.model.ShaurmaUser;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-18T19:45:31+0300",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userModel2UserDto(ShaurmaUser shaurmaUser) {
        if ( shaurmaUser == null ) {
            return null;
        }

        User user = new User();

        user.setFullName( shaurmaUser.getFullName() );
        user.setUsername( shaurmaUser.getUsername() );
        user.setEmail( shaurmaUser.getEmail() );
        user.setPhone( shaurmaUser.getPhone() );
        user.setAccessToken( shaurmaUser.getAccessToken() );

        return user;
    }
}
