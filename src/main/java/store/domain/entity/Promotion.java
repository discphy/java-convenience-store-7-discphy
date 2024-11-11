package store.domain.entity;

import store.domain.vo.PromotionCount;
import store.domain.vo.PromotionDate;

import static store.common.constant.ErrorMessage.NOT_BLANK_PROMOTION_NAME;

public class Promotion {

    private Long id;
    private final String name;
    private final PromotionCount count;
    private final PromotionDate date;

    public Promotion(String name, PromotionCount count, PromotionDate date) {
        validateName(name);

        this.name = name;
        this.count = count;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PromotionCount getCount() {
        return count;
    }

    public boolean isValid() {
        return date.isValidDateToday();
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_PROMOTION_NAME.message());
        }
    }
}
