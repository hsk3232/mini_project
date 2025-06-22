package edu.pnu.dto.Orders;

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
    private boolean isMain;
    private boolean delete;

    public static OrderAddressDTO fromEntity(OrderAddress addr) {
        return OrderAddressDTO.builder()
            .addressId(addr.getAddressId())
            .username(addr.getMember().getUsername())
            .name(addr.getName())
            .zip(addr.getZip())
            .address1(addr.getAddress1())
            .address2(addr.getAddress2())
            .phone(addr.getPhone())
            .isMain(addr.isMain())
            .build();
    }
    
}
