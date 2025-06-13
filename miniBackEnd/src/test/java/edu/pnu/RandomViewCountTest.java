package edu.pnu;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Goods;
import edu.pnu.persistence.GoodsRepository;

@SpringBootTest
public class RandomViewCountTest {

    @Autowired
    private GoodsRepository goodsRepo;

    private final Random random = new Random();

    @Test
    public void viewCountAdder() {
    	 List<Goods> goodsList = goodsRepo.findAll();

         for (Goods g : goodsList) {
             int randomCount = random.nextInt(1000); // 0 ~ 999 사이의 랜덤 숫자
             g.setViewcount(randomCount);
             System.out.println(g.getFullcode() + " → 조회수: " + randomCount);
         }

         goodsRepo.saveAll(goodsList); // 한 번에 저장
    	}
}