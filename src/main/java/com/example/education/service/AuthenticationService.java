package com.example.education.service;

import com.example.education.dao.entity.User;
import com.example.education.dao.repository.UserRepository;
import com.example.education.dto.request.auth.ChangePasswordRequest;
import com.example.education.dto.request.auth.SignInRequest;
import com.example.education.dto.response.user.UserLoginResponse;
import com.example.education.dto.response.user.UserSignInResponse;
import com.example.education.mapper.UserMapper;
import com.example.education.security.UserPrincipal;
import com.example.education.security.jwt.JwtProvider;
import com.example.education.validation.AuthValidation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
    private final AuthValidation authValidation;
    private final UserRepository userRepository;
    @Value("${authentication.jwt.access.expiration-in-ms}")
    private Long JWT_ACCESS_EXPIRATION_IN_MS;
    @Value("${authentication.jwt.refresh.expiration-in-ms}")
    private Long JWT_REFRESH_EXPIRATION_IN_MS;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthTokenService authTokenService;
    private final UserMapper userMapper;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthenticationService(AuthenticationManager authenticationManager, JwtProvider jwtProvider, AuthTokenService authTokenService, AuthValidation authValidation, UserRepository userRepository, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.authTokenService = authTokenService;
        this.authValidation = authValidation;
        this.userRepository = userRepository;
        this.userMapper = userMapper;

    }

    private UserLoginResponse getLoginResponse(UserPrincipal userPrincipal) {
        String accessToken = jwtProvider.generateToken(userPrincipal, JWT_ACCESS_EXPIRATION_IN_MS);
        String refreshToken = jwtProvider.generateToken(userPrincipal, JWT_REFRESH_EXPIRATION_IN_MS);
        authTokenService.saveTokenInfo(userPrincipal, accessToken, refreshToken);
        User user = userRepository.findByEmail(userPrincipal.getUsername()).orElseThrow();
        UserSignInResponse userSignInResponse = userMapper.toSignInResponse(user);
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setUser(userSignInResponse);
        userLoginResponse.setAccessToken(accessToken);
        userLoginResponse.setRefreshToken(refreshToken);
        return userLoginResponse;
    }

    public ResponseEntity<UserLoginResponse> signInAndReturnJWT(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(getLoginResponse(userPrincipal));
    }

    public ResponseEntity<Object> logout(String accessToken) {
        authTokenService.deactiveAccessToken(accessToken);
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<UserLoginResponse> refreshToken(HttpServletRequest authorizationHeader) {

        Authentication authentication = jwtProvider.getAuthentication(authorizationHeader);
        return ResponseEntity.ok(getLoginResponse((UserPrincipal) authentication.getPrincipal()));
    }

    public ResponseEntity<String> changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByEmail(changePasswordRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Old password is incorrect");
        }
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("New password cannot be same as old password");
        }
        authValidation.validateChangePassword(changePasswordRequest);
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }
}