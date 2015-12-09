package edu.cscie71.imm.app.slacker.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class SlackerClient implements ISlackerClient {

    private static final String MESSAGE = "/api/chat.postMessage";
    private static final String USER_DATA = "/api/users.info";
    private static final String OATH_TOKEN = "/api/oauth.access";
    private static final String CHANNELS_LIST = "/api/channels.list";
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
        Map<String, String> payload = new LinkedHashMap<String, String>();
        payload.put("token", token);
        payload.put("channel", channel);
        payload.put("text", message);
        payload.put("as_user", "true");
        return makePostRequest(BASE_URL + MESSAGE, payload);
    }

    /**
     * @inheritDoc
     */
    public String getUserInfo(String token, String user) {
        Map<String, String> query = new LinkedHashMap<String, String>();
        query.put("token", token);
        query.put("user", user);
        return makeGetRequest(BASE_URL + USER_DATA, query);
    }

    /**
     * @inheritDoc
     */
    public String getOAuthToken(String clientId, String clientSecret, String code, String redirectUri) {
        Map<String, String> query = new LinkedHashMap<String, String>();
        query.put("client_id", clientId);
        query.put("client_secret", clientSecret);
        query.put("code", code);
        query.put("redirect_uri", redirectUri);
        return makeGetRequest(BASE_URL + OATH_TOKEN, query);
    }

    public String getChannelList(String token, boolean excludeArchivedChannels) {
        Map<String, String> query = new LinkedHashMap<String, String>();
        query.put("token", token);
        if (excludeArchivedChannels) {
            query.put("exclude_archived", "1");
        }
        return makeGetRequest(BASE_URL + CHANNELS_LIST, query);
    }

    private String makeGetRequest(String url, Map<String, String> query) {
        try {
            String queryString = urlEncode(query);
            URL getURL = new URL(url + "?" + queryString);
            HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader response = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            StringBuilder sb = new StringBuilder();
            String nextLine = "";
            while ((nextLine = response.readLine()) != null) {
                sb.append(nextLine);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            return "{\"ok\":false}";
        } catch (MalformedURLException e) {
            return "{\"ok\":false,\"error\":\"malformed_url: " + e.getMessage() + "\"}";
        } catch (IOException e) {
            return "{\"ok\":false,\"error\":\"io_exception: " + e.getMessage() + "\"}";
        }
    }

    private String makePostRequest(String url, Map<String, String> payload) {
        try {
            String formEncoded = urlEncode(payload);
            URL postURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            OutputStream toServer = connection.getOutputStream();
            toServer.write(formEncoded.getBytes());
            toServer.flush();
            BufferedReader response = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            StringBuilder sb = new StringBuilder();
            String nextLine = "";
            while ((nextLine = response.readLine()) != null) {
                sb.append(nextLine);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            return "{\"ok\":false}";
        } catch (MalformedURLException e) {
            return "{\"ok\":false,\"error\":\"malformed_url: " + e.getMessage() + "\"}";
        } catch (IOException e) {
            return "{\"ok\":false,\"error\":\"io_exception: " + e.getMessage() + "\"}";
        }
    }

    private static String urlEncode(Map<String, String> payload) throws UnsupportedEncodingException {
        StringBuilder querySB = new StringBuilder();
        for (Map.Entry<String, String> entry : payload.entrySet()) {
            if (querySB.length() > 0) {
                querySB.append("&");
            }
            querySB.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return querySB.toString();
    }
}
