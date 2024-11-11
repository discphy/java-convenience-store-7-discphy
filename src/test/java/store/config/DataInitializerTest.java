package store.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.entity.Promotion;
import store.domain.repository.ProductRepository;
import store.domain.repository.PromotionRepository;

import static org.assertj.core.api.Assertions.assertThat;

class DataInitializerTest {

    ProductRepository productRepository;
    PromotionRepository promotionRepository;
    DataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        promotionRepository = new PromotionRepository();
        dataInitializer = new DataInitializer(productRepository, promotionRepository);
    }

    @Test
    void 데이터_초기_적재_테스트() {
        //when
        dataInitializer.init();

        Promotion promotion = promotionRepository.findByName("탄산2+1").get();

        //then
        assertThat(productRepository.findAll()).hasSizeGreaterThan(10);
        assertThat(promotion.getName()).isEqualTo("탄산2+1");
    }

}