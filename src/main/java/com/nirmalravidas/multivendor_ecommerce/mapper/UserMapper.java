package com.nirmalravidas.multivendor_ecommerce.mapper;

import com.nirmalravidas.multivendor_ecommerce.dto.UserDto;
import com.nirmalravidas.multivendor_ecommerce.model.User;

public class UserMapper {

    public static UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
