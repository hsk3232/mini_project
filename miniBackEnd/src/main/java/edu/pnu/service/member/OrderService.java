package edu.pnu.service.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.GoodsOption;
import edu.pnu.domain.Member;
import edu.pnu.domain.OrderAddress;
import edu.pnu.domain.OrderItem;
import edu.pnu.domain.OrderList;
import edu.pnu.dto.Orders.OrderAddressDTO;
import edu.pnu.dto.Orders.OrderItemDTO;
import edu.pnu.dto.Orders.OrderListDTO;
import edu.pnu.dto.Orders.OrderRequestDTO;
import edu.pnu.persistence.GoodsOptionRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.persistence.OrderAddressRepository;
import edu.pnu.persistence.OrderListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderListRepository orderListRepo;
    private final GoodsOptionRepository goodsOptionRepo;
    private final OrderAddressRepository orderAddressRepo;
    private final MemberRepository memberRepo;

    // 1. 주문 생성 시스템
    @Transactional
    public OrderList createOrder(String username, OrderRequestDTO requestDTO) {
        Member member = memberRepo.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보 없음: " + username));

        OrderListDTO orderInfo = requestDTO.getOrderInfo();
        OrderAddressDTO addressDTO = requestDTO.getAddress();
        List<OrderItemDTO> itemsDTO = requestDTO.getItems();

        OrderAddress address = getOrCreateAddress(member, addressDTO);
        OrderList order = createOrderList(member, orderInfo, address);
        addItemsAndUpdateStock(order, itemsDTO);

        return orderListRepo.save(order);
    }

    // 2. 주소 생성 / 조회 로직
//    private OrderAddress getOrCreateAddress(Member member, OrderAddressDTO dto) {
//        if (dto.getAddressId() != null) {
//            return orderAddressRepo.findById(dto.getAddressId())
//                .orElseThrow(() -> new IllegalArgumentException("[오류] 주소 없음: " + dto.getAddressId()));
//        }
//        return orderAddressRepo.save(OrderAddress.builder()
//            .member(member)
//            .addressId(dto.getAddressId())
//            .name(dto.getName())
//            .zip(dto.getZip())
//            .address1(dto.getAddress1())
//            .address2(dto.getAddress2())
//            .phone(dto.getPhone())
//            .main(dto.isMain())
//            .build());
//    }
    
    private OrderAddress getOrCreateAddress(Member member, OrderAddressDTO dto) {
        // 기존 주소 사용 (수정/조회)
        if (dto.getAddressId() != null) {
            return orderAddressRepo.findById(dto.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("[오류] 주소 없음: " + dto.getAddressId()));
        }

        // 신규 주소 생성 (addressId는 자동 할당되므로 빼기)
        OrderAddress newAddress = OrderAddress.builder()
            .member(member)
            .name(dto.getName())
            .zip(dto.getZip())
            .address1(dto.getAddress1())
            .address2(dto.getAddress2())
            .phone(dto.getPhone())
            .main(dto.isMain())
            .deleteAddr(false) // 논리삭제 컬럼이라면 기본 false로!
            .build();

        return orderAddressRepo.save(newAddress);
    }

    // 3. 주문서 생성 로직
    private OrderList createOrderList(Member member, OrderListDTO dto, OrderAddress address) {
        return OrderList.builder()
            .member(member)
            .orderstatus(dto.getOrderstatus() != null ? dto.getOrderstatus() : "주문완료")
            .total(dto.getTotal())
            .payment(dto.getPayment())
            .address(address)
            .build();
    }

    // 4. 아이템 생성 + 재고 체크 로직
    private void addItemsAndUpdateStock(OrderList order, List<OrderItemDTO> itemDTOs) {
        for (OrderItemDTO itemDTO : itemDTOs) {
            GoodsOption option = goodsOptionRepo.findById(itemDTO.getOptionid())
                .orElseThrow(() -> new IllegalArgumentException("[오류] 옵션 없음: " + itemDTO.getOptionid()));

            if (option.getStock() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("[오류] 재고 부족: " + itemDTO.getOptionid());
            }

            option.setStock(option.getStock() - itemDTO.getQuantity());

            OrderItem item = OrderItem.builder()
                .orderList(order)
                .goodsOption(option)
                .quantity(itemDTO.getQuantity())
                .price(itemDTO.getPrice())
                .build();

            order.getItems().add(item);
        }
    }
   	

    // ✅ 내 주문 내역 조회
    @Transactional
    public List<OrderRequestDTO> getMyOrders(String username) {
        List<OrderList> orders = orderListRepo.findByMember_Username(username);

        List<OrderRequestDTO> results = new ArrayList<>();

        for (OrderList order : orders) {
            // 1️. 주소 처리
            OrderAddressDTO addressDTO = null;
            if (order.getAddress() != null) {
                addressDTO = OrderAddressDTO.fromEntity(order.getAddress());
            }

            // 2️. 아이템 처리
            List<OrderItemDTO> itemDTOList = new ArrayList<>();
            if (order.getItems() != null && !order.getItems().isEmpty()) {
                for (OrderItem item : order.getItems()) {
                    itemDTOList.add(OrderItemDTO.fromEntity(item));
                }
            }

            // 3️. DTO 생성
            OrderRequestDTO requestDTO = OrderRequestDTO.builder()
                .orderInfo(OrderListDTO.fromEntity(order))
                .address(addressDTO)
                .items(itemDTOList)
                .build();

            results.add(requestDTO);
        }

        return results;
    }
}
