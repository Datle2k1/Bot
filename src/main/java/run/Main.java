package run;

import scheduler.ScheduleRequest;
import util.CheckMethod;
import util.CheckURL;

import java.net.URL;
import java.util.Scanner;

public class Main {
    private static ScheduleRequest botControl;
    private static URL url;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input URL : ");
        String inputURL = scanner.nextLine();
        url = CheckURL.checkURL(inputURL);
        System.out.println(url);
        System.out.print("Input Method : ");
        String inputMethod = scanner.nextLine();
        String method = CheckMethod.checkMethod(inputMethod);

        botControl = new ScheduleRequest(url,method);
        botControl.scheduleThread();
    }
}

