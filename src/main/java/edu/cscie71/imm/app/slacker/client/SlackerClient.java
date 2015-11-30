package edu.cscie71.imm.app.slacker.client;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Form;

import java.io.IOException;
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
        String queryString = "?token=".concat(URLEncoder.encode(token))
                .concat("&channel=").concat(URLEncoder.encode(channel))
                .concat("&text=").concat(URLEncoder.encode(message))
                .concat("&as_user=").concat("true");
        return postToURL(BASE_URL.concat(MESSAGE).concat(queryString));
    }

    /**
     * @inheritDoc
     */
    public String getUserInfo(String token, String user) {
        String queryString = "?token=".concat(URLEncoder.encode(token))
                .concat("&user=").concat(URLEncoder.encode(user));
        return getFromURL(BASE_URL.concat(USER_DATA).concat(queryString));
    }

    /**
     * @inheritDoc
     */
    public String getOAuthToken(String clientId, String clientSecret, String code, String redirectUri) {
        String queryString = "?client_id=".concat(URLEncoder.encode(clientId))
                .concat("&client_secret=").concat(URLEncoder.encode(clientSecret))
                .concat("&code=").concat(URLEncoder.encode(code))
                .concat("&redirect_uri=").concat(URLEncoder.encode(redirectUri));
        return getFromURL(BASE_URL.concat(OATH_TOKEN).concat(queryString));
    }

    private String getFromURL(String url) {
        Request toSend = Request.Get(url);
        try {
            return toSend.execute().returnContent().asString();
        } catch (ClientProtocolException e) {
            return "{\"ok\":false,\"error\":\"client_protocol_exception: " + e.getMessage() + "\"}";
        } catch (IOException e) {
            return "{\"ok\":false,\"error\":\"io_exception: " + e.getMessage() + "\"}";
        }
    }

    private String postToURL(String url) {
        Request toSend = Request.Post(url);
        try {
            return toSend.execute().returnContent().asString();
        } catch (ClientProtocolException e) {
            return "{\"ok\":false,\"error\":\"client_protocol_exception: " + e.getMessage() + "\"}";
        } catch (IOException e) {
            return "{\"ok\":false,\"error\":\"io_exception: " + e.getMessage() + "\"}";
        }
    }
}
