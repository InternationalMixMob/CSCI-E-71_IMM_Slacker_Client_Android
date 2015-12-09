package edu.cscie71.imm.app.slacker.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
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
        Map<String, String> payload = new HashMap<String, String>();
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

    public String getChannelList(String token, boolean excludeArchivedChannels) {
        String queryString = "?token=" + URLEncoder.encode(token);
        if (excludeArchivedChannels) {
            queryString += "&exclude_archived=1";
        }
        return makeRestTransaction(BASE_URL + CHANNELS_LIST + queryString, "GET");
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
            StringBuilder slackJsonSB = new StringBuilder();
            String nextLine = "";
            while ((nextLine = response.readLine()) != null) {
                slackJsonSB.append(nextLine);
            }
            return slackJsonSB.toString();
        } catch (UnsupportedEncodingException e) {
            return "{\"ok\":false}";
        } catch (MalformedURLException e) {
            return "{\"ok\":false,\"error\":\"malformed_url: " + e.getMessage() + "\"}";
        } catch (IOException e) {
            return "{\"ok\":false,\"error\":\"io_exception: " + e.getMessage() + "\"}";
        }
    }

    private String urlEncode(Map<String, String> payload) throws UnsupportedEncodingException {
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
