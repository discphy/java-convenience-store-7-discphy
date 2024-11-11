package store.common.parser;

import store.common.constant.ApprovalOptions;

import java.util.Arrays;
import java.util.function.Predicate;

import static store.common.constant.ErrorMessage.INVALID_INPUT_APPROVAL;

public class ApprovalParser {

    public static Boolean parse(String input) {
        validate(input);

        return ApprovalOptions.Y.name().equals(input);
    }

    private static void validate(String input) {
        if (isInvalidApproval(input)) {
            throw new IllegalArgumentException(INVALID_INPUT_APPROVAL.message());
        }
    }

    private static boolean isInvalidApproval(String input) {
        return Arrays.stream(ApprovalOptions.values()).noneMatch(matchesApproval(input));
    }

    private static Predicate<ApprovalOptions> matchesApproval(String input) {
        return approval -> approval.name().equals(input);
    }
}
