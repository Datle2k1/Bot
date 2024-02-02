package bot.tool.common.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CheckURL {
    public static URL checkURL(String inputURL){
        URL url = null;
        try {
            url = new URI(inputURL).toURL();
        } catch (MalformedURLException e) {
            System.out.println(e + "!!! The URL is not in the correct format.");;
        } catch (URISyntaxException e) {
            System.out.println(e + "!!! Input URL could not be parsed as a URI reference.");
        } catch (IllegalArgumentException e){
            System.out.println(e + "!!! Error URL input");
        }
        return url;
    }
}
