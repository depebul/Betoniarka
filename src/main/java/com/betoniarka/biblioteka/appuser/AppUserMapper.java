package com.betoniarka.biblioteka.appuser;

import com.betoniarka.biblioteka.appuser.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppUserMapper {

    AppUser toEntity(AppUserCreateDto source);

    @Mapping(target = "role", constant = "APP_USER")
    AppUser toEntity(AppUserRegisterDto source);

    @Mapping(target = "password", source = "encryptedPassword")
    void update(AppUserAdminUpdateDto source, @MappingTarget AppUser target, String encryptedPassword);

    @Mapping(target = "password", ignore = true)
    void update(AppUserAdminUpdateDto source, @MappingTarget AppUser target);

    @Mapping(target = "password", source = "encryptedPassword")
    void update(AppUserUpdateDto source, @MappingTarget AppUser target, String encryptedPassword);

    @Mapping(target = "password", ignore = true)
    void update(AppUserUpdateDto source, @MappingTarget AppUser target);

    AppUserResponseDto toDto(AppUser source);

    @Mapping(target = "role", constant = "APP_USER")
    AppUserAdminUpdateDto toAdminUpdateDto(AppUserUpdateDto source);

    @Mapping(target = "role", constant = "APP_USER")
    AppUserCreateDto toCreateDto(AppUserRegisterDto source);
}
