package edu.cscie71.imm.app.slacker.client;

import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;

public class MessagePost {
    private String channel;
    private String text;
    private boolean as_user = true;
    private String token;

    public MessagePost(String channel, String text) {
        this.channel = channel;
        this.text = text;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isAs_user() {
        return as_user;
    }

    public void setAs_user(boolean as_user) {
        this.as_user = as_user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<NameValuePair> toForm() {
        Form form = Form.form();
        if (this.token != null) {
            form.add("token", this.token);
        }
        form.add("channel", this.channel)
                .add("text", this.text)
                .add("as_user", Boolean.toString(this.as_user));
        return form.build();
    }
}