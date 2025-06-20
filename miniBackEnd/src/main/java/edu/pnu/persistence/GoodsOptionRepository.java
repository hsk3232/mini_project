package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.GoodsOption;

public interface GoodsOptionRepository extends JpaRepository<GoodsOption, String>{

}
