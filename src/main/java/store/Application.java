package store;

import store.core.manager.ConvenienceRunner;

public class Application {
    public static void main(String[] args) {
        try {
            ConvenienceRunner runner = ConvenienceRunner.stock();
            runner.open();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
