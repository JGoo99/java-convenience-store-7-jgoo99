package store;

import store.runner.ConvenienceRunner;

public class Application {
    public static void main(String[] args) {
        ConvenienceRunner runner = ConvenienceRunner.stock();
        runner.open();
    }
}
