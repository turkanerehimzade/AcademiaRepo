package com.example.education.security;

import com.example.education.dao.entity.User;
import com.example.education.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService usersService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersService.findUserByEmail(email);
        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), user.getRole().toString());
    }

}
