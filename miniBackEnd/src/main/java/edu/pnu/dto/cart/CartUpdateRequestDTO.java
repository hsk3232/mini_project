package edu.pnu.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Builder
public class CartUpdateRequestDTO {
	private String optionid;
    private Integer quantityChange;
}


    
