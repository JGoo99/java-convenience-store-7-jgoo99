package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.Item;

public class InputView {

    private static final String YES = "Y";
    private static final String NO = "N";

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
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean askWantToKeepGoing() {
        print("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return askYesOrNo();
    }

    private boolean askYesOrNo() {
        String yesOrNo = null;
        while (yesOrNo == null) {
            yesOrNo = getYesOrNo();
        }
        return yesOrNo.equals("Y");
    }

    private String getYesOrNo() {
        String yesOrNo = Console.readLine();
        try {
            validateYesOrNo(yesOrNo);
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return yesOrNo;
    }

    private static void validateYesOrNo(String yesOrNo) {
        if (!(yesOrNo.equals(YES) || yesOrNo.equals(NO))) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT);
        }
    }

    private void print(String view) {
        System.out.println(view);
    }
}
