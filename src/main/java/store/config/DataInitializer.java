package store.config;

import store.common.constant.FileName;
import store.domain.dto.ProductInitDto;
import store.domain.dto.PromotionInitDto;
import store.domain.entity.Product;
import store.domain.vo.ProductStock;
import store.domain.entity.Promotion;
import store.common.utils.FileMapper;
import store.common.utils.FileReader;
import store.domain.repository.ProductRepository;
import store.domain.repository.PromotionRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static store.common.constant.ErrorMessage.NOT_EXIST_PROMOTION;
import static store.common.constant.FileName.PRODUCTS;
import static store.common.constant.FileName.PROMOTIONS;

public class DataInitializer {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public DataInitializer(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public void init() {
        initPromotions();
        initProducts();
    }

    private void initPromotions() {
        readMap(PROMOTIONS).stream()
                .map(PromotionInitDto::from)
                .map(PromotionInitDto::toEntity)
                .forEach(promotionRepository::save);
    }

    private void initProducts() {
        readMap(PRODUCTS).stream()
                .map(ProductInitDto::from)
                .map(this::toProductEntity)
                .forEach(this::mergeProduct);
    }

    private Product toProductEntity(ProductInitDto productInitDto) {
        Promotion promotion = getPromotion(productInitDto.getPromotionName());

        if (promotion == null) {
            return productInitDto.toEntity(new ProductStock(productInitDto.getQuantity(), 0), null);
        }

        return productInitDto.toEntity(new ProductStock(0, productInitDto.getQuantity()), promotion);
    }

    private Promotion getPromotion(String promotionName) {
        if (promotionName == null) {
            return null;
        }

        return promotionRepository.findByName(promotionName)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_PROMOTION.message()));
    }

    private void mergeProduct(Product product) {
        Optional<Product> findProduct = productRepository.findByName(product.getInfo().getName());

        if (findProduct.isPresent()) {
            update(product, findProduct.get());
            return;
        }

        productRepository.save(product);
    }

    private void update(Product product, Product mergeProduct) {
        if (product.getPromotion() != null) {
            productRepository.updatePromotion(mergeProduct.getId(), product.getPromotion());
        }

        productRepository.updateStock(mergeProduct.getId(), mergeProduct.getStock().add(product.getStock()));
    }

    private List<Map<String, String>> readMap(FileName fileName) {
        return map(read(fileName));
    }

    private List<Map<String, String>> map(List<String> lines) {
        return FileMapper.map(lines);
    }

    private List<String> read(FileName fileName) {
        return FileReader.read(fileName);
    }
}
