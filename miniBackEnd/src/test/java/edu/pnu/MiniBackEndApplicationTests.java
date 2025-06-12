package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Goods;
import edu.pnu.persistence.GoodsRepository;

@SpringBootTest
class MiniBackEndApplicationTests {
	
	@Autowired
	private GoodsRepository goodsRepo;
	
	@Test
	void contextLoads() {
		List<Goods> list = goodsRepo.findAll();
		for(Goods g : list) {
			System.out.println(g);
		}
	}
	

}
