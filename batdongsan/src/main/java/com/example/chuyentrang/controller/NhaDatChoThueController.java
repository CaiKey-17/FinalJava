package com.example.chuyentrang.controller;

import com.example.chuyentrang.model.LandForSale;
import com.example.chuyentrang.model.User;
import com.example.chuyentrang.service.LandForSaleService;
import com.example.chuyentrang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller()
public class NhaDatChoThueController {
    @Autowired
    private LandForSaleService landForSaleService;
    @Autowired
    private UserService userService;

    @RequestMapping("/nha-dat-cho-thue")
    public String nhadatchothue(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "CategoryIdsAsString", required = false) String categoryIdsAsString,
            @RequestParam(value = "PriceAsString", required = false) String priceAsString,
            @RequestParam(value = "AreaAsString", required = false) String areaAsString,
            @RequestParam(value = "RoomNumersAsString", required = false) String roomNumersAsString,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "district", required = false) String district,
            Model model) {

        if (categoryIdsAsString != null && categoryIdsAsString.contains(",")) {
            categoryIdsAsString = categoryIdsAsString.split(",")[0];
        }

        if (priceAsString != null && priceAsString.contains(",")) {
            priceAsString = priceAsString.split(",")[0];
        }

        if (areaAsString != null && areaAsString.contains(",")) {
            areaAsString = areaAsString.split(",")[0];
        }

        System.out.println("Page: " + page);
        System.out.println("CategoryIdsAsString: " + categoryIdsAsString);
        System.out.println("PriceAsString: " + priceAsString);
        System.out.println("AreaAsString: " + areaAsString);
        System.out.println("RoomNumersAsString: " + roomNumersAsString);

        int minPrice = 0, minArea = 0;
        int maxPrice = Integer.MAX_VALUE, maxArea = Integer.MAX_VALUE;

        if (categoryIdsAsString == null) {
            categoryIdsAsString = "";
        }
        int numberRoom = 0;
        if (roomNumersAsString != null && !roomNumersAsString.isEmpty()) {
            try {
                numberRoom = Integer.parseInt(roomNumersAsString);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Giá trị 'RoomNumersAsString' không hợp lệ");
            }
        }

        if(areaAsString != null) {
            switch (areaAsString) {
                case "1":
                    minArea = 0;
                    maxArea = 30;
                    break;
                case "2":
                    minArea = 30;
                    maxArea = 50;
                    break;
                case "3":
                    minArea = 50;
                    maxArea = 80;
                    break;
                case "4":
                    minArea = 80;
                    maxArea = 100;
                    break;
                case "5":
                    minArea = 100;
                    maxArea = 150;
                    break;
                case "6":
                    minArea = 150;
                    maxArea = 200;
                    break;
                case "7":
                    minArea = 200;
                    maxArea = 250;
                    break;
                case "8":
                    minArea = 250;
                    maxArea = 300;
                    break;
                case "9":
                    minArea = 300;
                    maxArea = 500;
                    break;
                case "10":
                    minArea = 500;
                    maxArea = Integer.MAX_VALUE;
                    break;
                default:
                    minArea = 0;
                    maxArea = Integer.MAX_VALUE;
                    break;
            }
        }

        System.out.println("minArea: " + minArea);
        System.out.println("maxArea: " + maxArea);

        if (priceAsString != null) {
            switch (priceAsString) {
                case "1":
                    minPrice = 0;
                    maxPrice = 1000000;
                    break;
                case "2":
                    minPrice = 1000000;
                    maxPrice = 3000000;
                    break;
                case "3":
                    minPrice = 3000000;
                    maxPrice = 5000000;
                    break;
                case "4":
                    minPrice = 5000000;
                    maxPrice = 10000000;
                    break;
                case "5":
                    minPrice = 10000000;
                    maxPrice = 40000000;
                    break;
                case "6":
                    minPrice = 40000000;
                    maxPrice = 70000000;
                    break;
                case "7":
                    minPrice = 70000000;
                    maxPrice = 100000000;
                    break;
                case "8":
                    minPrice = 100000000;
                    maxPrice = Integer.MAX_VALUE;
                    break;
                case "0":
                    minPrice = 0;
                    maxPrice = 0;
                    break;
                default:
                    minPrice = 0;
                    maxPrice = Integer.MAX_VALUE;
                    break;
            }
        }

        Page<LandForSale> landForSalesPage;
        if (categoryIdsAsString.isEmpty() && priceAsString == null && areaAsString == null && roomNumersAsString == null && province == null && district == null){
            landForSalesPage = landForSaleService.listLandRent(page);
        } else {
            landForSalesPage = landForSaleService.listLandRent(page, categoryIdsAsString, minPrice, maxPrice, minArea, maxArea, numberRoom, province, district);
        }

        System.out.println("Danh sách LandForSales:");
        for (LandForSale land : landForSalesPage.getContent()) {
            System.out.println("Title: " + land.getName() + ", Price: " + land.getPrice());
        }

        System.out.println("Total items: " + landForSalesPage.getTotalElements());

        model.addAttribute("landForSales", landForSalesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", landForSalesPage.getTotalPages());
        model.addAttribute("totalItems", landForSalesPage.getTotalElements());

        model.addAttribute("categoryIdsAsString", categoryIdsAsString);
        model.addAttribute("priceAsString", priceAsString);
        model.addAttribute("areaAsString", areaAsString);
        model.addAttribute("roomNumersAsString", roomNumersAsString);
        model.addAttribute("province", province);
        model.addAttribute("district", district);

        List<LandForSale> HCM = landForSaleService.getLandForSaleByProvinceSold("Hồ Chí Minh");
        List<LandForSale> HN = landForSaleService.getLandForSaleByProvinceSold("Hà Nội");
        List<LandForSale> BD = landForSaleService.getLandForSaleByProvinceSold("Bình Dương");
        List<LandForSale> DN = landForSaleService.getLandForSaleByProvinceSold("Đà Nắng");
        List<LandForSale> DongNai = landForSaleService.getLandForSaleByProvinceSold("Đồng Nai");

        List<LandForSale> HCM1 = landForSaleService.getLandForSaleByProvinceRent("Hồ Chí Minh");
        List<LandForSale> HN1 = landForSaleService.getLandForSaleByProvinceRent("Hà Nội");
        List<LandForSale> BD1 = landForSaleService.getLandForSaleByProvinceRent("Bình Dương");
        List<LandForSale> DN1 = landForSaleService.getLandForSaleByProvinceRent("Đà Nắng");
        List<LandForSale> DongNai1 = landForSaleService.getLandForSaleByProvinceRent("Đồng Nai");


        model.addAttribute("HCM", HCM);
        model.addAttribute("HN", HN);
        model.addAttribute("BD", BD);
        model.addAttribute("DN", DN);
        model.addAttribute("DongNai", DongNai);

        model.addAttribute("HCM1", HCM1);
        model.addAttribute("HN1", HN1);
        model.addAttribute("BD1", BD1);
        model.addAttribute("DN1", DN1);
        model.addAttribute("DongNai1", DongNai1);

        System.out.println("HAHAHA"+province);

        return "nha_dat_cho_thue";
    }

    @GetMapping("/chi-tiet-nha-dat-cho-thue/{id}")
    public String detailnhadatchothue(@PathVariable("id") int id, Model model) {
        LandForSale landForSales = landForSaleService.findById(id);
        System.out.println(landForSales.toString());
        Double latitude  = landForSales.getLatitude();
        Double longitude  = landForSales.getLongitude();

        List<LandForSale> landForSaleList = landForSaleService.listLandRent();
        List<User> users = userService.getCustomers();
        for(User u : users){
            System.out.println(u.toString());
        }
        model.addAttribute("users", users);


        model.addAttribute("landForSaleList", landForSaleList);


        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        model.addAttribute("description", landForSales.getDescription());
        model.addAttribute("landForSale", landForSales);
        return "detail/detail_nha_dat_cho_thue";
    }
}
