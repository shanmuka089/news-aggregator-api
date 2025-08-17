package com.example.new_aggregator.mapper;

import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.models.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserDtoMapper
{

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);
    
    @Mapping(target = "password", ignore = true)
    UserDto toDto(UserEntity userEntity);
    
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserEntity toEntity(UserDto userDto);
    
    
}
