package store.view;

import static store.constants.ViewLine.ITEMS;
import static store.constants.ViewLine.KEEP_GOING;
import static store.constants.ViewLine.MEMBER_SHIP_DISCOUNT;
import static store.constants.ViewLine.ONE_MORE_FOR_PROMOTION;
import static store.constants.ViewLine.UN_DISCOUNTED_PURCHASE;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.constants.ParseModelRegex;
import store.constants.ViewLine;
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
            String inputItems = Console.readLine();
            validateInputItems(inputItems);
            return parserItems(inputItems);
        } catch (BusinessException e) {
            print(e.getMessage());
            return null;
        }
    }

    private void validateInputItems(String inputItems) {
        if (inputItems.endsWith(ParseModelRegex.COMMA.toString())) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT);
        }
    }

    private List<ItemDto> parserItems(String inputItems) {
        return Arrays.stream(inputItems.split(ParseModelRegex.COMMA.toString()))
                .map(line -> new ItemParser(line).parse())
                .collect(Collectors.toMap(
                        ItemDto::getName,
                        ItemDto::getQuantity,
                        Integer::sum))
                .entrySet().stream()
                .map(entry -> new ItemDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public boolean askWantToKeepGoing() {
        print(KEEP_GOING);
        return askYesOrNo();
    }

    public boolean checkGetOneMoreForFree(String itemName) {
        print(ViewLine.CURRENT + itemName + ONE_MORE_FOR_PROMOTION);
        return askYesOrNo();
    }

    public boolean checkPayFullPriceForSomeQuantities(String itemName, final int quantity) {
        print(ViewLine.CURRENT + itemName + " " + quantity + UN_DISCOUNTED_PURCHASE);
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
