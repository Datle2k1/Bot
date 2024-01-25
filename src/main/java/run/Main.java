package run;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import scheduler.ScheduleRequest;
import util.CheckMethod;
import util.CheckURL;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    //https://msh-jfrog.sohatv.vn/ui/native/vcc-viva-ads-gradle-release-local/com/google/auto/auto-common/0.10/auto-common-0.10.pom
    private static ScheduleRequest schedule;
    private static URL url;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        //Nhập kiểm tra url
        System.out.print("Input URL : ");
        String inputURL = scanner.nextLine();
        url = CheckURL.checkURL(inputURL);
        //Nhập kiểm tra method
        System.out.print("Input Method : ");
        String inputMethod = scanner.nextLine();
        String method = CheckMethod.checkMethod(inputMethod);
        //Xếp lịch cho request
        schedule = new ScheduleRequest(url,method);
        schedule.scheduleThread();
    }
}

