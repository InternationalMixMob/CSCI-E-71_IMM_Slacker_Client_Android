package edu.cscie71.imm.app.slacker.client;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;

import java.util.List;
import java.io.IOException;
import java.net.URLEncoder;

public class SlackerClient implements ISlackerClient {

    private static final String MESSAGE = "/chat.postMessage";
    private static final String USER_DATA = "/api/users.info";
    private final String BASE_URL;

    public SlackerClient() {
        BASE_URL = "https://slack.com/api";
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
        Form form = Form.form();
        form.add("token", token).add("user", user);
        String queryString = "?token=".concat(URLEncoder.encode(token))
                .concat("&user=").concat(URLEncoder.encode(user));
        return getFromURL(BASE_URL.concat(USER_DATA).concat(queryString));
    }

    private String getFromURL(String url) {
        Request toSend = Request.Get(url);
        try {
            return toSend.execute().returnContent().asString();
        }
        catch (ClientProtocolException e) {
            return "{\"ok\":false,\"error\":\"client_protocol_exception: " + e.getMessage() + "\"}";
        }
        catch (IOException e) {
            return "{\"ok\":false,\"error\":\"io_exception: " + e.getMessage() + "\"}";
        }
    }

    private String postToURL(String url) {
        Request toSend = Request.Post(url);
        try {
            return toSend.execute().returnContent().asString();
        }
        catch (ClientProtocolException e) {
            return "{\"ok\":false,\"error\":\"client_protocol_exception: " + e.getMessage() + "\"}";
        }
        catch (IOException e) {
            return "{\"ok\":false,\"error\":\"io_exception: " + e.getMessage() + "\"}";
        }
    }
}
