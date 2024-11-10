package store.entity;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;
import static store.constant.ErrorMessage.INVALID_PROMOTION_DATE;

public class PromotionDate {

    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionDate(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isValidDateToday() {
        LocalDateTime now = DateTimes.now();

        return startDate.isEqual(now.toLocalDate()) ||
                (startDate.atTime(MIN).isBefore(now) && endDate.atTime(MAX).isAfter(now));
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(INVALID_PROMOTION_DATE.message());
        }
    }
}
