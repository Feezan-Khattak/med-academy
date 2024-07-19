package com.med.acadmys.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.med.acadmys.utils.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UserEntityDto extends BaseEntityDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Boolean isEnabled;
}