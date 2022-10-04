package com.baykov.springeshop.mappers;

import com.baykov.springeshop.dtos.UserDto;
import com.baykov.springeshop.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDto);

    UserDto fromUser(User user);

    List<User> toUsers(List<UserDto> userDtos);

    List<UserDto> fromUsers(List<User> users);
}
