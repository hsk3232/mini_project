package edu.pnu.dto.orders;

import edu.pnu.domain.Member;
import edu.pnu.domain.OrderAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderAddressDTO {
	private Long addressId;
	private String username;
	private String name;
	private String zip;
	private String address1;
	private String address2;
	private String phone;
	private boolean main;
	private boolean deleteAddr;

	public static OrderAddressDTO fromEntity(OrderAddress addr) {
		return OrderAddressDTO.builder().addressId(addr.getAddressId()).username(addr.getMember().getUsername())
				.name(addr.getName()).zip(addr.getZip()).address1(addr.getAddress1()).address2(addr.getAddress2())
				.phone(addr.getPhone()).main(addr.isMain()).deleteAddr(addr.isDeleteAddr()).build();
	}

	public static OrderAddress toEntity(OrderAddressDTO dto, Member member) {
	    return OrderAddress.builder()
	            .member(member)
	            .name(dto.getName())
	            .zip(dto.getZip())
	            .address1(dto.getAddress1())
	            .address2(dto.getAddress2())
	            .phone(dto.getPhone())
	            .main(dto.isMain())
	            .deleteAddr(false)
	            .build();
	}

}
