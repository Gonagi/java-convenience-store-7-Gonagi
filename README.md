# java-convenience-store-precourse

# 기능 요구 사항

## 상품

### Product

- 상품의 수량이 0 미만이면 안된다.
- 상품명과 프로모션이 같으면 같은 상품이다.
- 같은 상품이 존재해서는 안된다.

### ProductParser

- 문자열을 읽어 상품 정보를 생성한다.
    - ","로 상품 문자열을 구분한다.
    - price, quantity는 숫자로 변환이 가능해야 한다.
    - promotion은 promotions.md에 있는 프로모션이어야 한다.

### ProductValidator

- 이름, price, quantity, 프로모션 요소만 있어야 한다.
    - 요소가 누락되거나 추가되면 안된다.
    - 요소가 빈칸이거나 null이면 안된다.

### Quantity

- 수량을 업데이트 한다.
    - 존재하지 않는 상품은 구매할 수 없다.
    - 재고 이상의 수량을 초과하여 구매할 수 없다.
    - 구매한 수량만큼 재고가 줄어든다.

### Products

- 파일 경로로 상품 파일을 읽는다.
    - 잘못된 파일 경로가 주어지면 예외가 발생해야 한다.
    - 첫번째 줄은 읽지 않는다.

## 프로모션

### Promotion

- 프로모션을 생성한다.
    - 이름이 같으면 같은 프로모션이다.

### PromotionParser

- 문자열을 읽어 프로모션 정보를 생성한다.
    - ","로 프로모션 문자열을 구분한다.
    - buy, get은 숫자로 변환이 가능해야 한다.
    - 시작 날짜, 끝나는 날짜는 "yyyy-MM-dd" 형식을 따라야 한다.

### PromotionValidator

- 이름, buy, get, 시작 날짜, 끝나는 날짜 요소만 있어야 한다.
    - 요소가 누락되거나 추가되면 안된다.
    - 요소가 빈칸이거나 null이면 안된다.
    - 시작 날짜가 끝나는 날짜 이후의 날짜 일수는 없다.

### Promotions

- 파일 경로로 프로모션 파일을 읽는다.
    - 잘못된 파일 경로가 주어지면 예외가 발생해야 한다.
    - 첫번째 줄은 읽지 않는다.
- 이름으로 프로모션을 검색한다.

## 인벤토리

### Inventory

- 상품을 통해 인벤토리를 생성한다.
    - 중복된 상품이 있는지 확인한다.
- 상품을 업데이트한다.
    - 상품을 찾는다.
    - 재고를 줄인다.
- 일반 상품의 재고가 충분한지 확인한다.
- 프로모션 상품의 재고가 충분한지 확인한다.
- 이름, 프로모션이 같은 상품을 찾는다.
    - 없으면 예외가 발생한다.
- 이름으로 일반 상품을 찾는다.
    - 없으면 예외가 발생한다.
- 이름으로 프로모션 상품을 찾는다.
    - 없으면 예외가 발생한다.
