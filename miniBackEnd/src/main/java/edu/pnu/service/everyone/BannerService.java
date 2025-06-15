package edu.pnu.service.everyone;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Banner;
import edu.pnu.persistence.BannerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepo;

    public List<Banner> getAllBanners() {
        return bannerRepo.findAllByOrderBySortOrderAsc();
    }
}