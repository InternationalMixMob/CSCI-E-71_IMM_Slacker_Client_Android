package edu.cscie71.imm.app.slacker.client;

import org.apache.http.client.ClientProtocolException;
import java.io.IOException;

/**
 * Interface for the Slack plugin to exercise the Slack API.
 */

public interface ISlackerClient {
    String postMessage(String token, MessagePost message) throws IOException, ClientProtocolException;
}
