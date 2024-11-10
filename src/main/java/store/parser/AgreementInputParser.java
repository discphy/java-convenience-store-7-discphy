package store.parser;

import store.constant.AgreementOption;

import java.util.Arrays;
import java.util.function.Predicate;

import static store.constant.ErrorMessage.INVALID_INPUT_AGREEMENT;

public class AgreementInputParser implements InputParser<Boolean> {

    private static AgreementInputParser instance;

    public static AgreementInputParser getInstance() {
        if (instance == null) {
            instance = new AgreementInputParser();
        }

        return instance;
    }

    @Override
    public Boolean parse(String input) {
        validate(input);

        return AgreementOption.Y.name().equals(input);
    }

    private void validate(String input) {
        if (isInvalidAgreement(input)) {
            throw new IllegalArgumentException(INVALID_INPUT_AGREEMENT.message());
        }
    }

    private boolean isInvalidAgreement(String input) {
        return Arrays.stream(AgreementOption.values())
                .noneMatch(matchesAgreement(input));
    }

    private Predicate<AgreementOption> matchesAgreement(String input) {
        return agreement -> agreement.name().equals(input);
    }
}
