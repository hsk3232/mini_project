# 📚 API 엔드포인트/JSON/코드 정리

---

## 1️⃣ API 엔드포인트 표 (컨트롤러별)

| 컨트롤러                | 엔드포인트 URL                   | HTTP 방식 | 주요 파라미터/Body                       | 응답/설명                        |
|------------------------|----------------------------------|----------|------------------------------------------|-----------------------------------|
| CartController         | `/api/member/cart/add`                  | POST     | optionId, quantity, member               | 장바구니에 상품 추가 (CartDTO 등) |
|                        | `/api/member/cart/list`                 | GET      | member                                   | 장바구니 전체 목록 반환           |
|                        | `/api/member/cart/remove/{optionid}`    | POST     | optionId, member                         | 장바구니 상품 선택 삭제               |
|                        | `/api/member/cart/remove`               | POST     | member                                   | 장바구니 상품 전체 삭제                |
| CategoryController     | `/api/public/categoryTree`     | GET      | 없음                                     | 카테고리 트리 반환                |
|                        | `/api/public/category`         | GET      | 없음                                     | 전체 카테고리 리스트              |
| GoodsController        | `/api/public/popular`             | GET      | 없음                                     | 인기 상품 목록 반환               |
|                        | `/api/public/recommend`           | GET      | 없음                                     | 추천 상품 목록 반환               |
|                        | `/api/public/detail/{imgname}` | GET        | imgname                          | 상품 상세 페이지
|                        | `/api/public/category/goods`                | GET      | 필터 파라미터 (카테고리 등)              | 상품 전체 목록 반환               |
| JoinController         | `/api/member/sign`               | POST     | 회원정보 (회원가입DTO)                     | 회원가입 처리, 성공/실패 응답     |
| SearchController       | `/api/public/search`             | GET      | main, mid, detail, gender, color, print, keyword, price 등 | 상품 검색 결과 반환     |
| AdminController        | `/api/admin/goods/approve`       | POST     | 상품정보, 승인정보                        | 상품 승인 처리                    |
|                        | `/api/admin/member/list`         | GET      | 없음                                     | 회원 전체 리스트 반환             |
|                        | `/api/admin/register`            | POST     | 상품 정보 (GoodsDTO 등)                   | 상품 등록                         |
|                        | `/api/admin/delete/{imgname}`    | DELETE   | imgname (PathVariable)                    | 상품 삭제                         |
| OrderController        | `/api/member/orders`              | POST     | 주문 정보(OrderDTO 등)                    | 주문 생성, 주문결과 반환          |
|                        | `/api/member/list`                | GET      | 회원정보 등                               | 주문 내역 리스트 반환             |
| GoodsDetailController  | `/api/public/detail/{imgname}`    | GET      | imgname (Path)                            | 상품 상세정보 반환                |
| MemberController       | `/api/member/login`              | POST/GET | username, password                        | 로그인, 토큰/정보 반환            |
|                        | `/api/member/info`                | GET      | 없음                                     | 회원 정보 반환                    |
|                        | `/api/member/logout`             | POST/GET | 없음                                     | 로그아웃 처리                     |


---

## 2️⃣ 주요 엔드포인트별 JSON 예시

### 🛍️ 상품 등록 (POST `/api/goods/register`)

```json
{
  "imgname": "108775015_main.jpg",
  "productname": "오버핏 반팔 티셔츠",
  "brand": "유니클로",
  "category": "top",
  "price": 25000,
  "description": "여름용 오버핏 티셔츠",
  "options": [
    {
      "optionid": "108775015_S",
      "size": "S",
      "color": "white",
      "stock": 10
    },
    {
      "optionid": "108775015_M",
      "size": "M",
      "color": "white",
      "stock": 15
    }
  ]
}
```
**응답:**
```json
{
  "result": "success",
  "goodsid": "108775015"
}
```

---

### 🛒 장바구니 추가 (POST `/api/cart/add`)

```json
{
  "optionid": "108775015_M",
  "quantity": 2
}
```
**응답:**
```json
{
  "result": "success",
  "cartitem": {
    "optionid": "108775015_M",
    "quantity": 2,
    "productname": "오버핏 반팔 티셔츠",
    "imgurl": "/api/public/img/goods/108/108775015_main.jpg"
  }
}
```

---

### 👤 회원가입 (POST `/api/member/sign`)

```json
{
  "username": "user14",
  "password": "1234",
  "nickname": "이지은",
  "role": "ROLE_MEMBER",
  "gender": "FEMALE",
  "birth": "2002-11-21",
  "email": "testuser14@example.com",
  "phone": "010-2345-6789"
}
```
**응답:**
```json
{
  "result": "success",
  "message": "회원가입이 완료되었습니다."
}
```

---

### 🔎 상품 검색 (GET `/api/public/search`)

**예시 요청:**  
`/api/public/search?main=top&gender=FEMALE&color=white&keyword=티셔츠`

**응답:**
```json
[
  {
    "imgname": "108775015_main.jpg",
    "productname": "오버핏 반팔 티셔츠",
    "brand": "유니클로",
    "category": "top",
    "price": 25000,
    "options": [
      { "optionid": "108775015_S", "size": "S", "color": "white", "stock": 10 },
      { "optionid": "108775015_M", "size": "M", "color": "white", "stock": 15 }
    ],
    "viewcount": 120
  }
]
```

---

### 📦 상품 상세 (GET `/api/goods/detail/{imgname}`)

```json
{
  "imgname": "108775015_main.jpg",
  "productname": "오버핏 반팔 티셔츠",
  "brand": "유니클로",
  "category": "top",
  "price": 25000,
  "description": "여름용 오버핏 티셔츠",
  "options": [
    { "optionid": "108775015_S", "size": "S", "color": "white", "stock": 10 },
    { "optionid": "108775015_M", "size": "M", "color": "white", "stock": 15 }
  ],
  "viewcount": 120
}
```

---

### 📝 주문 생성 (POST `/api/order/create`)

```json
{
  "username": "user14",
  "orderlist": [
    { "optionid": "108775015_S", "quantity": 1 },
    { "optionid": "108775044_L", "quantity": 2 }
  ],
  "name": "이지은",
  "zip": "12345",
  "address1": "부산광역시 금정구 중앙대로1719번길 13",
  "address2": "103동 1003호",
  "phone": "010-2345-6789",
  "payment": "card"
}
```
**응답:**
```json
{
  "result": "success",
  "orderid": 132
}
```

---

### 👤 로그인 (POST `/api/member/login`)

```json
{
  "username": "user14",
  "password": "1234"
}
```
**응답:**
```json
{
  "result": "success",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}
```

---

## 3️⃣ Java DTO 예시

### GoodsDTO

```java
public class GoodsDTO {
    private String imgname;
    private String productname;
    private String brand;
    private String category;
    private int price;
    private String description;
    private List<GoodsOptionDTO> options;
    private int viewcount;
}
```

### GoodsOptionDTO

```java
public class GoodsOptionDTO {
    private String optionid;
    private String size;
    private String color;
    private int stock;
}
```

### OrderDTO

```java
public class OrderDTO {
    private String username;
    private List<OrderItemDTO> orderlist;
    private String name;
    private String zip;
    private String address1;
    private String address2;
    private String phone;
    private String payment;
}
```

```
📁 src
    ├── 📁 main
    │   ├── 📁 java
    │   │   └── 📁 edu
    │   │       └── 📁 pnu
    │   │           ├── 📄 MiniBackEndApplication.java
    │   │           ├── 📁 config
    │   │           │   ├── 📄 ImgConfig.java
    │   │           │   ├── 📄 SecurityConfig.java
    │   │           │   └── 📁 filter
    │   │           │       ├── 📄 JWTAuthenticationFilter.java
    │   │           │       └── 📄 JWTAuthorizationFilter.java
    │   │           ├── 📁 controller
    │   │           │   ├── 📄 AdminController.java
    │   │           │   ├── 📄 CartController.java
    │   │           │   ├── 📄 CategoryController.java
    │   │           │   ├── 📄 GoodsController.java
    │   │           │   ├── 📄 GoodsDetailController.java
    │   │           │   ├── 📄 JoinController.java
    │   │           │   ├── 📄 MemberController.java
    │   │           │   ├── 📄 OrderController.java
    │   │           │   └── 📄 SearchController.java
    │   │           ├── 📁 domain
    │   │           │   ├── 📄 Cart.java
    │   │           │   ├── 📄 CartItem.java
    │   │           │   ├── 📄 Goods.java
    │   │           │   ├── 📄 GoodsOption.java
    │   │           │   ├── 📄 ImgAdress.java
    │   │           │   ├── 📄 Member.java
    │   │           │   ├── 📄 OrderItem.java
    │   │           │   ├── 📄 OrderList.java
    │   │           │   ├── 📄 QnA.java
    │   │           │   ├── 📄 Review.java
    │   │           │   ├── 📄 Role.java
    │   │           │   ├── 📄 SearchHistory.java
    │   │           │   └── 📄 WishList.java
    │   │           ├── 📁 dto
    │   │           │   ├── 📁 Orders
    │   │           │   │   ├── 📄 OrderItemDTO.java
    │   │           │   │   ├── 📄 OrderListDTO.java
    │   │           │   │   ├── 📄 OrderRequestDTO.java
    │   │           │   │   └── 📄 ReviewDTO.java
    │   │           │   ├── 📁 cart
    │   │           │   │   ├── 📄 CartDTO.java
    │   │           │   │   └── 📄 CartItemDTO.java
    │   │           │   ├── 📁 category
    │   │           │   │   ├── 📄 CategoryDTO.java
    │   │           │   │   ├── 📄 CategoryDetailDTO.java
    │   │           │   │   ├── 📄 CategoryListResponseDTO.java
    │   │           │   │   ├── 📄 CategoryMainDTO.java
    │   │           │   │   ├── 📄 CategoryMidDTO.java
    │   │           │   │   └── 📄 CategoryTreeResponseDTO.java
    │   │           │   ├── 📁 filter
    │   │           │   │   └── 📄 SearchFilterDTO.java
    │   │           │   ├── 📁 goods
    │   │           │   │   ├── 📄 AdGoodsDTO.java
    │   │           │   │   ├── 📄 GoodsDTO.java
    │   │           │   │   ├── 📄 GoodsOptionDTO.java
    │   │           │   │   └── 📄 ImgAdressDTO.java
    │   │           │   ├── 📁 member
    │   │           │   │   ├── 📄 MemberJoinDTO.java
    │   │           │   │   └── 📄 MemberUpdateDTO.java
    │   │           │   └── 📁 search
    │   │           │       └── 📄 SearchResultsDTO.java
    │   │           ├── 📁 persistence
    │   │           │   ├── 📄 CartRepository.java
    │   │           │   ├── 📄 GoodsOptionRepository.java
    │   │           │   ├── 📄 GoodsRepository.java
    │   │           │   ├── 📄 MemberRepository.java
    │   │           │   ├── 📄 OrderListRepository.java
    │   │           │   └── 📄 SearchHistoryRepository.java
    │   │           ├── 📁 service
    │   │           │   ├── 📁 everyone
    │   │           │   │   ├── 📄 CategoryService.java
    │   │           │   │   ├── 📄 GoodsDetailService.java
    │   │           │   │   ├── 📄 GoodsService.java
    │   │           │   │   └── 📄 SearchService.java
    │   │           │   └── 📁 member
    │   │           │       ├── 📄 CartService.java
    │   │           │       ├── 📄 JoinService.java
    │   │           │       ├── 📄 MemberService.java
    │   │           │       ├── 📄 OrderService.java
    │   │           │       ├── 📄 SearchHistoryService.java
    │   │           │       └── 📄 SecurityUserDetailsService.java
    │   │           ├── 📁 specification
    │   │           │   └── 📄 CategorySpecification.java
    │   │           └── 📁 util
    │   │               └── 📄 GoodsImgUtil.java
    │   └── 📁 resources
    │       ├── 📄 application.properties
    │       ├── 📁 static
    │       └── 📁 templates
    └── 📁 test
        └── 📁 java
            └── 📁 edu
                └── 📁 pnu
                    ├── 📄 MemberRepositoryTest.java
                    ├── 📄 MiniBackEndApplicationTests.java
                    └── 📄 RandomViewCountTest.java

```

