# 📘 API 엔드포인트 매핑 정리
### 🛍️ GoodsController

| 엔드포인트 URL              | 메서드 이름              | 설명          |
| ---------------------- | ------------------- | ----------- |
| `/api/goods/popular`   | `getPopularGoods`   | 인기 상품 목록 조회 |
| `/api/goods/recommend` | `getRecommendGoods` | 추천 상품 목록 조회 |


---

### 🗂️ CategoryController

| 엔드포인트 URL                    | 메서드 이름             | 설명             |
| ---------------------------- | ------------------ | -------------- |
| `/api/category/categoryTree` | `getCategoryTrees` | 카테고리 트리 조회     |
| `/api/category/category`     | `getCategoryList`  | 전체 카테고리 리스트 조회 |


---

### 👤 MemberController

| 엔드포인트 URL                | 메서드 이름      | 설명            |
| ------------------------ | ----------- | ------------- |
| `/api/member/sign`       | `postSign`  | 회원가입          |
| `/api/member/login`      | `getLogin`  | 로그인           |
| `/api/member/memberinfo` | `getMember` | 현재 로그인된 회원 정보 |


---

### 🔍 SearchController

| 엔드포인트 URL  | 메서드 이름 | 설명          |
| ---------- | ------ | ----------- |
| (추가 정리 필요) | -      | 검색 관련 기능 처리 |


---

## 🧩 프론트에서 사용하는 이미지 정보 매핑

| 컬럼명       | 설명                                      | 프론트 사용 예시                                     |
| --------- | --------------------------------------- | --------------------------------------------- |
| `imgname` | 상품 코드 (`Goods.java`의 PK, 예: 108775015)  | 상품별 이미지 구분 키                                  |
| `imgurl`  | 이미지 상대 경로 (예: `108/108775015_main.jpg`) | `<img src="/api/public/img/goods/ + imgurl">` |
| `ismain`  | true면 대표 이미지, false면 서브 이미지             | 썸네일(대표)/상세 이미지 구분용                            |




```
edu.pnu
📁 src
└── 📁 main
    ├── 📁 java
    │   └── 📁 edu.pnu
    │        ├── 📁 config
    │        │    ├── 📄 ImgConfig.java                 // ✅ 정적 이미지 경로 설정
    │        │    └── 📄 SecurityConfig.java            // ✅ Spring Security 설정
    │        │
    │        ├── 📁 config.filter
    │        │    ├── 📄 JWTAuthenticationFilter.java   // ✅ 로그인 필터
    │        │    └── 📄 JWTAuthorizationFilter.java    // ✅ 인증 필터
    │        │
    │        ├── 📁 controller
    │        │    ├── 📄 MainPageController.java        // ✅ 메인 페이지
    │        │    ├── 📄 GoodsController.java           // ✅ 상품(목록, 상세, 검색)
    │        │    ├── 📄 UserController.java            // ✅ 회원가입/로그인/정보
    │        │    ├── 📄 CategoryController.java        // ✅ 카테고리 관련
    │        │    ├── 📄 ❗️MyPageController.java         // [구현 필요] 마이페이지
    │        │    ├── 📄 ❗️CartController.java           // [구현 필요] 장바구니
    │        │    └── 📄 ❗️AdminController.java          // [구현 필요] 관리자용
    │        │
    │        ├── 📁 dto
    │        │    ├── 📄 MainPageDTO.java               // ✅ 메인 페이지 전체 DTO
    │        │    ├── 📄 MainPageGoodsDTO.java          // ✅ 인기/추천 상품 DTO
    │        │    ├── 📄 CategoryDTO.java               // ✅ 카테고리 DTO
    │        │    ├── 📄 GoodsSearchDTO.java            // ✅ 검색 결과 DTO
    │        │    ├── 📄 GoodsDetailDTO.java            // ✅ 상품 상세 DTO
    │        │    ├── 📄 MemberSignDTO.java             // ✅ 회원가입 DTO
    │        │    ├── 📄 ImgAdressDTO.java              // ✅ 이미지 DTO
    │        │    ├── 📄 ❗️FindIdDTO.java               // [구현 필요] 아이디 찾기
    │        │    └── 📄 ❗️ResetPasswordDTO.java        // [구현 필요] 비밀번호 재설정
    │        │
    │        ├── 📁 domain
    │        │    ├── 📄 Goods.java                     // ✅ 상품 Entity
    │        │    ├── 📄 GoodsOption.java               // ✅ 상품 옵션 Entity
    │        │    ├── 📄 ImgAdress.java                 // ✅ 이미지 Entity
    │        │    ├── 📄 Member.java                    // ✅ 회원 Entity
    │        │    ├── 📄 Banner.java                    // ✅ 배너 Entity
    │        │    ├── 📄 SearchHistory.java             // ✅ 검색 기록 Entity
    │        │    ├── 📄 Role.java                      // ✅ 권한 Entity
    │        │    ├── 📄 ❗️Order.java                   // [구현 필요] 주문
    │        │    ├── 📄 ❗️Cart.java                    // [구현 필요] 장바구니
    │        │    └── 📄 ❗️Notice.java                  // [구현 필요] 공지사항
    │        │
    │        ├── 📁 persistence
    │        │    ├── 📄 GoodsRepository.java           // ✅ 상품 JPA
    │        │    ├── 📄 GoodsOptionRepository.java     // ✅ 옵션 JPA
    │        │    ├── 📄 ImgAdressRepository.java       // ✅ 이미지 JPA
    │        │    ├── 📄 MemberRepository.java          // ✅ 회원 JPA
    │        │    ├── 📄 BannerRepository.java          // ✅ 배너 JPA
    │        │    ├── 📄 SearchHistoryRepository.java   // ✅ 검색 기록 JPA
    │        │    ├── 📄 ❗️OrderRepository.java         // [구현 필요] 주문 JPA
    │        │    ├── 📄 ❗️CartRepository.java          // [구현 필요] 장바구니 JPA
    │        │    └── 📄 ❗️NoticeRepository.java        // [구현 필요] 공지사항 JPA
    │        │
    │        └── 📁 service
    │             ├── 📄 MainPageService.java           // ✅ 메인 페이지 서비스
    │             ├── 📄 GoodsService.java              // ✅ 상품 서비스
    │             ├── 📄 ImgAdressService.java          // ✅ 이미지 서비스
    │             ├── 📄 MemberService.java             // ✅ 회원 서비스
    │             ├── 📄 ServiceUserDetailsService.java // ✅ Spring Security 인증
    │             ├── 📄 ❗️OrderService.java            // [구현 필요] 주문 서비스
    │             └── 📄 ❗️CartService.java             // [구현 필요] 장바구니 서비스
    │
    └── 📁 resources
         ├── 📄 application.properties                 // ✅ DB 등 환경설정
         └── 📁 static/                                // (실제 사용 X, 외부 이미지 경로 사용 중)

```

