package com.example.chuyentrang.controller;

import com.example.chuyentrang.model.*;
import com.example.chuyentrang.model.Package;
import com.example.chuyentrang.repository.AvailableRepository;
import com.example.chuyentrang.repository.UserRepository;
import com.example.chuyentrang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller()
public class AdminController {

    @Autowired
    private PackageService packageService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private AvailableService availableService;


    @Autowired
    private UserService userService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  ImageLandService imageLandService;


    @Autowired
    private  ImageNewsService imageNewsService;

    @Autowired
    private AvailableRepository availableRepository;

    @Autowired
    private LandForSaleService landForSaleService;


    @GetMapping("/admin")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));


            if (isAdmin) {

                String address = userService.getAddress(username);
                String name = userService.getName(username);
                model.addAttribute("name", name);

                model.addAttribute("username", username);
                model.addAttribute("role", "ROLE_ADMIN");
                model.addAttribute("address", address);
                return "dashboard";
            }
        }
        return "redirect:/login";

    }


    @GetMapping("/manager-customer")
    public String manager_customer(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));


            if (isAdmin) {
                String name = userService.getName(username);
                model.addAttribute("name", name);
                List<User> users = userService.getListUser();
                model.addAttribute("users", users);
                return "dashboard_manager_customer";
            }
        }
        return "redirect:/login";

    }
    @GetMapping("/manager-package")
    public String magager_package(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));


            if (isAdmin) {
                String name = userService.getName(username);
                model.addAttribute("name", name);
                List<com.example.chuyentrang.model.Package> packageList = packageService.getAllPackage();


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


                for (Package packagee : packageList) {
                    if (packagee.getExpiry() != null) {
                        String formattedExpiry = packagee.getExpiry().format(formatter);
                        packagee.setFormattedExpiry(formattedExpiry);
                    }
                }


                model.addAttribute("packageList", packageList);

                return "dashboard_manager_package";
            }
        }
        return "redirect:/login";

    }



    @GetMapping("/admin/delete/{id}")
    public String deleteLand(@PathVariable int id) {
        LandForSale land = landForSaleService.findById(id);
        imageLandService.deleteImageLandsByLandForSale(land);
        landForSaleService.deleteLandById(id);
        return "redirect:/manager-list";
    }

    @GetMapping("/admin/delete/news/{id}")
    public String deleteNews(@PathVariable int id) {
        News land = newsService.findById(id);
        imageNewsService.deleteImageLandsByNews(land);
        newsService.deleteNews(id);
        return "redirect:/manager-list";
    }



    @GetMapping("/manager-list")
    public String manager_list(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));


            if (isAdmin) {

                Long id = userService.getId(username);
                String name = userService.getName(username);
                model.addAttribute("name", name);


                List<LandForSale> landForSales = landForSaleService.listLandSold();
                List<LandForSale> landForSales1 = landForSaleService.listLandRent();
                List<News> news = newsService.listLand();

                System.out.println(news.size());



                model.addAttribute("news", news);

                model.addAttribute("landForSales", landForSales);
                model.addAttribute("landForRents", landForSales1);
                model.addAttribute("id", id);
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);


                return "dashboard_manager_list";
            }
        }
        return "redirect:/login";

    }
    @GetMapping("/manager-history")
    public String magager_history(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));


            if (isAdmin) {

                String address = userService.getAddress(username);
                String name = userService.getName(username);
                model.addAttribute("name", name);


                List<Available> availableList = availableService.findAllAvailable();
                model.addAttribute("availableList", availableList);



                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


                for (Available available : availableList) {

                    if (available.getPurchaseDate() != null) {
                        String formattedPurchase = available.getPurchaseDate().format(formatter1);
                        available.setFormattedPurchase(formattedPurchase);
                    }


                    if (available.getExpirationDate() != null) {
                        String formattedExpiry = available.getExpirationDate().format(formatter1);
                        available.setFormattedExpiry(formattedExpiry);
                    }
                }



                model.addAttribute("availableList", availableList);


                return "dashboard_manager_history";
            }
        }
        return "redirect:/login";
    }
    @PostMapping("/manager-package/add")
    public String addPackage(@RequestBody Package packagee) {
        packageService.createPackage(packagee);
        return "redirect:/manager-package";
    }
    @GetMapping("/admin-change")
    public String admin_change(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")  );


            if (isAdmin) {

                Long id = userService.getId(username);
                String name = userService.getName(username);
                model.addAttribute("name", name);
                model.addAttribute("id", id);


                return "dashboard_admin_change";
            }
        }
        return "redirect:/login";
    }



    @PostMapping("/admin-change-password")
    public String changePassword(
            @RequestParam("id") Long userId,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {


        User user = userService.findById(userId);
        System.out.println(userId);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "dashboard_admin_change";
        }


        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng.");
            return "dashboard_admin_change";
        }


        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới không khớp.");
            return "dashboard_admin_change";
        }


        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        model.addAttribute("message", "Đổi mật khẩu thành công.");
        return "redirect:/admin-change";

    }

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/package-statistics")
    public String getPackageStatistics(Model model) {
        List<Map<String, Object>> statistics = statisticsService.getPackageStatistics();
        model.addAttribute("statistics", statistics);
        return "dashboard_manager_static";
    }

    @GetMapping("/package-statistics-data")
    @ResponseBody
    public List<Map<String, Object>> getPackageStatisticsData() {
        return statisticsService.getPackageStatistics();
    }



}
