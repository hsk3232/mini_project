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
