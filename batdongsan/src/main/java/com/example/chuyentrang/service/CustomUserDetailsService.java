package com.example.chuyentrang.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Thay thế bằng dịch vụ thực tế để lấy dữ liệu từ DB
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ví dụ giả lập
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(new BCryptPasswordEncoder().encode("password")) // Mã hóa mật khẩu
                    .roles("ADMIN") // Gán vai trò
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
