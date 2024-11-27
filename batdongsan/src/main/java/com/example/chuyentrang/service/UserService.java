package com.example.chuyentrang.service;

import com.example.chuyentrang.model.User;
import com.example.chuyentrang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Phương thức đăng nhập xác thực người dùng
    public boolean authenticateUser(String username, String password) {
        // Tìm người dùng trong cơ sở dữ liệu
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true; // Xác thực thành công
        }
        return false; // Tài khoản hoặc mật khẩu không đúng
    }

    // Phương thức đăng ký người dùng
    public boolean registerUser(User user, String roleName) {
        // Kiểm tra xem tên người dùng đã tồn tại chưa
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;  // Tên người dùng đã tồn tại
        }

        // Mã hóa mật khẩu người dùng
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Tìm vai trò tương ứng theo roleName
        // Giả sử bạn đã có logic để gán vai trò cho người dùng
        // user.setRole(roleRepository.findByName(roleName));

        // Lưu người dùng vào cơ sở dữ liệu
        userRepository.save(user);
        return true;
    }


    public String getUserRole(String username) {
        // Tìm người dùng theo username
        User user = userRepository.findByUsername(username);
        if (user != null && !user.getRoles().isEmpty()) {
            // Trả về vai trò đầu tiên (nếu người dùng có nhiều vai trò, cần xử lý thêm)
            return user.getRoles().iterator().next().getRoleName();
        }
        return null; // Không tìm thấy vai trò
    }




    public String getAddress(String username) {
        // Logic lấy email của người dùng
        User user = userRepository.findByUsername(username);
        return user.getAddress();
    }

    public String getName(String username) {
        User user = userRepository.findByUsername(username);
        return user.getName();
    }

}
