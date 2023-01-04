package com.bkalika.socialnetwork.mappers;

import com.bkalika.socialnetwork.dto.ProfileDto;
import com.bkalika.socialnetwork.dto.SignUpDto;
import com.bkalika.socialnetwork.dto.UserDto;
import com.bkalika.socialnetwork.dto.UserSummaryDto;
import com.bkalika.socialnetwork.entities.User;
import com.bkalika.socialnetwork.entities.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * @author @bkalika
 */
//@Mapper(componentModel = "spring", imports = {UserStatus.class, Period.class, LocalDate.class}) // if we use this classes in expression
@Mapper(componentModel = "spring", imports = {UserStatus.class})
public interface UserMapper {

    @Mapping(target = "status", constant = "CREATED")
//    @Mapping(target = "age", expression = "java(Period(LocalDate.now(), user.getBirthDate()).getYears())")
    @Mapping(target = "age", expression = "java(birthDateToAge(user))", dependsOn = "status")
    UserDto toUserDto(User user);

    default int birthDateToAge(User user) {
        Period period = Period.between(LocalDate.now(), user.getBirthDate());
        return period.getYears();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "login",ignore = true)
    @Mapping(target = "messages",ignore = true)
    @Mapping(target = "friends",ignore = true)
    @Mapping(target = "createdDate",ignore = true)
    void updateUser(@MappingTarget User target, UserDto input);

    UserSummaryDto toUserSummary(User user);
    List<UserSummaryDto> toUserSummaryDtos(List<User> users);

    @Mapping(target = "userDto.id", source = "id")
    @Mapping(target = "userDto.firstName", source = "firstName")
    @Mapping(target = "userDto.lastName", source = "lastName")
    ProfileDto userToProfileDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
