package com.example.chuyentrang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class NhaDatChoThueController {
    @GetMapping("/nha-dat-cho-thue")
    public String nhadatchothue() {
        return "nha_dat_cho_thue";
    }

    @GetMapping("/chi-tiet-nha-dat-cho-thue")
    public String detailnhadatchothue() {
        return "detail/detail_nha_dat_cho_thue";
    }
}
