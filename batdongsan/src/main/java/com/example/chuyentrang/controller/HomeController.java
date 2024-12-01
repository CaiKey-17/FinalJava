package com.example.chuyentrang.controller;

import com.example.chuyentrang.dto.PostLandRequest;
import com.example.chuyentrang.dto.UserRegistrationRequest;
import com.example.chuyentrang.model.*;
import com.example.chuyentrang.model.Package;
import com.example.chuyentrang.repository.AvailableRepository;
import com.example.chuyentrang.repository.UserRepository;
import com.example.chuyentrang.security.JwtTokenUtil;
import com.example.chuyentrang.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Controller()
public class HomeController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/dang-tin")
    public String dangtin() {
        return "dashboardAccount";
    }

    @GetMapping("/tin-tuc")
    public String tintuc(Model model) {


        List<News> news = newsService.listLand();
        List<News> news2 = newsService.listLandTop4();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        Collections.sort(news, new Comparator<News>() {
            @Override
            public int compare(News n1, News n2) {
                if (n1.getPublishDate() == null || n2.getPublishDate() == null) {
                    return 0;
                }
                return n1.getPublishDate().compareTo(n2.getPublishDate());
            }
        });

        Collections.sort(news, (n1, n2) -> {
            if (n1.getPublishDate() == null || n2.getPublishDate() == null) {
                return 0;
            }
            return n2.getPublishDate().compareTo(n1.getPublishDate());
        });


        for (News news1 : news) {
            if (news1.getPublishDate() != null) {
                String formattedPurchase = news1.getPublishDate().format(formatter1);
                news1.setFormattedExpiry(formattedPurchase);
            }
        }

        News latestNews = newsService.getLatestNews();


        if (latestNews != null && latestNews.getPublishDate() != null) {
            String formattedDate = latestNews.getPublishDate().format(formatter2);
            latestNews.setFormattedExpiry(formattedDate); // Giả sử bạn có phương thức setFormattedExpiry
        }

        model.addAttribute("latestNews", latestNews);



        model.addAttribute("news", news);
        model.addAttribute("top4", news2);

        return "tin_tuc";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgot")
    public String forgot() {
        return "forgot";
    }
    @Autowired
    private EmailService emailService;

    @PostMapping("/forgot")
    public String sendResetLink(@RequestBody Map<String, String> requestData, HttpServletResponse response) {
        String email = requestData.get("email");

        if (userService.isEmailRegistered(email)) {
            String newPass = "111";
            userService.updatePassword(email, newPass);
            emailService.sendPasswordResetEmail(email, newPass);
            return "Mã khôi phục đã được gửi vào email của bạn!";
        } else {
            return "Email này chưa được đăng ký!";
        }
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

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }


        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
        boolean authenticated = userService.authenticateUser(username, password);
        if (authenticated) {
            try {
                String role = userService.getUserRole(username);
                String token = JwtTokenUtil.createToken(username, role);
                model.addAttribute("token", token);


                Cookie tokenCookie = new Cookie("token", token);
                tokenCookie.setHttpOnly(true);
                System.out.println("Token: " + token);
                System.out.println("Is token valid: " + JwtTokenUtil.verifyToken(token));
                System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());

                tokenCookie.setPath("/");
                response.addCookie(tokenCookie);
                if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
                    return "redirect:/admin";
                } else if ("ROLE_CUSTOMER".equalsIgnoreCase(role)) {
                    return "redirect:/customer";
                } else {
                    model.addAttribute("error", "Invalid role");
                    return "login";
                }
            } catch (Exception e) {
                model.addAttribute("error", "Error creating token");
                return "login";
            }
        } else {
            model.addAttribute("error", "Không tồn tại tài khoản");
            return "login";
        }
    }




    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRequest) {

        Role role = roleService.findByName(userRequest.getRoleName());
        if (role == null) {
            return ResponseEntity.status(400).body("Role does not exist");
        }

        User user = new User(userRequest.getUsername(), userRequest.getPassword(),userRequest.getName(),userRequest.getAddress(),userRequest.getZoneking(),userRequest.getEmail(),userRequest.getPhone());
        user.getRoles().add(role);

        boolean result = userService.registerUser(user, userRequest.getRoleName());

        if (result) {
            return ResponseEntity.status(201).body("User registered successfully with role: " + userRequest.getRoleName());
        } else {
            return ResponseEntity.status(500).body("Failed to register user");
        }
    }









}
