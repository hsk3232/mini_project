# 🌺 KALANI — 노가다로 빚어낸 쇼핑 스토어

> **팀명:** 취업시켜  
> **FE**: 강나현 &nbsp;&nbsp;|&nbsp;&nbsp; **BE**: 홍지민  

---

## 🌈 프로젝트 소개

**KALANI**는 하와이어로 ‘하늘(heaven)’, ‘천국’, ‘고귀한’을 의미합니다.  
직접 데이터를 만들고, 사진을 촬영/가공해서 만든 리얼 쇼핑몰 웹 서비스입니다.  
H&M 의류 상품 이미지를 기반으로, 실제 쇼핑몰 환경과 최대한 유사하게 구현했습니다.

- **Frontend:** React, Vite, JavaScript, TailwindCSS  
- **Backend:** Spring Boot, JPA, JWT, Spring Security  
- **Database:** MySQL  

---

## 🗂️ 활용 데이터

- **데이터 출처:** Kaggle, 자체 생성(직접 촬영 및 가공)
- **주요 콘텐츠:** H&M 의류 상품 사진, 쇼핑몰에 적합한 상품 정보

---

## 🧩 주요 기능

- 회원가입 / 로그인 / 내 정보 관리  
- 장바구니(담기, 수량 수정, 항목 삭제)  
- 주문 생성 / 주문 내역 조회  
- 카테고리별 상품 탐색(전체, 트리, 필터, 인기/추천, 상세)

---

## ⚙️ API 명세 및 화면 설계

| 화면명           | API 경로                                         | 설명           |
|:----------------|:-------------------------------------------------|:---------------|
| 회원가입         | `/api/public/join`                               | Users-01, 02   |
| 내 정보 조회/수정 | `/api/member/info`, `/api/member/infoEdit`       | Users-03, 04   |
| 장바구니 담기    | `/api/member/cart/add`                           | cart-01, 02    |
| 장바구니 수정/삭제 | `/api/member/cart/update`, `/delete/{id}`        | cart-03, 04    |
| 주문/주문내역    | `/api/member/orders`, `/orderlist`               | Order-01, 02   |
| 카테고리 전체 조회 | `/api/member/category`, `/categoryTree`          | Category-01, 02|
| 카테고리 상품 검색 | `/api/member/category/goods`                     | Category-03    |
| 인기/추천 상품   | `/api/public/popular`, `/api/public/recommend`   | Goods-01, 02   |
| 상품 상세        | `/api/public/detail/{imgname}`                   | Goods-03       |

- 자세한 API 명세: [Swagger 문서](https://app.swaggerhub.com/draft/hongjimin-be4#/)
- 화면 설계서 및 예시는 별도 공유

---

## 🛠️ 시스템 구성도

- FE: React + Vite (TailwindCSS)
- BE: Spring Boot (JPA, JWT, Spring Security)
- DB: MySQL

---

## 🗃️ Database 설계 (ERD)

- [ERD 설계 바로가기](https://dbdiagram.io/d/mini_projesct-6859b96af039ec6d36893f6b)

---

## 🚀 배포 및 실행 방법

### 프론트엔드

```bash
npm install
npm run dev
```
### 백엔드

- JDK 17 이상 설치  
- MySQL 세팅 및 application.yml 환경 변수 입력  
- IDE에서 Spring Boot 실행  

---

## 📬 문의 및 연락

- FE 강나현: [이메일/연락처]  
- BE 홍지민: [이메일/연락처]  
- 이슈/버그/건의: GitHub Issue  

---

## ✨ Special Thanks

데이터 수집/가공, 팀원들의 노가다,  
그리고 이 README를 읽어주시는 모든 분께 감사드립니다!  
취업시켜 조, 파이팅! 👊


---

## 1️⃣ API 엔드포인트 표 (컨트롤러별)

| 컨트롤러                | 엔드포인트 URL                   | HTTP 방식 | 주요 파라미터/Body                       | 응답/설명                        |
|------------------------|----------------------------------|----------|------------------------------------------|-----------------------------------|
| CartController         | `/api/member/cart/add`                  | POST     | optionId, quantity, member               | 장바구니에 상품 추가 (CartDTO 등) |
|                        | `/api/member/cart/list`                 | GET      | member                                   | 장바구니 전체 목록 반환           |
|                        | `/api/member/cart/remove/{optionid}`    | POST     | optionId, member                         | 장바구니 상품 선택 삭제               |
|                        | `/api/member/cart/remove`               | POST     | member                                   | 장바구니 상품 전체 삭제                |
| CategoryController     | `/api/public/categoryTree`       | GET      | 없음                                     | 카테고리 트리 반환                |
|                        | `/api/public/category`           | GET      | 없음                                     | 전체 카테고리 리스트              |
| GoodsController        | `/api/public/popular`            | GET      | 없음                                     | 인기 상품 목록 반환               |
|                        | `/api/public/recommend`          | GET      | 없음                                     | 추천 상품 목록 반환               |
|                        | `/api/public/detail/{imgname}`   | GET      | imgname                          | 상품 상세 페이지
|                        | `/api/public/category/goods`     | GET      | 필터 파라미터 (카테고리 등)              | 상품 전체 목록 반환               |
| JoinController         | `/api/member/join`               | POST     | 회원정보 (회원가입DTO)                     | 회원가입 처리, 성공/실패 응답     |
|                        |`/join/idsearch`                  | POST     | 아이디 중복 확인                           | 아이디 사용 가능 여부 반환  |
| SearchController       | `/api/public/search`             | GET      | main, mid, detail, gender, color, print, keyword, price 등 | 상품 검색 결과 반환     |
| AdminController        | `/api/admin/goods/approve`       | POST     | 상품정보, 승인정보                        | 상품 승인 처리                    |
|                        | `/api/admin/member/list`         | GET      | 없음                                     | 회원 전체 리스트 반환             |
|                        | `/api/admin/register`            | POST     | 상품 정보 (GoodsDTO 등)                   | 상품 등록                         |
|                        | `/api/admin/delete/{imgname}`    | DELETE   | imgname (PathVariable)                    | 상품 삭제                         |
| OrderController        | `/api/member/orders`              | POST    | 주문 정보(OrderDTO 등)                    | 주문 생성, 주문결과 반환          |
|                        | `/api/member/list`                | GET     | 회원정보 등                               | 주문 내역 리스트 반환             |
| GoodsDetailController  | `/api/public/detail/{imgname}`    | GET     | imgname (Path)                            | 상품 상세정보 반환                |
| MemberController       | `/api/member/login`              | POST/GET | username, password                        | 로그인, 토큰/정보 반환            |
|                        | `/api/member/info`                | GET     | 없음                                     | 회원 정보 반환                    |
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

