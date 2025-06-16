package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.pnu.domain.Goods;
import edu.pnu.dto.filter.SearchFilterDTO;

public interface GoodsRepository extends JpaRepository<Goods, String>, JpaSpecificationExecutor<Goods>{

    // 인기 상품 (조회수 기준 내림차순 상위 10개)
    List<Goods> findTop10ByOrderByViewcountDesc();

    // 키워드 기반 추천 상품(키워드 기반 추천 상품 (상품명에 키워드 포함된 상위 10개)

    List<Goods> findTop10ByProductNameContainingIgnoreCase(String keyword);

    // 상품명에 키워드 포함된 상품 전체 검색 (대소문자 무시)
    List<Goods> findByProductNameContainingIgnoreCase(String keyword);

    // 상세 설명에서 검색
    // List<Goods> findByDescriptionContaining(String keyword);
   
}