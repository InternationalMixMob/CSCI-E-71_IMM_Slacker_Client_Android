package edu.cscie71.imm.app.slacker.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SlackerClient implements ISlackerClient {

    public String postMessage(String token, MessagePost message) throws IOException {
        message.setToken(token);
        URL url = new URL("https://slack.com/api/chat.postMessage");
        String input = message.toJson();
        return postToURL(url, input);
    }

    private String postToURL(URL url, String input) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        //System.out.println("Connection created.");

        OutputStream payload = connection.getOutputStream();
        payload.write(input.getBytes());
        payload.flush();
        //System.out.println("Input has been posted.");

        BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String responseLine;
        String wholeResponse = "";
        while ((responseLine = response.readLine()) != null) {
            //System.out.println("Response line: " + responseLine);
            wholeResponse += responseLine;
        }

        connection.disconnect();
        //System.out.println("Connection closed.");
        return wholeResponse;
    }
}
