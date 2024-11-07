package store;

import store.core.ConvenienceRunner;

public class Application {
    public static void main(String[] args) {
        ConvenienceRunner runner = ConvenienceRunner.stock();
        runner.open();
    }
}
