package edu.cscie71.imm.app.slacker.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SlackerClient implements ISlackerClient {

    private static final String MESSAGE = "/api/chat.postMessage";
    private static final String USER_DATA = "/api/users.info";
    private static final String OATH_TOKEN = "/api/oauth.access";
    private final String BASE_URL;

    public SlackerClient() {
        BASE_URL = "https://slack.com";
    }

    protected SlackerClient(String baseURL) {
        BASE_URL = baseURL;
    }

    /**
     * @inheritDoc
     */
    public String postMessage(String token, String channel, String message) {
        String queryString = "?token=" + URLEncoder.encode(token)
                + "&channel=" + URLEncoder.encode(channel)
                + "&text=" + URLEncoder.encode(message)
                + "&as_user=" + "true";
        return makeRestTransaction(BASE_URL + MESSAGE + queryString, "POST");
    }

    /**
     * @inheritDoc
     */
    public String getUserInfo(String token, String user) {
        String queryString = "?token=" + URLEncoder.encode(token)
                + "&user=" + URLEncoder.encode(user);
        return makeRestTransaction(BASE_URL + USER_DATA + queryString, "GET");
    }

    /**
     * @inheritDoc
     */
    public String getOAuthToken(String clientId, String clientSecret, String code, String redirectUri) {
        String queryString = "?client_id=" + URLEncoder.encode(clientId)
                + "&client_secret=" + URLEncoder.encode(clientSecret)
                + "&code=" + URLEncoder.encode(code)
                + "&redirect_uri=" + URLEncoder.encode(redirectUri);
        return makeRestTransaction(BASE_URL + OATH_TOKEN + queryString, "GET");
    }

    private String makeRestTransaction(String url, String method) {
        try {
            URL getURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod(method);
            BufferedReader response = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            StringBuilder sb = new StringBuilder();
            String nextLine = "";
            while ((nextLine = response.readLine()) != null) {
                sb.append(nextLine);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            return "{\"ok\":false,\"error\":\"malformed_url: " + e.getMessage() + "\"}";
        } catch (IOException e) {
            return "{\"ok\":false,\"error\":\"io_exception: " + e.getMessage() + "\"}";
        }
    }
}
