package common.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class Utility {
    public static boolean isUrlValidFormat(String url) {
        String regex = "^(http|Http|https?|Https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return url.matches(regex);
    }
}
