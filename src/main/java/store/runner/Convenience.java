package store.runner;

import java.util.List;
import store.model.Item;
import store.model.PosMachine;
import store.model.Product;

public class Convenience {

    private final List<Product> products;
    private final PosMachine pos;
    private List<Item> items;

    private Convenience(List<Product> products) {
        this.products = products;
        this.pos = new PosMachine();
    }

    public static Convenience stockProduct(ConvenienceCreator creator) {
        return new Convenience(creator.getProducts());
    }

    public void buy(List<Item> items) {
        initToPos(items);

        /*
        1. 프로모션 할인
        2. 멤버십 할인
         */
    }

    public String getProductsStatus() {
        StringBuilder sb = new StringBuilder();
        products.forEach(x -> sb.append("- ").append(x).append("\n"));
        return sb.toString();
    }

    private void initToPos(List<Item> items) {
        this.items = items;
    }
}
