package store.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.entity.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void 상품_저장() {
        //given
        ProductInfo productInfo = new ProductInfo("콜라", 1_000L);
        ProductStock productStock = new ProductStock(10, 10);
        Product product = new Product(productInfo, productStock, null);

        //when
        Product saveProduct = productRepository.save(product);

        //then
        assertThat(saveProduct.getInfo()).isEqualTo(product.getInfo());
        assertThat(saveProduct.getStock()).isEqualTo(product.getStock());
        assertThat(saveProduct.getPromotion()).isEqualTo(product.getPromotion());
    }

    @Test
    void 상품_전체_조회() {
        //given
        Product product1 = new Product(new ProductInfo("콜라", 1_000L), new ProductStock(10, 10), null);
        Product product2 = new Product(new ProductInfo("오렌지주스", 1_800L), new ProductStock(1, 1), null);

        productRepository.save(product1);
        productRepository.save(product2);

        //when
        List<Product> products = productRepository.findAll();

        //then
        assertThat(products).hasSize(2);
        assertThat(products).contains(product1, product2);
    }

    @Test
    void 상품_ID_조회() {
        //given
        ProductInfo productInfo = new ProductInfo("콜라", 1_000L);
        ProductStock productStock = new ProductStock(10, 10);
        Product product = new Product(productInfo, productStock, null);

        Product saveProduct = productRepository.save(product);

        //when
        Product findProduct = productRepository.findById(saveProduct.getId()).get();

        //then
        assertThat(findProduct).isEqualTo(saveProduct);
        assertThat(findProduct.getId()).isEqualTo(saveProduct.getId());
        assertThat(findProduct.getInfo()).isEqualTo(product.getInfo());
        assertThat(findProduct.getStock()).isEqualTo(product.getStock());
        assertThat(findProduct.getPromotion()).isEqualTo(product.getPromotion());
    }

    @Test
    void 상품_이름_조회() {
        //given
        ProductInfo productInfo = new ProductInfo("콜라", 1_000L);
        ProductStock productStock = new ProductStock(10, 10);
        Product product = new Product(productInfo, productStock, null);

        Product saveProduct = productRepository.save(product);

        //when
        Product findProduct = productRepository.findByName(saveProduct.getInfo().getName()).get();

        //then
        assertThat(findProduct).isEqualTo(saveProduct);
        assertThat(findProduct.getId()).isEqualTo(saveProduct.getId());
        assertThat(findProduct.getInfo()).isEqualTo(product.getInfo());
        assertThat(findProduct.getStock()).isEqualTo(product.getStock());
        assertThat(findProduct.getPromotion()).isEqualTo(product.getPromotion());
    }

    @Test
    void 상품_재고_수정() {
        //given
        ProductInfo productInfo = new ProductInfo("콜라", 1_000L);
        ProductStock productStock = new ProductStock(10, 10);
        Product product = new Product(productInfo, productStock, null);

        productRepository.save(product);

        //when
        productRepository.updateStock(product.getId(), new ProductStock(1, 2));
        Product findProduct = productRepository.findById(product.getId()).get();

        //then
        assertThat(findProduct.getStock().getGeneralStock()).isEqualTo(1);
        assertThat(findProduct.getStock().getPromotionStock()).isEqualTo(2);
    }

    @Test
    void 상품_프로모션_수정() {
        //given
        ProductInfo productInfo = new ProductInfo("콜라", 1_000L);
        ProductStock productStock = new ProductStock(10, 10);
        Product product = new Product(productInfo, productStock, null);

        productRepository.save(product);

        //when
        Promotion promotion = new Promotion("1+1", new PromotionCount(1, 1), new PromotionDate(LocalDate.MIN, LocalDate.MAX));
        productRepository.updatePromotion(product.getId(), promotion);
        Product findProduct = productRepository.findById(product.getId()).get();

        //then
        assertThat(findProduct.getPromotion()).isEqualTo(promotion);
        assertThat(findProduct.getPromotion().getCount().buyCount()).isEqualTo(1);
        assertThat(findProduct.getPromotion().getCount().getCount()).isEqualTo(1);
        assertThat(findProduct.getPromotion().getName()).isEqualTo("1+1");
    }
}