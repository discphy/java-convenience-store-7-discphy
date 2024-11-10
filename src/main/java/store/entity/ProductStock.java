package store.entity;

import static store.constant.ErrorMessage.INVALID_PRODUCT_STOCK;

public class ProductStock {

    private final int generalStock;
    private final int promotionStock;

    public ProductStock(int generalStock, int promotionStock) {
        validateStock(generalStock);
        validateStock(promotionStock);

        this.generalStock = generalStock;
        this.promotionStock = promotionStock;
    }

    public int getTotalStock() {
        return generalStock + promotionStock;
    }

    public int getGeneralStock() {
        return generalStock;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public ProductStock add(ProductStock anotherStock) {
        return new ProductStock(this.generalStock + anotherStock.generalStock, this.promotionStock + anotherStock.promotionStock);
    }

    public ProductStock update(int updateGeneralStock, int updatePromotionStock) {
        return new ProductStock(this.generalStock - updateGeneralStock, this.promotionStock - updatePromotionStock);
    }

    private void validateStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException(INVALID_PRODUCT_STOCK.message());
        }
    }
}
