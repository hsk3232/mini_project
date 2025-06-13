package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Goods;

public interface GoodsRepository extends JpaRepository<Goods, String> {

    // 인기 상품
    List<Goods> findTop10ByOrderByViewcountDesc();

    // 키워드 기반 추천 상품
    List<Goods> findTop10ByProductNameContainingIgnoreCase(String keyword);

    // 전체 검색
    List<Goods> findByProductNameContainingIgnoreCase(String keyword);
    
    // 상품 분류 검색
    List<Goods> findByMain(String main);
    List<Goods> findByMainAndMid(String main, String mid);
    List<Goods> findByMainAndMidAndDetail(String main, String mid, String detail);
}