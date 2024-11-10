package store.constant;

public enum ErrorMessage {

    FAILED_READ_FILE("파일을 읽는데에 실패하였습니다."),

    NOT_BLANK_PROMOTION_NAME("프로모션명은 비어 있을 수 없습니다."),
    NOT_BLANK_PRODUCT_NAME("상품 이름은 비어있을 수 없습니다."),

    NOT_PARSE_LOCALDATE("날짜 변환 에러"),
    NOT_PARSE_INTEGER("숫자(Integer) 변환 에러"),
    NOT_PARSE_LONG("숫자(Long) 변환 에러"),

    NOT_EXIST_PROMOTION("프로모션이 존재하지 않습니다."),
    NOT_EXIST_PRODUCT("상품이 존재하지 않습니다."),
    NOT_EXIST_FILE_RESOURCE("리소스가 존재하지 않습니다."),
    NOT_EXIST_FILE_DATA("파일의 데이터가 존재하지 않습니다."),

    INVALID_URI_OF_RESOURCE("리소스의 URI가 올바르지 않습니다."),
    INVALID_STOCK("조건에 맞는 재고가 없습니다."),
    INVALID_PRODUCT_PRICE("상품 금액은 0보다 커야 합니다."),
    INVALID_PRODUCT_STOCK("재고 수는 0보다 작을 수 없습니다"),
    INVALID_PROMOTION_BUY_COUNT("프로모션 구매 개수는 0보다 커야합니다."),
    INVALID_PROMOTION_GET_COUNT("증정개수는 1이여야 합니다."),
    INVALID_PROMOTION_DATE("프로모션 시작날짜가 종료날짜보다 이후일 수 없습니다."),
    INVALID_INPUT_AGREEMENT("(Y/N) 중에 입력해주세요."),
    INVALID_INPUT_ORDER_ITEMS("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT_NOT_EXIST_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVALID_INPUT_EXCEED_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_INPUT_ORDER_QUANTITY("주문 수량은 0보다 커야합니다.")
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return String.format("[ERROR] %s", message);
    }
}
