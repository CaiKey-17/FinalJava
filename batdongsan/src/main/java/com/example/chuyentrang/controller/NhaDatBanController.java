package com.example.chuyentrang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class NhaDatBanController {
    @GetMapping("/nha-dat-ban")
    public String nhadatban() {
        return "nha_dat_ban";
    }

    @GetMapping("/chi-tiet-nha-dat-ban")
    public String chitietnhadatban() {
        return "detail/detail_nha_dat_ban";
    }

}
