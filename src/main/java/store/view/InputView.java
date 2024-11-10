package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.parser.AgreementInputParser;
import store.parser.InputParser;

public class InputView {

    private static final String ORDER_ITEM = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String FREE_QUANTITY = "\n현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n";
    private static final String FULL_PAYMENT = "\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n";
    private static final String MEMBERSHIP_DISCOUNT = "\n멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String RETRY_ORDER = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public <T> T readOrderItems(InputParser<T> parser) {
        System.out.println(ORDER_ITEM);
        return readProcess(parser);
    }

    public Boolean readAgreeFreeQuantity(Object ... args) {
        System.out.printf(FREE_QUANTITY, args);
        return readAgree();
    }

    public Boolean readAgreeFullPayment(Object ... args) {
        System.out.printf(FULL_PAYMENT, args);
        return readAgree();
    }

    public Boolean readAgreeMembershipDiscount() {
        System.out.println(MEMBERSHIP_DISCOUNT);
        return readAgree();
    }

    public Boolean readAgreeRetryOrder() {
        System.out.println(RETRY_ORDER);
        return readAgree();
    }

    private Boolean readAgree() {
        AgreementInputParser processor = AgreementInputParser.getInstance();
        return readProcess(processor);
    }

    private <T> T readProcess(InputParser<T> parser) {
        try {
            return parser.parse(Console.readLine());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readProcess(parser);
        }
    }
}
