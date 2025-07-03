package edu.pnu.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.QnA;

public interface QnARepository extends JpaRepository<QnA, Long> {
	List<QnA> findAllByMember_Username(String username);
	QnA existsByMember_UsernameAndQaid(String username, Long qaid);

	
	Optional<QnA> findByMember_UsernameAndQaid(String username, Long qaid);
	
	List<QnA> findAllByGoods_ImgnameAndRemainTrue(String imgname);
}
