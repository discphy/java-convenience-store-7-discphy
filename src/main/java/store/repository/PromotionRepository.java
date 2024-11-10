package store.repository;


import store.entity.Promotion;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PromotionRepository {

    private static final Map<Long, Promotion> store = new HashMap<>();
    private static long sequence = 0L;

    public PromotionRepository() {
        clear();
    }

    public Promotion save(Promotion promotion) {
        promotion.setId(++sequence);
        store.put(promotion.getId(), promotion);
        return promotion;
    }

    public Optional<Promotion> findByName(String name) {
        return store.values().stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findAny();
    }

    public void clear() {
        store.clear();
    }
}
