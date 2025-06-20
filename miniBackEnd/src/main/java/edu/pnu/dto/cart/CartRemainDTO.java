package edu.pnu.dto.cart;

import java.util.List;

import edu.pnu.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRemainDTO {
	String optionid;
	boolean remain; 
	
}
