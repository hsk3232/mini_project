package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.GoodsOption;

public interface GoodsOptionRepository extends JpaRepository<GoodsOption, String>{
	Optional<GoodsOption> findById(String optionId);
}
