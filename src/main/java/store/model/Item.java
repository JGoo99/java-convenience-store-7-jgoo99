package store.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.exception.BusinessException;

public class Item {

    private static final String REGEX = "\\[([가-힣]+)-([0-9]+)\\]";

    private final String name;
    private final long quantity;

    private Item(String name, long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static Item from(String itemInput) {
        Matcher matcher = Pattern.compile(REGEX).matcher(itemInput);
        if (matcher.find()) {
            return new Item(matcher.group(1), Long.parseLong(matcher.group(2)));
        }
        throw new BusinessException("구매 정보가 유효하지 않습니다.");
    }

    public String getName() {
        return name;
    }

    public long getQuantity() {
        return quantity;
    }
}
