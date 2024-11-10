package store;

import store.core.manager.ConvenienceRunner;
import store.exception.BusinessException;

public class Application {
    public static void main(String[] args) {
        try {
            ConvenienceRunner runner = ConvenienceRunner.stock();
            runner.open();
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
        }
    }
}
