package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import store.exception.BusinessException;
import store.model.Item;

public class InputView {

    public List<Item> readItems() {
        print("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        List<Item> items = null;
        while (items == null) {
            items = readItemInputs();
        }
        return items;
    }

    private static List<Item> readItemInputs() {
        try {
            String itemInput = Console.readLine();
            return Arrays.stream(itemInput.split(","))
                    .map(Item::from)
                    .toList();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean checkQuantity(Item item) {
        print("현재 " + item + "은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        String yesOrNo = getYesOrNo();
        return yesOrNo.equals("Y");
    }

    private String getYesOrNo() {
        String yesOrNo = Console.readLine();
        validateYesOrNo(yesOrNo);
        return yesOrNo;
    }

    private void validateYesOrNo(String yesOrNo) {
        if (!(yesOrNo.equals("Y") || yesOrNo.equals("N"))) {
            throw new BusinessException("");
        }
    }

    private void print(String view) {
        System.out.println(view);
    }
}
