package store.view;


import store.core.Receipt;

public class OutView {

    public String printWelcome() {
        return print("안녕하세요. W편의점입니다.");
    }

    public String printProducts(String products) {
        return print("현재 보유하고 있는 상품입니다.\n\n" + products);
    }

    public String printReceipt(Receipt receipt) {
        StringBuffer sb = new StringBuffer("==============W 편의점================\n");
        sb.append(String.format("%-11s\t\t%-10s\t%s\n", "상품명", "수량", "금액"));
        sb.append(receipt.printPurchasedItems());
        sb.append("=============증\t\t정===============\n");
        sb.append(receipt.printFreeItems());
        sb.append("=====================================\n");
        sb.append(receipt.printTotalAmount() + "\n");
        sb.append(receipt.printFreeAmount() + "\n");
        sb.append(receipt.printMembershipDiscountedAmount() + "\n");
        sb.append(receipt.printTotalPayment() + "\n");
        return print(sb.toString());
    }

    private String print(String view) {
        System.out.println(view);
        return view;
    }
}
