package store.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.exception.BusinessException;
import store.repository.ProductQuantityRepository;

public class Item {

    private static final String REGEX = "\\[([가-힣]+)-([0-9]+)\\]";

    private final String productName;
    private long quantity;

    public Item(String productName, long quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public static Item from(String itemInput) {
        Matcher matcher = Pattern.compile(REGEX).matcher(itemInput);
        if (matcher.find()) {
            String productName = matcher.group(1);
            long quantity = Long.parseLong(matcher.group(2));

            validate(productName, quantity);
            return new Item(productName, quantity);
        }
        throw new BusinessException("잘못된 입력입니다. 다시 입력해 주세요.");
    }

    private static void validate(String productName, long quantity) {
        if (quantity <= 0) {
            throw new BusinessException("잘못된 입력입니다. 다시 입력해 주세요.");
        }
        Long productQuantity = ProductQuantityRepository.getInstance().findByName(productName);
        if (productQuantity == null) {
            throw new BusinessException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
        if (productQuantity < quantity) {
            throw new BusinessException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public String getProductName() {
        return productName;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return productName;
    }
}
