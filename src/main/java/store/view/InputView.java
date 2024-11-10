package store.view;

import static store.constants.ViewLine.ITEMS;
import static store.constants.ViewLine.KEEP_GOING;
import static store.constants.ViewLine.MEMBER_SHIP_DISCOUNT;
import static store.constants.ViewLine.ONE_MORE_FOR_PROMOTION;
import static store.constants.ViewLine.UN_DISCOUNTED_PURCHASE;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import store.exception.BusinessException;
import store.exception.ErrorMessage;
import store.model.ItemDto;
import store.reader.parser.ItemParser;

public class InputView implements Printable {

    private static final String YES = "Y";
    private static final String NO = "N";

    public List<ItemDto> readItems() {
        print(ITEMS);
        List<ItemDto> itemDtos = null;
        while (itemDtos == null) {
            itemDtos = readItemInputs();
        }
        return itemDtos;
    }

    private List<ItemDto> readItemInputs() {
        try {
            Map<String, Integer> itemQuantities = new HashMap<>();

            String itemInput = Console.readLine();
            Arrays.stream(itemInput.split(","))
                    .forEach(line -> {

                        ItemDto itemDto = new ItemParser(line).parse();
                        int prev = itemQuantities.getOrDefault(itemDto.getName(), 0);
                        itemQuantities.put(itemDto.getName(), itemDto.getQuantity() + prev);
                    });

            return itemQuantities.entrySet().stream()
                    .map(entry -> new ItemDto(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        } catch (BusinessException e) {
            print(e.getMessage());
            return null;
        }
    }

    public boolean askWantToKeepGoing() {
        print(KEEP_GOING);
        return askYesOrNo();
    }

    public boolean checkGetOneMoreForFree(String itemName) {
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
