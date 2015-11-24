package edu.cscie71.imm.app.slacker.client;

import org.junit.*;
import static org.junit.matchers.JUnitMatchers.containsString;

public class SlackerClientTest {

    ISlackerClient slack;
    MessagePost okMsg;
    String okMsgResponse;

    @Before
    public void setUp() throws Exception {
        slack = new SlackerClient();
        String token = "xoxp-10020492535-10036686290-14227963249-1cb545e1ae";
        String immTestChannel = "C0F6U0R5E";
        okMsg = new MessagePost(immTestChannel, "This is a test.");
        okMsgResponse = slack.postMessage(token, okMsg);
    }

    @Test
    public void okMsgResponseIsOkAndContainsMessage() throws Exception {
        Assert.assertThat(okMsgResponse, containsString("\"ok\":true"));
        Assert.assertThat(okMsgResponse, containsString("\"text\":\"This is a test.\""));
    }

    @After
    public void tearDown() throws Exception {
        slack = null;
        okMsg = null;
    }
}