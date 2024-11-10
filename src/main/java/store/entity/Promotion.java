package store.entity;

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
            throw new IllegalArgumentException("프로모션명은 비어 있을 수 없습니다.");
        }
    }
}
