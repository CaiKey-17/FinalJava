package com.example.chuyentrang.controller;

import com.example.chuyentrang.dto.PostLandRequest;
import com.example.chuyentrang.dto.UserRegistrationRequest;
import com.example.chuyentrang.model.*;
import com.example.chuyentrang.model.Package;
import com.example.chuyentrang.repository.AvailableRepository;
import com.example.chuyentrang.security.JwtTokenUtil;
import com.example.chuyentrang.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private PackageService packageService;

    @PostMapping("/manager-package/add")
    public String addPackage(@RequestBody Package packagee) {
        packageService.createPackage(packagee);
        return "redirect:/manager-package";
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
                List<Package> packageList = packageService.getAllPackage();

                // Định dạng ngày giờ trước khi thêm vào model
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                // Duyệt qua danh sách gói tin và chuyển expiry thành chuỗi
                for (Package packagee : packageList) {
                    if (packagee.getExpiry() != null) {
                        String formattedExpiry = packagee.getExpiry().format(formatter);
                        packagee.setFormattedExpiry(formattedExpiry); // Gán chuỗi đã định dạng vào trường formattedExpiry
                    }
                }

                // Thêm danh sách gói tin vào model
                model.addAttribute("packageList", packageList);
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



    @GetMapping("/customer-package")
    public String customer_package(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {
                // Thêm thông tin vào model

                List<Package> packageList = packageService.getAllPackage();

                // Định dạng ngày giờ trước khi thêm vào model
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                // Duyệt qua danh sách gói tin và chuyển expiry thành chuỗi
                for (Package packagee : packageList) {
                    if (packagee.getExpiry() != null) {
                        String formattedExpiry = packagee.getExpiry().format(formatter);
                        packagee.setFormattedExpiry(formattedExpiry); // Gán chuỗi đã định dạng vào trường formattedExpiry
                    }
                }
                User u = userService.findByUser(username);


                List<Available> availableList = availableRepository.findByBroker(u);
                if (availableList.isEmpty()) {
                    System.out.println("Không có availableList nào.");
                }
                model.addAttribute("availableList", availableList);


                // Định dạng ngày giờ trước khi thêm vào model
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                // Duyệt qua danh sách gói tin và chuyển expiry thành chuỗi
                for (Available available : availableList) {
                    // Định dạng purchaseDate nếu nó không null
                    if (available.getPurchaseDate() != null) {
                        String formattedPurchase = available.getPurchaseDate().format(formatter1);
                        available.setFormattedPurchase(formattedPurchase);  // Gán chuỗi đã định dạng vào trường formattedPurchase
                    }

                    // Định dạng expirationDate nếu nó không null
                    if (available.getExpirationDate() != null) {
                        String formattedExpiry = available.getExpirationDate().format(formatter1);
                        available.setFormattedExpiry(formattedExpiry);  // Gán chuỗi đã định dạng vào trường formattedExpiry
                    }
                }



                model.addAttribute("availableList", availableList);



                // Thêm danh sách gói tin vào model
                model.addAttribute("packageList", packageList);

                Double money = userService.getMoney(username);
                model.addAttribute("money", money);
                if (model.containsAttribute("error")) {
                    System.out.println("Không đủ tiền");
                    model.addAttribute("error", model.getAttribute("error"));
                }
                return "dashboard_cus_package"; // Trả về view `home`
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
    @Autowired
    private LandForSaleService landForSaleService;
    @PostMapping("/post")
    public ResponseEntity<String> postLand(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "price") Double price,
            @RequestParam(value = "area") Double area,
            @RequestParam(value = "province") String province,
            @RequestParam(value = "district") String district,
            @RequestParam(value = "ward") String ward,
            @RequestParam(value = "interior") String interior,
            @RequestParam(value = "numberOfToilets") Integer numberOfToilets,
            @RequestParam(value = "numberOfBedRooms") Integer numberOfBedRooms,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "datePosted") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datePosted,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "legal") String legal,
            @RequestParam(value = "propertyType") String propertyType,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "availableId") Integer availableId,
            @RequestParam(value = "imageLinks", required = false) List<MultipartFile> imageLinks) { // Sửa thành List<MultipartFile>

        try {
            List<String> imageUrls = new ArrayList<>();
            if (imageLinks != null && !imageLinks.isEmpty()) {
                for (MultipartFile image : imageLinks) {
                    String imageUrl = saveImage(image); // Hàm tự định nghĩa để lưu ảnh
                    imageUrls.add(imageUrl);
                }
            }

            // Tạo đối tượng LandForSale từ dữ liệu nhận được
            LandForSale landForSale = new LandForSale();
            landForSale.setName(name);
            landForSale.setPrice(price);
            landForSale.setArea(area);
            landForSale.setProvince(province);
            landForSale.setDistrict(district);
            landForSale.setWard(ward);
            landForSale.setInterior(interior);
            landForSale.setNumberOfToilets(numberOfToilets);
            landForSale.setNumberOfBedRooms(numberOfBedRooms);
            landForSale.setDescription(description);
            landForSale.setDatePosted(datePosted);
            landForSale.setType(type);
            landForSale.setLegal(legal);
            landForSale.setPropertyType(propertyType);

            // Gọi service để lưu dữ liệu
            landForSaleService.postLandForSale(landForSale, userId, availableId, imageUrls);

            return ResponseEntity.ok("Post successful!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        String uploadDir = "images/"; // Thay đổi thành "images"
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        return "/images/" + fileName; // Trả về đường dẫn ảnh
    }






    @Autowired
    private AvailableRepository availableRepository;



    @PostMapping("/customer-package/purchase")
    public String purchasePackage(@RequestParam("selectedPackage") Integer selectedPackage, Model model,RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {
                User user = userService.findByUser(username);
                List<Package> packages = packageService.getAllPackage();
                Package selected = packageService.getPackageById(selectedPackage).orElseThrow(() -> new IllegalArgumentException("Invalid package Id"));
                Double totalAmount = selected.getPrice();


                Double money = userService.getMoney(username);
                model.addAttribute("money", money);
                if (money < totalAmount) {
                    redirectAttributes.addFlashAttribute("error", "Không đủ tiền để mua các gói tin.");
                    return "redirect:/customer-package";  // Chuyển hướng đến trang customer
                }
                userService.minusMoney(username,totalAmount);
                System.out.println("Yes");

                Available available = new Available();
                available.setStatusPayment("Paid");
                available.setPurchaseDate(LocalDateTime.now());
                available.setQuantityAvailable(selected.getQuantity()); // Lấy số lượng gói tin
                available.setTotal(selected.getPrice());
                available.setExpirationDate(LocalDateTime.now().plusDays(30)); // Hạn sử dụng là 30 ngày sau khi mua
                available.setBroker(user); // Gán người dùng mua
                available.setPackagee(selected); // Gán gói tin

                    // Lưu vào database
                availableRepository.save(available);

                return "redirect:/customer-package";  // Nếu là customer, chuyển hướng đến trang customer


            }
        }
        return "login"; // Chuyển hướng đến trang lỗi nếu không phải admin
    }

    @GetMapping("/customer-dangtin")
    public String dangtin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {
                // Thêm thông tin vào model
                User user = userService.findByUser(username);
                Long userId = user.getId();
                List<Available> availableList = availableRepository.findByBrokerAndQuantityAvailableGreaterThan(user,0);

                // Kiểm tra nếu không có available nào
                if (availableList.isEmpty()) {
                    System.out.println("Không có availableList nào.");
                }

                // Thêm danh sách available vào model
                model.addAttribute("availableList", availableList);
                model.addAttribute("userId", userId);


                return "dashboard_cus_dangtin";
            }
        }
        return "login"; // Chuyển hướng đến trang lỗi nếu không phải admin
    }


    @GetMapping("/customer-history-list")
    public String customer_history_list(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên đăng nhập từ authentication
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {
                // Thêm thông tin vào model
                Long id = userService.getId(username);

                List<LandForSale> landForSales = landForSaleService.listLandByBroker(id);
                model.addAttribute("landForSales", landForSales);
                model.addAttribute("id", id);
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);


                return "dashboard_cus_list";
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
