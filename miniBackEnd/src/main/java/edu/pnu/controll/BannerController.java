package edu.pnu.controll;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Banner;
import edu.pnu.service.BannerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/banner")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public List<Banner> getBanners() {
        return bannerService.getAllBanners();
    }
}