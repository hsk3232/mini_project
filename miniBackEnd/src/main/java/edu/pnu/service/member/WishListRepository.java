package edu.pnu.service.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.WishList;

public interface WishListRepository extends JpaRepository<WishList, Long> {
	List<WishList> findByMemberUsername(String username);
}
