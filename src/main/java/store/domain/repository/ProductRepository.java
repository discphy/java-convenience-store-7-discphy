package store.domain.repository;


import store.domain.entity.Product;
import store.domain.vo.ProductStock;
import store.domain.entity.Promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static store.common.constant.ErrorMessage.NOT_EXIST_PRODUCT;

public class ProductRepository {

    private static final Map<Long, Product> store = new HashMap<>();
    private static long sequence = 0L;

    public ProductRepository() {
        clear();
    }

    public Product save(Product product) {
        product.setId(++sequence);
        store.put(product.getId(), product);
        return product;
    }

    public List<Product> findAll() {
        return List.copyOf(store.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Product> findByName(String name) {
        return store.values().stream()
                .filter(product -> product.getInfo().getName().equals(name))
                .findAny();
    }

    public void updateStock(Long id, ProductStock updateStock) {
        Product product = findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_PRODUCT.message()));
        product.setStock(updateStock);
    }

    public void updatePromotion(Long id, Promotion promotion) {
        Product product = findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_PRODUCT.message()));
        product.setPromotion(promotion);
    }

    public void clear() {
        store.clear();
    }
}
