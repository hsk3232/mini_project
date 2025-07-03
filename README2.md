# 1️⃣ QnA 관련 API 엔드포인트 표

| 엔드포인트 URL                  | HTTP 방식 | 요청 파라미터/Body          | 응답 예시/설명                           |
| -------------------------- | ------- | --------------------- | ---------------------------------- |
| `/api/member/qnalist`      | GET     | 없음                    | 내 QnA 전체 리스트 반환 (remain=true만)     |
| `/api/member/qnagoodsinfo` | GET     | `imgname` (Body로 전달)  | 해당 상품의 간단 정보 반환 (imgname, 상품명, 가격) |
| `/api/member/addqna`       | POST    | `imgname`, `question` | 등록 후 내 QnA 전체 리스트 반환               |
| `/api/member/deleteqna`    | PATCH   | `qaid` (DTO로 전달)      | 삭제 후 내 QnA 전체 리스트 반환               |


# 2️⃣ QnA 주요 요청/응답 JSON 예시


## 🔵 상품 정보 조회 (GET /api/member/qnagoodsinfo)

요청 Body:
```json
{
  "imgname": "108775051"
}
```

응답 Body:응답 Body:
```json
{
  "imgname": "108775051",
  "productname": "Fog Stripe 감각적인 슬리브리스 탑",
  "price": 18200
}
```

## 🟢 QnA 등록 (POST /api/member/addqna)
요청 Body:
```json
{
  "imgname": "108775051",
  "question": "이거 사이즈 xxxl은 없나여? 제가 돼지라서여..."
}
```

응답 Body (QnA 전체 목록, 배열):
```json
[
  {
    "qaid": 1,
    "imageUrl": "108/108775051_main.jpg",
    "productName": "Fog Stripe 감각적인 슬리브리스 탑",
    "createdat": "2025-07-03","Createat": "2025-07-03",
    "imgname": "108775051",
    "question": "이거 사이즈 xxxl은 없나여? 제가 돼지라서여...",
    "remain": true,
    "username": "user2"
  },
  ...
]
```

## 🟣QnA 목록 조회 (GET /api/member/qnalist)
응답 Body (배열):
```json
[
  {
    "qaid": 1,
    "imageUrl": "108/108775051_main.jpg",
    "productName": "Fog Stripe 감각적인 슬리브리스 탑",
    "createdat": "2025-07-03",
    "imgname": "108775051",
    "question": "이거 사이즈 xxxl은 없나여? 제가 돼지라서여...",
    "remain": true,
    "username": "user2"
  }
]
```

## 🔴QnA 삭제 (PATCH /api/member/deleteqna)
요청 Body:
```json
{
  "qaid": 1
}
```

응답 Body (삭제 후 남은 QnA 목록, 배열):
```json
[
  {
    "qaid": 2,
    "imageUrl": "108/108775051_main.jpg",
    "productName": "Fog Stripe 감각적인 슬리브리스 탑",
    "createdat": "2025-07-03",
    "imgname": "108775051",
    "question": "이거 사이즈 xxxl은 없나여? 제가 돼지라서여...",
    "remain": true,
    "username": "user2"
  }
]
```

