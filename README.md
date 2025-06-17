# 📘 API 엔드포인트 매핑 정리
### 🛍️ GoodsController

|엔드포인트 URL|메서드 이름|설명|
|---|---|---|
|`/api/goods/popular`|`getPopularGoods`|인기 상품 목록 조회|
|`/api/goods/recommend`|`getRecommendGoods`|추천 상품 목록 조회|

---

### 🗂️ CategoryController

|엔드포인트 URL|메서드 이름|설명|
|---|---|---|
|`/api/category/categoryTree`|`getCategoryTrees`|카테고리 트리 조회|
|`/api/category/category`|`getCategoryList`|전체 카테고리 리스트 조회|

---

### 👤 MemberController

| 엔드포인트 URL                | 메서드 이름      | 설명            |
| ------------------------ | ----------- | ------------- |
| `/api/member/sign`       | `postSign`  | 회원가입          |
| `/api/member/login`      | `getLogin`  | 로그인           |
| `/api/member/memberinfo` | `getMember` | 현재 로그인된 회원 정보 |

---

### 🔍 SearchController

|엔드포인트 URL|메서드 이름|설명|
|---|---|---|
|(추가 정리 필요)|-|검색 관련 기능 처리|

---

## 🧩 프론트에서 사용하는 이미지 정보 매핑

|컬럼명|설명|프론트 사용 용도|
|---|---|---|
|`imgname`|상품 고유 ID (`Goods.java`의 PK와 연결됨)|상품 묶음 구분용 키|
|`imgurl`|실제 이미지 URL (`/api/public/img/goods/...`)|`<img src="도메인 + image_url">`|
|`ismain`|true면 메인 이미지, false면 서브 이미지|썸네일과 상세 이미지 구분|


```
edu.pnu
├── config
│   ├── ImgConfig.java               → 이미지 업로드 경로 관련 설정
│   ├── JWTAuthenticationFilter.java→ JWT 로그인 시 인증 처리 필터
│   ├── JWTAuthorizationFilter.java → 요청 시 JWT 인증/인가 필터
│   └── SecurityConfig.java         → Spring Security 설정 전반 관리
│
├── controller
│   ├── CategoryController.java     → 카테고리 관련 API 엔드포인트
│   ├── GoodsController.java        → 광고/인기 상품 등 상품 조회 API
│   ├── MemberController.java       → 회원 관련 요청 처리 (로그인, 가입 등)
│   └── SearchController.java       → 키워드/필터 기반 상품 검색 API
│
├── domain
│   ├── Goods.java                  → 상품 엔티티 (DB 테이블 매핑)
│   ├── Member.java                 → 회원 엔티티
│   ├── Role.java                   → 회원 역할(Role) 정의용 Enum
│   └── SearchHistory.java         → 회원별 검색 기록 엔티티
│
├── dto
│   ├── category
│   │   ├── CategoryDTO.java              → 단일 카테고리 정보 DTO
│   │   ├── CategoryDetailDTO.java       → 소분류 DTO
│   │   ├── CategoryListResponseDTO.java → 중분류 목록 응답 DTO
│   │   ├── CategoryMainDTO.java         → 대분류 정보 DTO
│   │   ├── CategoryMidDTO.java          → 중분류 및 소분류 묶음 DTO
│   │   └── CategoryTreeResponseDTO.java → 전체 카테고리 트리 구조 DTO
│   │
│   ├── filter
│   │   └── SearchFilterDTO.java         → 필터 검색 요청 DTO (main, gender 등)
│   │
│   ├── goods
│   │   ├── AdGoodsDTO.java              → 인기/추천 상품 리스트 응답 DTO
│   │   └── GoodsDTO.java                → 상품 기본 응답 DTO
│   │
│   └── search
│       └── SearchResultsDTO.java        → 키워드 검색 결과 DTO
│
├── persistence
│   ├── GoodsRepository.java             → 상품 관련 JPA 쿼리 메서드 정의
│   ├── MemberRepository.java            → 회원 관련 JPA 쿼리 메서드 정의
│   └── SearchHistoryRepository.java     → 검색 기록 관련 쿼리 메서드 정의
│
├── service
│   ├── CategoryService.java             → 카테고리 데이터 가공 로직
│   ├── GoodsService.java                → 인기/추천 상품 비즈니스 로직 처리
│   ├── MemberService.java               → 회원 가입/조회 등 비즈니스 로직
│   ├── SearchHistoryService.java        → 검색 기록 저장/조회 처리
│   └── SearchService.java               → 키워드 및 필터 검색 처리
│
├── specification
│   └── CategorySpecification.java       → JPA Specification 조합 정의 (조건별 검색)
│
└── util
    └── GoodsImgUtil.java                → 상품 이미지명 중복 제거 유틸리티
```

