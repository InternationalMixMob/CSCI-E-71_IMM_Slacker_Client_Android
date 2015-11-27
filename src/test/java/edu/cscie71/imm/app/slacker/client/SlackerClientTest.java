package edu.cscie71.imm.app.slacker.client;

import junit.framework.TestCase;

public class SlackerClientTest extends TestCase {

    ISlackerClient slack;
    MessagePost okMsg;
    String okMsgResponse;

    public void setUp() throws Exception {
        slack = new SlackerClient();
        String token = "xoxp-10020492535-10036686290-14227963249-1cb545e1ae";
        String immTestChannel = "C0F6U0R5E";
        okMsg = new MessagePost(immTestChannel, "This is a test.");
        okMsgResponse = slack.postMessage(token, okMsg);
    }

    public void testOkMsgIsNotOK() throws Exception {
        assertTrue(okMsgResponse.equals("{\"ok\":false,\"error\":\"not_authed\"}"));
    }

    public void tearDown() throws Exception {
        slack = null;
        okMsg = null;
    }
}