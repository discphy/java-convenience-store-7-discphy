package store.constant;

public enum FileName {

    PRODUCTS, PROMOTIONS;

    private static final String extension = ".md";

    public String getFileName() {
        return this.name().toLowerCase() + extension;
    }
}
