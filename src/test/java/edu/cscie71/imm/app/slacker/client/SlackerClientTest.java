package edu.cscie71.imm.app.slacker.client;

import org.junit.*;
import static org.junit.matchers.JUnitMatchers.containsString;

public class SlackerClientTest {

    ISlackerClient slack;
    MessagePost okMsg;
    String okMsgResponse;

    @BeforeClass
    public void setUp() throws Exception {
        slack = new SlackerClient();
        String token = "xoxp-10020492535-10036686290-14227963249-1cb545e1ae";
        String immChannel = "C0AJL0599";
        okMsg = new MessagePost(immChannel, "This is a test.");
        okMsgResponse = slack.postMessage(token, okMsg);
    }

    @Test
    public void okMsgResponseIsOk() throws Exception {
        Assert.assertThat(okMsgResponse, containsString("\"ok\":true"));
    }

    @Test
    public void okMsgResponseContainsMessage() throws Exception {
        Assert.assertThat(okMsgResponse, containsString("\"text\":\"This is a test.\""));
    }

    @AfterClass
    public void tearDown() throws Exception {
        slack = null;
        okMsg = null;
    }
}