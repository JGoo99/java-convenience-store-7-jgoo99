package store.view;

import static store.constants.ViewLine.ITEMS;
import static store.constants.ViewLine.KEEP_GOING;
import static store.constants.ViewLine.MEMBER_SHIP_DISCOUNT;
import static store.constants.ViewLine.ONE_MORE_FOR_PROMOTION;
import static store.constants.ViewLine.UN_DISCOUNTED_PURCHASE;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.Item;
import store.reader.parser.ItemParser;

public class InputView implements Printable {

    private static final String YES = "Y";
    private static final String NO = "N";

    public List<Item> readItems() {
        print(ITEMS);
        List<Item> items = null;
        while (items == null) {
            items = readItemInputs();
        }
        return items;
    }

    private List<Item> readItemInputs() {
        try {
            String itemInput = Console.readLine();
            return Arrays.stream(itemInput.split(","))
                    .map(line -> new ItemParser(line).parse())
                    .toList();
        } catch (BusinessException e) {
            print(e.getMessage());
            return null;
        }
    }

    public boolean askWantToKeepGoing() {
        print(KEEP_GOING);
        return askYesOrNo();
    }

    public boolean checkOneMoreForPromotion(String itemName) {
        print("현재 " + itemName + ONE_MORE_FOR_PROMOTION);
        return askYesOrNo();
    }

    public boolean checkUnDiscountedPromotionPurchase(String itemName, final int quantity) {
        print("현재 " + itemName + " " + quantity + UN_DISCOUNTED_PURCHASE);
        return askYesOrNo();
    }

    public boolean checkMembershipDiscount() {
        print(MEMBER_SHIP_DISCOUNT);
        return askYesOrNo();
    }

    private boolean askYesOrNo() {
        String yesOrNo = null;
        while (yesOrNo == null) {
            yesOrNo = getYesOrNo();
        }
        return yesOrNo.equals(YES);
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

    private void validateYesOrNo(String yesOrNo) {
        if (!(yesOrNo.equals(YES) || yesOrNo.equals(NO))) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT);
        }
    }
}
