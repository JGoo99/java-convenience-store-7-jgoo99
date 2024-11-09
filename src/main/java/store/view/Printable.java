package store.view;

import store.constants.ViewLine;

public interface Printable {

    default String print(ViewLine line) {
        return print(line.toString());
    }

    default String print(String view) {
        System.out.println(view);
        return view;
    }
}
