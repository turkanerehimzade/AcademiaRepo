package com.example.education.dto.response.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserSignInResponse {
    private String name;
    private String email;
}
