package edu.pnu.dto.Orders;

import edu.pnu.domain.OrderAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class OrderAddressDTO {
    private Long id;
    private String zip;
    private String address1;
    private String address2;
    private String phone;

    public static OrderAddressDTO fromEntity(OrderAddress addr) {
        return OrderAddressDTO.builder()
            .id(addr.getAddressId())
            .zip(addr.getZip())
            .address1(addr.getAddress1())
            .address2(addr.getAddress2())
            .phone(addr.getPhone())
            .build();
    }
}
