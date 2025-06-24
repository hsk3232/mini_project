package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.WishList;
import edu.pnu.dto.goods.WishListDTO;

public interface WishListRepository extends JpaRepository<WishList, Long> {
	List<WishList> findByMemberUsername(String username);
	boolean existsByMember_UsernameAndGoods_ImgnameAndRemain(String username, String imgname, boolean remain);
	boolean existsByMember_UsernameAndGoods_Imgname(String username, String imgname);
	
	WishList findByMember_UsernameAndGoods_Imgname(String username, String imgname);

	List<WishList> existsByGoods_Imgname(String imgname);
	
}
