package edu.cscie71.imm.app.slacker.client;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

public class SlackerClient implements ISlackerClient {

    public String postMessage(String token, MessagePost message) {
        String url = "https://slack.com/api/chat.postMessage";
        message.setToken(token);
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new GsonHttpMessageConverter());
        return rest.postForObject(url, message, String.class);
    }
}
