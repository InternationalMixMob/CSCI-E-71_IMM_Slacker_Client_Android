package edu.cscie71.imm.app.slacker.client;

import org.apache.http.client.ClientProtocolException;
import java.io.IOException;

/**
 * Interface for the Slack plugin to exercise the Slack API.
 */

public interface ISlackerClient {
    /**
     * Posts a message in a Slack channel.
     * @param token: OAuth token
     * @param message: MessagePost object containing channel and message body
     * @return JSON string from Slack API
     * @see <a href="https://api.slack.com/methods/chat.postMessage">chat.postMessage</a>
     */
    String postMessage(String token, MessagePost message);

    /**
     * Retrieves a Slack user's information.
     * @param token: OAuth token
     * @param user: Slack user ID
     * @return JSON string from Slack API
     * @see <a href="https://api.slack.com/methods/users.info">users.info</a>
     */
    String getUserInfo(String token, String user);
}
