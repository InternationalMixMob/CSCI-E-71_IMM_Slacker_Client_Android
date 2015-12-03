package edu.cscie71.imm.app.slacker.client;

/**
 * Interface for the Slack plugin to exercise the Slack API.
 */

public interface ISlackerClient {
    /**
     * Posts a message in a Slack channel.
     * @param token: OAuth token
     * @param channel: Slack channel ID to post message to.
     * @param message: Message text to post in channel.
     * @return JSON string from Slack API
     * @see <a href="https://api.slack.com/methods/chat.postMessage">chat.postMessage</a>
     */
    String postMessage(String token, String channel, String message);

    /**
     * Retrieves a Slack user's information.
     * @param token: OAuth token
     * @param user: Slack user ID
     * @return JSON string from Slack API
     * @see <a href="https://api.slack.com/methods/users.info">users.info</a>
     */
    String getUserInfo(String token, String user);

    /**
     * Get OAuth token from Slack.
     * @param clientId ID of application
     * @param clientSecret Secret of application
     * @param code User login code
     * @param redirectUri Redirect URL of the application
     * @return JSON string from Slack API
     * @see <a href="https://api.slack.com/docs/oauth">OAuth</a>
     */
    String getOAuthToken(String clientId, String clientSecret, String code, String redirectUri);

    /**
     * Get list of team's channels from Slack.
     * @param token: OAuth token
     * @param excludeArchivedChannels: Filters out archived channels when true.
     * @return JSON string from Slack API
     * @see <a href="https://api.slack.com/methods/channels.list">channels.list</a>
     */
    String getChannelList(String token, boolean excludeArchivedChannels);
}
