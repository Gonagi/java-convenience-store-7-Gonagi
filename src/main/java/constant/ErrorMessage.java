package constant;

public enum ErrorMessage {
    INVALID_INPUT_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    EXCEEDS_STOCK_QUANTITY("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    DUPLICATE_PRODUCT_NOT_ALLOWED("[ERROR] 중복된 제품이 있어서는 안됩니다."),
    INVALID_PRICE_OR_QUANTITY("[ERROR] price, quantity 값들 중 유효하지 않은 값이 있습니다."),
    PRODUCT_FILE_NOT_FOUND("[ERROR] 상품 파일을 찾을 수 없습니다."),
    MISSING_PRODUCT_FIELDS("[ERROR] \"이름, price, quantity, 프로모션\" 중 누락된 요소가 있습니다."),
    ADDITIONAL_FIELDS_IN_PRODUCT("[ERROR] \"이름, price, quantity, 프로모션\" 외 추가된 요소가 있습니다."),
    MISSING_VALUES_IN_FIELDS("[ERROR] 요소들 중 누락된 값이 있습니다."),
    INVALID_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_PROMOTION_VALUES("[ERROR] buy, get 값들 중 유효하지 않은 값이 있습니다."),
    INVALID_PROMOTION_DATES("[ERROR] start_date, end_date 값들 중 유효하지 않은 값이 있습니다."),
    PROMOTION_FILE_NOT_FOUND("[ERROR] 프로모션 파일을 찾을 수 없습니다."),
    MISSING_PROMOTION_FIELDS("[ERROR] \"이름, buy, get, 시작 날짜, 끝나는 날짜\" 중 누락된 요소가 있습니다."),
    ADDITIONAL_FIELDS_IN_PROMOTION("[ERROR] \"이름, buy, get, 시작 날짜, 끝나는 날짜\" 외 추가된 요소가 있습니다."),
    START_DATE_AFTER_END_DATE("[ERROR] start_date가 end_date보다 이후의 날짜로 설정되어 있습니다.");

    private final String message;

    ErrorMessage(final String errorMessage) {
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }
}
