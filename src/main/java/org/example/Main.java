package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
//        Scanner scanner = new Scanner(System.in);
//        String inputURL = scanner.nextLine();
//        URL url = new URI(inputURL).toURL();
//        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//        System.out.println(httpURLConnection.getResponseCode());
//        System.out.println(httpURLConnection.getRequestMethod());
//        System.out.println(httpURLConnection.getResponseMessage());

        String urlParameters = "param1=a&param2=b";
        URL url = new URL("https://httpbin.org/post");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setDoOutput(true);

        System.out.println(httpURLConnection.getRequestMethod());
        OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());

        writer.write(urlParameters);
        writer.flush();

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        System.out.println(httpURLConnection.getResponseCode());
        System.out.println(httpURLConnection.getResponseMessage());

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        writer.close();
        reader.close();

//        String urlParameters = "param1=value1&param2=value2";
//        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
//        int postDataLength = postData.length;
//        String request = "https://example.com";
//        URL url = new URL(request);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setDoOutput(true);
//        conn.setInstanceFollowRedirects(false);
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        conn.setRequestProperty("charset", "utf-8");
//        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
//        conn.setUseCaches(false);
//        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
//            wr.write(postData);
//        }
//        int responseCode = conn.getResponseCode();
//        System.out.println("Response Code: " + responseCode);
//        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//            response.append("\n" + inputLine);
//        }
//        in.close();
//        System.out.println(response.toString());
    }
}