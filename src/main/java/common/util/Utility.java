package common.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Utility {
    public static Boolean checkURL(String inputURL){
        try {
            new URI(inputURL).toURL();
        } catch (MalformedURLException e) {
            System.out.println(e + "!!! The URL is not in the correct format.");
            return false;
        } catch (URISyntaxException e) {
            System.out.println(e + "!!! Input URL could not be parsed as a URI reference.");
            return false;
        } catch (IllegalArgumentException e){
            System.out.println(e + "!!! Error URL input");
            return false;
        }
        return true;
    }

    public static boolean isUrlValidFormat(String url) {
        String regex = "^(http|Http|https?|Https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return url.matches(regex);
    }
}
