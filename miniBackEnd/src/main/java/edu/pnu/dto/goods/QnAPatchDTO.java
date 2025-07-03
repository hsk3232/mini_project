package edu.pnu.dto.goods;

import edu.pnu.domain.QnA;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QnAPatchDTO {
	private Long qaid;
	
	public static QnAPatchDTO fromEntity(QnA q) {
		return QnAPatchDTO.builder()
				.qaid(q.getQaid())
				.build();
	}
}
