package bin;

import task.MyBot;
import manager.Manager;

public class Main {
    public static void main(String[] args){
        //Tạo và chạy bot
        System.out.println("--- Create Bot ---");
        MyBot.createBot();
        //Tạo đối tượng quản lý xếp lịch
        Manager manager = new Manager(20);
        manager.run();
    }
}

