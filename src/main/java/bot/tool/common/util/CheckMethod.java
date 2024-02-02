package bot.tool.common.util;

import bot.tool.bin.Main;

public class CheckMethod {
    Main main = new Main();
    private static String[] Method = {"GET","POST"};
    public static String checkMethod(String method){
        int i =0;
        boolean ck = false;
        while (i<=1){
            if (!Method[i].equals(method)){
                i++;
            } else {
                ck = true;
                break;
            }
        }
        if (ck == false){
            System.out.println("!!! Error Method");
        }
        return method;
    }
}
