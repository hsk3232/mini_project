package edu.pnu.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import edu.pnu.domain.Goods;

public class CategorySpecification {
	
    public static Specification<Goods> hasMain(String main) {
        return (root, query, cb) -> cb.equal(root.get("main"), main);
    }

    public static Specification<Goods> hasMidIn(List<String> mid) {
        return (root, query, cb) -> root.get("mid").in(mid);
    }

    public static Specification<Goods> hasDetailIn(List<String> detail) {
        return (root, query, cb) -> root.get("detail").in(detail);
    }

    public static Specification<Goods> hasGender(List<String> gender) {
        return (root, query, cb) -> root.get("gender").in(gender);
    }
    public static Specification<Goods> hasColorIn(List<String> color) {
    	return (root, query, cb) -> root.get("color").in(color);
    }
  
    public static Specification<Goods> hasPrintIn(List<String> print) {
        return (root, query, cb) -> root.get("print").in(print);
    }

    public static Specification<Goods> hasPriceBetween(Integer minPrice, Integer maxPrice) {
        return (root, query, cb) -> {
            if (minPrice != null && maxPrice != null) {
                return cb.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            } else {
                return cb.conjunction(); // 아무 조건도 걸지 않음
            }
        };
    }
    
    public static Specification<Goods> hasProductName(String keyword) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("productName")), "%" + keyword.toLowerCase() + "%");
    }


    public static Specification<Goods> filterBy(
    	    String main,
    	    List<String> mid,
    	    List<String> detail,
    	    List<String> gender,
    	    List<String> color,
    	    List<String> print,
            Integer minPrice,
            Integer maxPrice,
            String keyword
    	) {
    	    Specification<Goods> spec = (root, query, cb) -> cb.conjunction(); // 빈 조건에서 시작
    	
    	    if (main != null && !main.isEmpty()) spec = spec.and(hasMain(main));
    	    if (mid != null && !mid.isEmpty()) spec = spec.and(hasMidIn(mid));
    	    if (detail != null && !detail.isEmpty()) spec = spec.and(hasDetailIn(detail));
    	    if (gender != null && !gender.isEmpty()) spec = spec.and(hasGender(gender));
    	    if (color != null && !color.isEmpty()) spec = spec.and(hasColorIn(color));
    	    if (print != null && !print.isEmpty()) spec = spec.and(hasPrintIn(print));
    	    if (minPrice != null || maxPrice != null) spec = spec.and(hasPriceBetween(minPrice, maxPrice));
    	    if (keyword != null && !keyword.isBlank()) spec = spec.and(hasProductName(keyword)); // 키워드 검색 조건 추가

    	    return spec;
    	}
}