package com.example.education.dto.request;

import com.example.education.enums.RoleName;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserRequest {
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
