package store.vo;

public class OrderMembership {

    private static final double DISCOUNT_RATE = 0.3d;
    private static final long LIMIT_MONEY = 8_000L;

    private boolean isApply = false;

    public OrderMembership(boolean isApply) {
        this.isApply = isApply;
    }

    public static OrderMembership ofNotApply() {
        return new OrderMembership(false);
    }

    public void apply() {
        isApply = true;
    }

    public long membershipDiscountPrice(long money) {
        if (!isApply) {
            return 0;
        }

        return (long) Math.min(money * DISCOUNT_RATE, LIMIT_MONEY);
    }
}
