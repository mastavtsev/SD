package com.kpo.springshaurma.mapper;

import com.kpo.springshaurma.api.model.User;
import com.kpo.springshaurma.model.ShaurmaUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userModel2UserDto(ShaurmaUser shaurmaUser);
}