package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
//    private static final String GET_URL = "https://jsonplaceholder.typicode.com/users";
//    private static final String GET_URL = "https://my-json-server.typicode.com/typicode/demo/posts";
    private static final String GET_URL = "https://static.xx.fbcdn.net/rsrc.php/v3i-MW4/yk/l/makehaste_jhash/rvp5xpafJN7.js?_nc_x=I-cKk9FkQ98";
    private static final String POST_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String PUT_URL = "https://jsonplaceholder.typicode.com/users/10";
    private static final String PATCH_URL = "https://jsonplaceholder.typicode.com/users/10";
    private static final String DELETE_URL = "https://jsonplaceholder.typicode.com/users/9";

    public static void main(String[] args) throws IOException, InterruptedException {
        sendGET();
        sendPOST();
        sendPUT();
        sendPATCH();
        sendDELETE();
    }

    private static void sendDELETE() throws IOException, InterruptedException {
        System.out.println("DELETE REQUEST --->");
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(DELETE_URL))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.print("Request Code: ");
        System.out.println(response.statusCode());
        System.out.print("Request Method: ");
        System.out.println(request.method());

        //get all response header
        Map<String, List<String>> map = response.headers().map();
        System.out.println("-----Response Header-----");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " ,Value : " + entry.getValue());
        }
        System.out.println("-----Response Header-----\n");

        //get all content response
        System.out.println("-----Response Body-----");
        System.out.println(response.body());
        System.out.println("-----Response Body-----\n");
        System.out.println("--- DELETE REQUEST <---");
    }
    private static void sendPATCH() throws IOException, InterruptedException {
        System.out.println("PATCH REQUEST --->");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PATCH_URL))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString("{\"name\": \"BMW X5 M Sport\"}"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.print("Request Code: ");
        System.out.println(response.statusCode());
        System.out.print("Request Method: ");
        System.out.println(request.method());

        //get all response header
        Map<String, List<String>> map = response.headers().map();
        System.out.println("-----Response Header-----");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " ,Value : " + entry.getValue());
        }
        System.out.println("-----Response Header-----\n");

        //get all content response
        System.out.println("-----Response Body-----");
        System.out.println(response.body());
        System.out.println("-----Response Body-----\n");
        System.out.println("--- PATCH REQUEST <---");
    }
    private static void sendPUT() throws IOException, InterruptedException {
        System.out.println("PUT REQUEST --->");
        String requestBody = " {\n" +
                "        \"id\": 10,\n" +
                "        \"name\": \"Roronoa Zoro\",\n" +
                "        \"username\": \"Bret\",\n" +
                "        \"email\": \"Sincere@april.biz\",\n" +
                "        \"address\": {\n" +
                "            \"street\": \"Kulas Light\",\n" +
                "            \"suite\": \"Apt. 556\",\n" +
                "            \"city\": \"Gwenborough\",\n" +
                "            \"zipcode\": \"92998-3874\",\n" +
                "            \"geo\": {\n" +
                "                \"lat\": \"-37.3159\",\n" +
                "                \"lng\": \"81.1496\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"phone\": \"1-770-736-8031 x56442\",\n" +
                "        \"website\": \"hildegard.org\",\n" +
                "        \"company\": {\n" +
                "            \"name\": \"Romaguera-Crona\",\n" +
                "            \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "            \"bs\": \"harness real-time e-markets\"\n" +
                "        }\n" +
                "    }";
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(PUT_URL))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.print("Request Code: ");
        System.out.println(response.statusCode());
        System.out.print("Request Method: ");
        System.out.println(request.method());

        //get all response header
        Map<String, List<String>> map = response.headers().map();
        System.out.println("-----Response Header-----");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " ,Value : " + entry.getValue());
        }
        System.out.println("-----Response Header-----\n");

        //get all content response
        System.out.println("-----Response Body-----");
        System.out.println(response.body());
        System.out.println("-----Response Body-----\n");
        System.out.println("--- PUT REQUEST <---");
    }

    private static void sendGET() throws IOException, InterruptedException {
        System.out.println("GET REQUEST --->");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GET_URL))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.print("Request Code: ");
        System.out.println(response.statusCode());
        System.out.print("Request Method: ");
        System.out.println(request.method());

        //get all response header
        Map<String, List<String>> map = response.headers().map();
        System.out.println("-----Response Header-----");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " ,Value : " + entry.getValue());
        }
        System.out.println("-----Response Header-----\n");

        //get all content response
        System.out.println("-----Response Body-----");
        System.out.println(response.body());
        System.out.println("-----Response Body-----\n");
        System.out.println("--- GET REQUEST <---\n");
    }

    private static void sendPOST() throws IOException, InterruptedException {
        System.out.println("POST REQUEST --->");
        String requestBody = " {\n" +
                "        \"id\": 11,\n" +
                "        \"name\": \"Krishna Rungta\",\n" +
                "        \"username\": \"Bret\",\n" +
                "        \"email\": \"Sincere@april.biz\",\n" +
                "        \"address\": {\n" +
                "            \"street\": \"Kulas Light\",\n" +
                "            \"suite\": \"Apt. 556\",\n" +
                "            \"city\": \"Gwenborough\",\n" +
                "            \"zipcode\": \"92998-3874\",\n" +
                "            \"geo\": {\n" +
                "                \"lat\": \"-37.3159\",\n" +
                "                \"lng\": \"81.1496\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"phone\": \"1-770-736-8031 x56442\",\n" +
                "        \"website\": \"hildegard.org\",\n" +
                "        \"company\": {\n" +
                "            \"name\": \"Romaguera-Crona\",\n" +
                "            \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "            \"bs\": \"harness real-time e-markets\"\n" +
                "        }\n" +
                "    }";
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(POST_URL))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.print("Request Code: ");
        System.out.println(response.statusCode());
        System.out.print("Request Method: ");
        System.out.println(request.method());

        //get all response header
        Map<String, List<String>> map = response.headers().map();
        System.out.println("-----Response Header-----");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " ,Value : " + entry.getValue());
        }
        System.out.println("-----Response Header-----\n");

        //get all content response
        System.out.println("-----Response Body-----");
        System.out.println(response.body());
        System.out.println("-----Response Body-----\n");
        System.out.println("--- POST REQUEST <---");
    }

}