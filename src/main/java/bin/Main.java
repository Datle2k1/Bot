package bin;

import manager.Manager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Manager manager = new Manager();
        manager.init();
        manager.run();
    }
}

