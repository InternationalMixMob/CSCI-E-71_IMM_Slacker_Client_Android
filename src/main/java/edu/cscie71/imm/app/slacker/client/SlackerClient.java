package edu.cscie71.imm.app.slacker.client;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.NameValuePair;
import java.util.List;
import java.io.IOException;

public class SlackerClient implements ISlackerClient {

    public String postMessage(String token, MessagePost message) throws IOException, ClientProtocolException {
        message.setToken(token);
        String url = "https://slack.com/api/chat.postMessage";
        List<NameValuePair> input = message.toForm();
        return postToURL(url, input);
    }

    private String postToURL(String url, List<NameValuePair> formData)
            throws IOException, ClientProtocolException {
        return Request.Post(url).bodyForm(formData).execute().returnContent().asString();
    }
}
