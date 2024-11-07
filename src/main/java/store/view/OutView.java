package store.view;


public class OutView {

    public String printWelcome() {
        return print("안녕하세요. W편의점입니다.");
    }

    public String printProducts(String products) {
        return print("현재 보유하고 있는 상품입니다.\n\n" + products);
    }

    public String print(String view) {
        System.out.println(view);
        return view;
    }
}
