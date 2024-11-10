package store.dto;

import store.entity.Promotion;
import store.entity.PromotionCount;
import store.entity.PromotionDate;

import java.time.LocalDate;
import java.util.Map;

import static store.utils.MapUtils.getInt;
import static store.utils.MapUtils.getLocalDate;

public class PromotionInitDto {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private PromotionInitDto(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PromotionInitDto from(Map<String, String> promotion) {
        return new PromotionInitDto(promotion.get("name"),
                getInt(promotion, "buy"),
                getInt(promotion, "get"),
                getLocalDate(promotion, "start_date"),
                getLocalDate(promotion, "end_date"));
    }

    public Promotion toEntity() {
        return new Promotion(name, new PromotionCount(buy, get), new PromotionDate(startDate, endDate));
    }

}
