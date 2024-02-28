package bin;

import manager.Manager;
import task.MyBot;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Manager manager = new Manager();
        manager.run();
    }
}

