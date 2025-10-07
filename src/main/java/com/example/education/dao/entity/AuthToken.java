package com.example.education.dao.entity;

import com.example.education.security.UserPrincipal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "auth_token")
public class AuthToken extends BaseEntity {

    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "user_principal")
    private UserPrincipal userPrincipal;

}
