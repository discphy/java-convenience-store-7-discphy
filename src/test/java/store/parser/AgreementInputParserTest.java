package store.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ErrorMessage.INVALID_INPUT_AGREEMENT;

class AgreementInputParserTest {

    AgreementInputParser parser;

    @BeforeEach
    void setUp() {
        parser = new AgreementInputParser();
    }

    @ParameterizedTest
    @ValueSource(strings = {"B", "1", ""})
    void Y_N_값이_아니면_예외처리(String input) {
        //when
        assertThatThrownBy(() -> parser.parse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT_AGREEMENT.message());
    }

    @Test
    void Y값이_입력되면_TRUE를_반환() {
        //when
        Boolean yes = parser.parse("Y");

        //then
        assertThat(yes).isTrue();
    }

    @Test
    void N값이_입력되면_FALSE를_반환() {
        //when
        Boolean no = parser.parse("N");

        //then
        assertThat(no).isFalse();
    }

}