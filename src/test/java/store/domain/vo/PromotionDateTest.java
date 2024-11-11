package store.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.common.constant.ErrorMessage.INVALID_PROMOTION_DATE;

class PromotionDateTest {

    @ParameterizedTest
    @MethodSource
    void 시작날짜가_종료일자_보다_이후이면_예외_발생(LocalDate startDate, LocalDate endDate) {
        //when
        assertThatThrownBy(() -> new PromotionDate(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PROMOTION_DATE.message());
    }

    static Stream<Arguments> 시작날짜가_종료일자_보다_이후이면_예외_발생() {
        return Stream.of(
                Arguments.of(LocalDate.MAX, LocalDate.MIN),
                Arguments.of(LocalDate.of(2024, 1, 1), LocalDate.of(2023, 12, 31)),
                Arguments.of(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 10, 1))
        );
    }

}