package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Goods;

public interface GoodsRepository extends JpaRepository<Goods, String> {

}
