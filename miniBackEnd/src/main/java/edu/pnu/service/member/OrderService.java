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
import edu.pnu.dto.Orders.OrderInfoDTO;
import edu.pnu.dto.Orders.OrderItemDTO;
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
    @Transactional //Lazy 로딩 데이터 접근이 트랜잭션 내에서 안전하게 처리됨
    public OrderList createOrder(String username, OrderRequestDTO requestDTO) {
        Member member = memberRepo.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("[오류] 회원 정보 없음: " + username));

        OrderInfoDTO orderInfo = requestDTO.getOrderInfo();
        OrderAddressDTO addressDTO = requestDTO.getAddress();
        List<OrderItemDTO> itemsDTO = requestDTO.getItems();

        OrderAddress address = getAddressAndSearch(addressDTO, member);
        OrderList order = createOrderList(member, orderInfo, address);
        addItemsAndUpdateStock(order, itemsDTO);

        return orderListRepo.save(order);
    }

    
    // 2. 주소 생성 / 조회 로직
    private OrderAddress getAddressAndSearch(OrderAddressDTO dto, Member member) {
        // 기존 주소 사용 (수정/조회)
        if (dto.getAddressId() != null) {
            return orderAddressRepo.findById(dto.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("[오류] 주소 없음: " + dto.getAddressId()));
        }

        // 신규 주소 생성 (addressId는 자동 할당되므로 빼기)
        OrderAddress newAddress = OrderAddressDTO.toEntity(dto, member);

        return orderAddressRepo.save(newAddress);
    }

    // 3. 주문서 생성 로직
    private OrderList createOrderList(Member m, OrderInfoDTO o, OrderAddress a) {
    	
    	OrderList newOrderList = OrderInfoDTO.toEntity(m, o, a);
    	
        return orderListRepo.save(newOrderList);
    }

    // 4. 아이템 생성 + 재고 체크 로직
    private void addItemsAndUpdateStock(OrderList order, List<OrderItemDTO> itemDTOs) {
        for (OrderItemDTO itemDTO : itemDTOs) {
            GoodsOption option = goodsOptionRepo.findById(itemDTO.getOptionid())
                .orElseThrow(() -> new IllegalArgumentException("[오류] 옵션 없음: " + itemDTO.getOptionid()));

            if (option.getStock() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("[오류] 재고 부족: " + itemDTO.getOptionid());
            }

            // 🔄 기존 수동 재고 감소 → 엔티티 메서드로 변경
            option.decreaseStock(itemDTO.getQuantity());

            // 🔄 기존 Builder → toEntity 사용
            OrderItem item = OrderItemDTO.toEntity(itemDTO, order, option);

            // 🔄 직접 add → addItem 메서드 활용
            order.addItem(item);
        }
    }
   	

    // 내 주문 내역 조회
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
                .orderInfo(OrderInfoDTO.fromEntity(order))
                .address(addressDTO)
                .items(itemDTOList)
                .build();

            results.add(requestDTO);
        }

        return results;
    }
}
