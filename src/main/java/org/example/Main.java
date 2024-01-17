package org.example;

import com.sun.net.httpserver.HttpHandler;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String inputURL = scanner.nextLine().trim();

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        //https://jsonplaceholder.typicode.com/users

//        String inputURL = "https://csp.withgoogle.com/csp/angular.io"; //405
//        String inputURL = "https://ups.analytics.yahoo.com/ups/55973/sync?uid=9060fa68-6eb8-4d23-aaf7-5346f473f54e-tuct87a199b&_origin=1"; //204
//        String inputURL = "https://hk-trc-events.taboola.com/vnzoom-publisher/log/3/supply-feature?route=HK:HK:V&tvi2=5906&tvi48=9598&tvi50=9058&lti=deflated&ri=5d7309d16a0173f97c081d6942e8ae97&sd=v2_94daa36068a920933a429558d7fe78c3_9060fa68-6eb8-4d23-aaf7-5346f473f54e-tuct87a199b_1705393385_1705393385_CAwQlshdGJnb94rRMSABKAMw6QE4uIEQQIOgEEjAjvMDUP___________wFYAGCJAWiI8MPY3I3a9PEBcAE&ui=9060fa68-6eb8-4d23-aaf7-5346f473f54e-tuct87a199b&pi=/threads/can-tim-cach-tai-course-tu-udemy.51162&wi=2679739317661352242&pt=text&vi=1705393384857&d=%7B%22event_type%22%3A%22EXPLORE_MORE%22%2C%22event_state%22%3A%22CLICKABLE%22%2C%22event_value%22%3A%22tblOriginalState%3A%20true%22%2C%22event_msg%22%3A%22back%20button%20enabled%2C%20history%20changed.%22%2C%22event_key%22%3A%22%22%7D&tim=15%3A23%3A15.782&id=7349&llvl=2&cv=20240115-4-RELEASE&"; //204
//        String inputURL = "https://images.taboola.com/taboola/image/fetch/f_jpg%2Cq_auto%2Ch_178%2Cw_320%2Cc_fill%2Cg_faces:auto%2Ce_sharpen/https%3A//vn-z.vn/styles/vn-z-thumb.jpg"; //400
//        String inputURL = "https://vn-z.vn/styles/vn-z-thumb.jpg"; //404
//        String inputURL = "https://www.google.com/ads/ga-audiences?t=sr&aip=1&_r=4&slf_rd=1&v=1&_v=j101&tid=UA-5831155-1&cid=1114205879.1645675946&jid=1234981401&_u=YDFAAAIgAAAAgCgBI~&z=664516008"; //404
//        String inputURL = "https://api.omappapi.com/v2/embed/4555?d=webfx.com"; //401
//        String inputURL = "https://mwzeom.zeotap.com/mw?zpartnerid=1412&env=mWeb&cid=b53c7282da50e070e06de3c8e666773d1f581820352861b9fe7244f075bece6d&gdpr=$0&gdpr_consent=$"; //403
//        String inputURL = "https://static.zdassets.com/web_widget/classic/latest/fda6cd35495c75f83508d9d2e77ee33d.mp3";
//        String inputURL = "https://td.doubleclick.net/td/rul/796001856?random=1705393187848&cv=11&fst=1705393187848&fmt=3&bg=ffffff&guid=ON&async=1&gtm=45be41a0v877914216&gcd=11l1l1l1l1&dma=0&u_w=1536&u_h=864&url=https%3A%2F%2Fwww.geeksforgeeks.org%2Ftop-20-tips-and-tricks-of-android-studio%2F&hn=www.googleadservices.com&frm=0&tiba=Top%2020%20Tips%20and%20Tricks%20of%20Android%20Studio%20-%20GeeksforGeeks&auid=543589667.1703150198&fledge=1&uaa=x86&uab=64&uafvl=Not_A%2520Brand%3B8.0.0.0%7CChromium%3B120.0.6099.217%7CGoogle%2520Chrome%3B120.0.6099.217&uamb=0&uap=Windows&uapv=15.0.0&uaw=0&data=event%3Dgtag.config";

//        httpRequest(inputURL,"GET");
//        httpRequest(inputURL,"POST");
//        httpRequest(inputURL,"PUT");
        httpRequest(inputURL,"DELETE");
    }

    private static void httpRequest(String inputURL, String inputMethod) {
        Boolean sendReq = true;
        while (sendReq){
            try {
                URL url = new URI(inputURL).toURL();
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod(inputMethod);
                httpsURLConnection.setInstanceFollowRedirects(true);

                System.out.println("--- Start Request ---");
                System.out.println("Request Method : " + httpsURLConnection.getRequestMethod());
                System.out.println("Response Code : " + httpsURLConnection.getResponseCode());
                System.out.println("Response Message : " + httpsURLConnection.getResponseMessage());
                System.out.println("Connect Time Out : " + httpsURLConnection.getConnectTimeout());
                System.out.println("Error Stream : " + httpsURLConnection.getErrorStream());
                System.out.println("--- End Request ---\n");
                Thread.sleep(10000);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                System.out.println("Input URL is URL ? " + e.toString());
                break;
            } catch (IllegalArgumentException e){
                System.out.println("Input URL is URL ? " + e.toString());
                break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}