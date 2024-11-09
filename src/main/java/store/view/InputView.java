package store.view;

import static store.constants.InputViewRequest.ITEMS;
import static store.constants.InputViewRequest.KEEP_GOING;
import static store.constants.InputViewRequest.MEMBER_SHIP_DISCOUNT;
import static store.constants.InputViewRequest.ONE_MORE_FOR_PROMOTION;
import static store.constants.InputViewRequest.UN_DISCOUNTED_PURCHASE;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.Item;

public class InputView {

    private static final String YES = "Y";
    private static final String NO = "N";

    private InputView() {
    }

    public static List<Item> readItems() {
        print(ITEMS.toString());
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

    public static boolean askWantToKeepGoing() {
        print(KEEP_GOING.toString());
        return askYesOrNo();
    }

    public static boolean checkOneMoreForPromotion(String itemName) {
        print("현재 " + itemName + ONE_MORE_FOR_PROMOTION);
        return askYesOrNo();
    }

    public static boolean checkUnDiscountedPromotionPurchase(String itemName, long quantity) {
        print("현재 " + itemName + " " + quantity + UN_DISCOUNTED_PURCHASE);
        return askYesOrNo();
    }

    public static boolean checkMembershipDiscount() {
        print(MEMBER_SHIP_DISCOUNT.toString());
        return askYesOrNo();
    }

    private static boolean askYesOrNo() {
        String yesOrNo = null;
        while (yesOrNo == null) {
            yesOrNo = getYesOrNo();
        }
        return yesOrNo.equals(YES);
    }

    private static String getYesOrNo() {
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

    private static void print(String view) {
        System.out.println(view);
    }
}
