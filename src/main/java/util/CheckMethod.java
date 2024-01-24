package util;

public class CheckMethod {
    private static String[] Method = {"GET","POST","PUT","DELETE","PATCH","HEAD"};
    public static String checkMethod(String method){
        int i =0;
        boolean ck = false;
        while (i<=5){
            if (!Method[i].equals(method)){
                i++;
            } else {
                ck = true;
                break;
            }
        }
        if (ck == false){System.out.println("!!! Error Method");}
        return method;
    }
}
