## 📝 QnA API 명세

| #  | Method | URL                                     | 요청 예시                                                        | 응답 예시                                                      | 설명                          |
|----|--------|------------------------------------------|------------------------------------------------------------------|----------------------------------------------------------------|-------------------------------|
| 1  | GET    | `/api/member/qnagoodsinfo?imgname=108775051` | 없음 (imgname 쿼리파라미터)                                      | `{ "imgname": "108775051", "productname": "Fog Stripe 감각적인 슬리브리스 탑", "price": 18200 }` | 상품정보 조회 (QnA 작성용)      |
| 2  | POST   | `/api/member/addqna`                    | `{ "imgname": "108775051", "question": "이거 사이즈 xxxl은 없나여? 제가 돼지라서여..." }` | `{ "qaid": 3, "imageUrl": "108/108775051_main.jpg", "productName": "Fog Stripe 감각적인 슬리브리스 탑", "createdat": "2025-07-03", "imgname": "108775051", "question": "이거 사이즈 xxxl은 없나여? 제가 돼지라서여...", "remain": true }` | QnA 등록                       |
| 3  | PATCH  | `/api/member/deleteqna`                 | `{ "qaid": 1 }`                                                  | `[ { "qaid": 1, ... }, { "qaid": 2, ... } ]`                   | QnA 논리삭제 (본인 QnA 리스트 반환) |

---

### 🚩 요청/응답 필드 설명
- **상품정보(QnAGoodsInfoDTO)**
    - `imgname` : 상품 이미지명 (고유 ID)
    - `productname` : 상품명
    - `price` : 상품 가격

- **QnA 등록/조회(QnAListDTO)**
    - `qaid` : QnA 고유 ID
    - `imageUrl` : 대표 이미지 URL
    - `productName` : 상품명
    - `createdat` : QnA 작성일 (yyyy-MM-dd)
    - `imgname` : 상품 이미지명
    - `question` : 질문 내용
    - `remain` : 삭제 여부 (`true`면 살아있는 QnA)
    - `username` : (있으면 유저명, 없으면 null)

---

#### 인증 주의사항
- 인증 필요한 API는 **Authorization 헤더** 필수  
    ```
    Authorization: Bearer {JWT토큰}
    ```
- 모든 요청/응답은 **application/json** 포맷 사용

