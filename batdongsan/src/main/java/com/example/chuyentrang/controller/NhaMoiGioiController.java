package com.example.chuyentrang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class NhaMoiGioiController {
    @GetMapping("/nha-moi-gioi")
    public String nhamoigioi() {
        return "nha_moi_gioi";
    }

    @GetMapping("/chi-tiet-nha-moi-gioi")
    public String detailnhamoigioi() {
        return "detail/detail_nha_moi_gioi";
    }
}
