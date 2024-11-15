# java-convenience-store-precourse

# 기능 요구 사항

## 상품

### Product

- 상품의 수량을 줄인다.
    - 상품의 수량이 0 미만이면 안된다.
- 상품명과 프로모션이 같으면 같은 상품이다.
- 같은 상품이 존재해서는 안된다.
- 프로모션 상품인지 확인한다.
- 프로모션 기간이 지났는지를 현재 날짜 기준으로 확인한다.

### ProductParser

- 문자열을 읽어 상품 정보를 생성한다.
    - ","로 상품 문자열을 구분한다.
    - price, quantity는 숫자로 변환이 가능해야 한다.
    - promotion은 promotions.md에 있는 프로모션이어야 한다.

### ProductFactory

- 파일을 읽어와 Products 객체를 만든다.
- product로 List를 만들어 Products 객체를 만든다.
    - 프로모션이 있는 상품이 일반 상품 재고가 없으면 그 상품을 재고 0으로 만들어야 한다.
- 입력한 문자열에서 Products 객체를 만든다.

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

### PromotionService

- 프로모션을 계산한다.
    - 프로모션의 상품의 재고가 부족한 경우, 일반 상품을 안내하고 구매할 수 있다.
    - 프로모션의 상품의 재고가 부족한 경우에도 일반 상품을 구매 안하고 거래를 끝낼 수 있다.
    - 증정 상품을 추가로 받을 수 있을 때, 해당 사항을 안내하고 증정상품을 받을 수 있다.
    - 증정 상품을 추가로 안받고 거래를 끝낼 수 있다.

## 인벤토리

### Inventory

- 상품을 통해 인벤토리를 생성한다.
    - 중복된 상품이 있는지 확인한다.
- 이름, 프로모션이 같은 상품을 찾는다.
    - 없으면 예외가 발생한다.
- 이름으로 상품을 찾는다.
    - 프로모션 상품 재고가 있는지 찾는다.
    - 기간이 지난 프로모션 상품 재고가 있는지 찾는다.
    - 일반 상품 재고가 있는지 찾는다.
    - 셋다 없는 경우 예외가 발생한다.
- 이름으로 해당 상품의 재고를 확인한다.
    - 프로모션 상품의 재고가 충분한지 확인한다.
    - 프로모션 기간이 지난 상품의 재고가 충분한지 확인한다.
    - 일반 상품의 재고가 충분한지 확인한다.

### InventoryService

- 상품을 업데이트한다.
    - 상품을 찾는다.
    - 재고를 줄인다.
- 재고를 줄인다.
    - 재고를 줄인다.
- 이름으로 상품을 찾는다.
- 상품의 재고를 찾는다.
    - 상품의 재고는 프로모션 + 일반 상품 수량이다.

## 영수증

### Receipt

- 영수증을 생성한다.
    - 구매 상품, 증정 상품, 금액 정보가 있다.
- 구매 상품 항목에 추가한다.
- 증정 상품 항목에 추가한다.
- 금액 정보를 업데이트한다.

### ReceiptService

- 구매한 상품을 영수증에 등록한다.
    - 프로모션 상품인 경우, 프로모션 상품에 얼마를 썼는지도 기록한다.
- 증정받은 상품을 영수증에 등록한다.
- 멤버십 할인 금액을 업데이트한다.
- 최종 결제 금액을 업데이트한다.

### Amount

- 총 구매액에 구매금액을 더한다.
- 프로모션 상품 금액을 기록한다.
- 프로모션으로 할인된 금액에 할인 금액을 계산한다.
- 멤버십으로 할인된 금액을 계산한다.
    - 10의 자리는 버린다.
- 최종 결제 금액을 계산한다.

## 입력

### InputView

- 입력 받는다.
    - 입력받은 값이 null이거나 blank이면 예외가 발생한다.

### InputService

- 구매할 상품과 수량을 입력받는다.
    - "[상품명-수량]" 형식이 아니면 예외가 발생한다.
- 프로모션 적용 여부를 입력받는다.
    - "Y" 또는 "N" 외에 다른값을 입력했으면 예외가 발생한다.
- 일부 수량을 정가로 결제할지에 대한 여부를 입력받는다.
    - "Y" 또는 "N" 외에 다른값을 입력했으면 예외가 발생한다.
- 멤버십 할인 적용 여부를 입력받는다.
    - "Y" 또는 "N" 외에 다른값을 입력했으면 예외가 발생한다.
- 추가 구매 여부를 입력받는다.
    - "Y" 또는 "N" 외에 다른값을 입력했으면 예외가 발생한다.

## 출력

### OutputView

- 시작 메시지를 띄운다.
- 재고 현황을 띄운다.
- 영수증을 출력한다.
