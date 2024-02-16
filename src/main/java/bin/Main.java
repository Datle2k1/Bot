package bin;

import common.model.Request;
import common.util.Utility;
import manager.Manager;
import task.SendRequest;

import java.net.URL;
import java.util.Scanner;

public class Main {
    private static URL url;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        //Nhập kiểm tra url
        System.out.print("Input URL : ");
        String inputURL = scanner.nextLine().trim();
        if(!Utility.isUrlValidFormat(inputURL)) {
            System.out.println("ERROR");
            return;
        }
        System.out.println("SUCCESS");
        Request request = new Request(inputURL);

        Manager manager = new Manager(20);
        manager.run(request);
    }
}

