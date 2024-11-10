package store.common.parser;

import store.common.constant.ApprovalOptions;

import java.util.Arrays;
import java.util.function.Predicate;

import static store.common.constant.ErrorMessage.INVALID_INPUT_AGREEMENT;

public class ApprovalParser {

    public static Boolean parse(String input) {
        validate(input);

        return ApprovalOptions.Y.name().equals(input);
    }

    private static void validate(String input) {
        if (isInvalidAgreement(input)) {
            throw new IllegalArgumentException(INVALID_INPUT_AGREEMENT.message());
        }
    }

    private static boolean isInvalidAgreement(String input) {
        return Arrays.stream(ApprovalOptions.values()).noneMatch(matchesAgreement(input));
    }

    private static Predicate<ApprovalOptions> matchesAgreement(String input) {
        return agreement -> agreement.name().equals(input);
    }
}
