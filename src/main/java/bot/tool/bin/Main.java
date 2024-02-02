package bot.tool.bin;

import bot.tool.common.model.ResponseResult;
import bot.tool.manager.ScheduleThread;
import bot.tool.common.util.CheckMethod;
import bot.tool.common.util.CheckURL;
import bot.tool.task.SendRequest;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static ScheduleThread scheduleThread;
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
        scheduleThread = new ScheduleThread(new SendRequest(url,method));
        scheduleThread.scheduleThread();
    }
}

