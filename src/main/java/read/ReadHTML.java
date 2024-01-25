package read;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import request.RequestOKHttp;

import java.io.IOException;
import java.net.URL;

public class ReadHTML {
    public static String readHTML(URL url) {
        Connection connect = Jsoup.connect(String.valueOf(url));
        Document doc;
        try {
            doc = connect.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String resp = doc.body().text();
        return resp;
    }
}
