package com.example.chuyentrang.controller;

import com.example.chuyentrang.model.LandForSale;
import com.example.chuyentrang.service.LandForSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller()
public class NhaDatChoThueController {
    @Autowired
    private LandForSaleService landForSaleService;

    @GetMapping("/nha-dat-cho-thue")
    public String nhadatchothue(Model model) {
        List<LandForSale> landForSalesPage = landForSaleService.listLandRent();

        model.addAttribute("landForSales", landForSalesPage);

        return "nha_dat_cho_thue";
    }

    @GetMapping("/chi-tiet-nha-dat-cho-thue")
    public String detailnhadatchothue() {
        return "detail/detail_nha_dat_cho_thue";
    }
}
