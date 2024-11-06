package store.view;

import java.util.List;
import store.dto.Product;

public class OutView {

    public String printWelcome() {
        return print("안녕하세요. W편의점입니다.");
    }

    public String printProducts(List<Product> products) {
        StringBuilder sb = new StringBuilder("현재 보유하고 있는 상품입니다.\n\n");
        products.forEach(x -> sb.append("- ").append(x).append("\n"));
        return print(String.valueOf(sb));
    }

    public String print(String view) {
        System.out.println(view);
        return view;
    }
}
