package com.example.chuyentrang.controller;

import com.example.chuyentrang.dto.UserRegistrationRequest;
import com.example.chuyentrang.model.Deposit;
import com.example.chuyentrang.model.Role;
import com.example.chuyentrang.model.User;
import com.example.chuyentrang.security.JwtTokenUtil;
import com.example.chuyentrang.service.DepositService;
import com.example.chuyentrang.service.RoleService;
import com.example.chuyentrang.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
@Controller()
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/dang-tin")
    public String dangtin() {
        return "dashboardAccount";
    }

    @GetMapping("/tin-tuc")
    public String tintuc() {
        return "tin_tuc";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @Autowired
    private UserService userService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private RoleService roleService;
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // Xóa token khỏi cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    cookie.setValue(null);  // Xóa giá trị của cookie
                    cookie.setMaxAge(0);    // Đặt thời gian sống của cookie về 0
                    cookie.setPath("/");    // Cấu hình lại path cho cookie
                    response.addCookie(cookie);  // Thêm cookie đã xóa vào response
                }
            }
        }

        // Chuyển hướng người dùng về trang login
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
        boolean authenticated = userService.authenticateUser(username, password);
        if (authenticated) {
            try {
                String role = userService.getUserRole(username); // Lấy vai trò từ UserService
                String token = JwtTokenUtil.createToken(username, role); // Tạo token với vai trò
                model.addAttribute("token", token);

                // Add token to the cookie
                Cookie tokenCookie = new Cookie("token", token);
                tokenCookie.setHttpOnly(true);
                System.out.println("Token: " + token);
                System.out.println("Is token valid: " + JwtTokenUtil.verifyToken(token));
                System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());

                tokenCookie.setPath("/");
                response.addCookie(tokenCookie);
                if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
                    return "redirect:/admin";  // Nếu là admin, chuyển hướng đến trang admin
                } else if ("ROLE_CUSTOMER".equalsIgnoreCase(role)) {
                    return "redirect:/customer";  // Nếu là customer, chuyển hướng đến trang customer
                } else {
                    model.addAttribute("error", "Invalid role");
                    return "login";  // Nếu không phải admin hay customer, quay lại trang login
                }
            } catch (Exception e) {
                model.addAttribute("error", "Error creating token");
                return "login";
            }
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }



    // Endpoint to register a user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRequest) {
        // Fetch the role from the database
        Role role = roleService.findByName(userRequest.getRoleName());
        if (role == null) {
            return ResponseEntity.status(400).body("Role does not exist");
        }

        User user = new User(userRequest.getUsername(), userRequest.getPassword());
        user.getRoles().add(role);
        boolean result = userService.registerUser(user, userRequest.getRoleName());

        if (result) {
            return ResponseEntity.status(201).body("User registered successfully with role: " + userRequest.getRoleName());
        } else {
            return ResponseEntity.status(500).body("Failed to register user");
        }
    }

    @GetMapping("/admin")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));


            if (isAdmin) {
                // Thêm thông tin vào model
                String address = userService.getAddress(username);
                String name = userService.getName(username);
                model.addAttribute("username", username);
                model.addAttribute("role", "ROLE_ADMIN");
                model.addAttribute("address", address); // Thay bằng dữ liệu thực
                model.addAttribute("name", name); // Thay bằng dữ liệu thực
                return "dashboard"; // Trả về view `home`
            }
        }
        return "login"; // Chuyển hướng đến trang lỗi nếu không phải admin
    }


    @GetMapping("/manager-customer")
    public String manager_customer(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));


            if (isAdmin) {
                // Thêm thông tin vào model
                List<User> users = userService.getListUser();
                model.addAttribute("users", users);
//                model.addAttribute("role", "ROLE_ADMIN");
//                model.addAttribute("address", address); // Thay bằng dữ liệu thực
//                model.addAttribute("name", name); // Thay bằng dữ liệu thực
                return "dashboard_manager_customer"; // Trả về view `home`
            }
        }
        return "login"; // Chuyển hướng đến trang lỗi nếu không phải admin
    }

    @GetMapping("/manager-package")
    public String magager_package(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));


            if (isAdmin) {
                // Thêm thông tin vào model
                String address = userService.getAddress(username);
                String name = userService.getName(username);
//                model.addAttribute("username", username);
//                model.addAttribute("role", "ROLE_ADMIN");
//                model.addAttribute("address", address); // Thay bằng dữ liệu thực
//                model.addAttribute("name", name); // Thay bằng dữ liệu thực
                return "dashboard_manager_package"; // Trả về view `home`
            }
        }
        return "login"; // Chuyển hướng đến trang lỗi nếu không phải admin
    }

    @GetMapping("/manager-history")
    public String magager_history(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));


            if (isAdmin) {
                // Thêm thông tin vào model
                String address = userService.getAddress(username);
                String name = userService.getName(username);
//                model.addAttribute("username", username);
//                model.addAttribute("role", "ROLE_ADMIN");
//                model.addAttribute("address", address); // Thay bằng dữ liệu thực
//                model.addAttribute("name", name); // Thay bằng dữ liệu thực
                return "dashboard_manager_history"; // Trả về view `home`
            }
        }
        return "login"; // Chuyển hướng đến trang lỗi nếu không phải admin
    }







    @GetMapping("/customer")
    public String customer(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {
                // Thêm thông tin vào model
                String address = userService.getAddress(username);
                String name = userService.getName(username);
                model.addAttribute("username", username);
                model.addAttribute("role", "ROLE_CUSTOMER");
                model.addAttribute("address", address); // Thay bằng dữ liệu thực
                model.addAttribute("name", name); // Thay bằng dữ liệu thực
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);

                return "dashboard_cus"; // Trả về view `home`
            }
        }
        return "login"; // Chuyển hướng đến trang lỗi nếu không phải admin
    }



    @GetMapping("/customer-history")
    public String customer_history(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {
                // Thêm thông tin vào model
                Long id = userService.getId(username);
                List<Deposit> depositList = depositService.customer_history(id);
                model.addAttribute("depositList", depositList);
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);
                return "dashboard_cus_history"; // Trả về view `home`
            }
        }
        return "login"; // Chuyển hướng đến trang lỗi nếu không phải admin
    }

    @GetMapping("/customer-deposit")
    public String customer_deposit(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {
                // Thêm thông tin vào model
                Long id = userService.getId(username);
                List<Deposit> depositList = depositService.customer_history(id);
                model.addAttribute("depositList", depositList);
                model.addAttribute("id", id);
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);
                return "dashboard_cus_deposit"; // Trả về view `home`
            }
        }
        return "login"; // Chuyển hướng đến trang lỗi nếu không phải admin
    }





}
