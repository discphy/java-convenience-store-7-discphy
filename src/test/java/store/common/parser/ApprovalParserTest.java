package store.common.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.common.constant.ErrorMessage.INVALID_INPUT_APPROVAL;

class ApprovalParserTest {

    @ParameterizedTest
    @ValueSource(strings = {"B", "1", ""})
    void Y_N_값이_아니면_예외처리(String input) {
        //when
        assertThatThrownBy(() -> ApprovalParser.parse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT_APPROVAL.message());
    }

    @Test
    void Y값이_입력되면_TRUE를_반환() {
        //when
        Boolean yes = ApprovalParser.parse("Y");

        //then
        assertThat(yes).isTrue();
    }

    @Test
    void N값이_입력되면_FALSE를_반환() {
        //when
        Boolean no = ApprovalParser.parse("N");

        //then
        assertThat(no).isFalse();
    }

}