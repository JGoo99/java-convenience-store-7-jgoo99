package store.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.exception.BusinessException;
import store.repository.ProductRepository;

public class Item {

    private static final String REGEX = "\\[([가-힣]+)-([0-9]+)\\]";

    private final Product product;
    private long quantity;

    private Item(Product product, long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static Item from(String itemInput) {
        Matcher matcher = Pattern.compile(REGEX).matcher(itemInput);
        if (matcher.find()) {
            Product product = findProduct(matcher.group(1));
            return new Item(product, Long.parseLong(matcher.group(2)));
        }
        throw new BusinessException("구매 정보가 유효하지 않습니다.");
    }

    public static Product findProduct(String itemInput) {
        Product product = ProductRepository.getInstance().findByName(itemInput);
        if (product == null) {
            throw new BusinessException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
        return product;
    }

    public boolean checkQuantity() {
        return product.checkQuantity(quantity);
    }

    public void buy() {
        product.buy(quantity);
    }

    @Override
    public String toString() {
        return product.getName();
    }

    public void plusQuantity() {
        this.quantity++;
    }
}
