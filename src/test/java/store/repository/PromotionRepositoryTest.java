package store.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.entity.Promotion;
import store.entity.PromotionCount;
import store.entity.PromotionDate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromotionRepositoryTest {

    PromotionRepository promotionRepository;

    @BeforeEach
    void setUp() {
        promotionRepository = new PromotionRepository();
    }

    @Test
    void 프로모션_저장() {
        //given
        PromotionCount promotionCount = new PromotionCount(2, 1);
        PromotionDate promotionDate = new PromotionDate(LocalDate.MIN, LocalDate.MAX);
        Promotion promotion = new Promotion("2+1", promotionCount, promotionDate);

        //when
        Promotion save = promotionRepository.save(promotion);

        //then
        assertThat(save.getName()).isEqualTo(promotion.getName());
        assertThat(save.getCount()).isEqualTo(promotion.getCount());
        assertThat(save.isValid()).isTrue();
    }

    @Test
    void 프로모션_이름조회() {
        //given
        PromotionCount promotionCount = new PromotionCount(2, 1);
        PromotionDate promotionDate = new PromotionDate(LocalDate.MIN, LocalDate.MAX);
        Promotion promotion = new Promotion("2+1", promotionCount, promotionDate);

        Promotion save = promotionRepository.save(promotion);

        //when
        Promotion findPromotion = promotionRepository.findByName("2+1").get();

        //then
        assertThat(findPromotion.getId()).isEqualTo(save.getId());
        assertThat(findPromotion.getName()).isEqualTo(save.getName());
        assertThat(findPromotion.getCount()).isEqualTo(save.getCount());
        assertThat(findPromotion.isValid()).isTrue();
    }

}