package edu.cscie71.imm.app.slacker.client;

import org.junit.*;

public class SlackerClientTest {

    ISlackerClient slack;
    String token = "xoxp-10020492535-10036686290-14227963249-1cb545e1ae";
    String immTestChannel = "C0F6U0R5E";

    @Before
    public void setUp() throws Exception {
        slack = new SlackerClient();
    }

    @Test
    public void testResponseIsOkAndContainsMessage() throws Exception {
        MessagePost okMsg = new MessagePost(immTestChannel, "This is a test.");
        String okMsgResponse = slack.postMessage(token, okMsg);
        Assert.assertTrue(okMsgResponse.contains("\"ok\":true"));
        Assert.assertTrue(okMsgResponse.contains("\"text\":\"This is a test.\""));
    }

    @Test
    public void testGetUserInfo() throws Exception {
        String userInfo = slack.getUserInfo(token, "U0A12L68J");
        Assert.assertTrue(userInfo.contains("\"ok\":true"));
        Assert.assertTrue(userInfo.contains("\"real_name\":\"Jeffry Pincus\""));
    }

    @After
    public void tearDown() throws Exception {
        slack = null;
    }
}
