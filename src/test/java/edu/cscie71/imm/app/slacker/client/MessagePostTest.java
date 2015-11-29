package edu.cscie71.imm.app.slacker.client;

import org.junit.*;

import org.apache.http.client.fluent.Form;
import org.apache.http.NameValuePair;
import java.util.List;

public class MessagePostTest {

    public MessagePost msg;

    @Before
    public void setUp() throws Exception {
        msg = new MessagePost("#testchannel", "This is a test.");
    }

    @Test
    public void tearDown() throws Exception {
        msg = null;
    }

    @Test
    public void testGetChannel() throws Exception {
        Assert.assertEquals("#testchannel", msg.getChannel());
    }

    @Test
    public void testSetChannel() throws Exception {
        msg.setChannel("#newChannel");
        Assert.assertEquals("#newChannel", msg.getChannel());
    }

    @Test
    public void testGetMessage() throws Exception {
        Assert.assertEquals("This is a test.", msg.getText());
    }

    @Test
    public void testSetMessage() throws Exception {
        msg.setText("New message");
        Assert.assertEquals("New message", msg.getText());
    }

    @Test
    public void testIsAs_user_defaults_true() throws Exception {
        Assert.assertTrue(msg.isAs_user());
    }

    @Test
    public void testSetAs_user() throws Exception {
        msg.setAs_user(false);
        Assert.assertFalse(msg.isAs_user());
    }

    @Test
    public void testSetToken() throws Exception {
        msg.setToken("test-token");
        Assert.assertEquals("test-token", msg.getToken());
    }

    @Test
    public void testToFormWithToken() throws Exception {
        List<NameValuePair> expected = Form.form()
                .add("token", "test-token")
                .add("channel", "#testchannel")
                .add("text", "This is a test.")
                .add("as_user", "true")
                .build();
        msg.setToken("test-token");
        Assert.assertTrue(expected.containsAll(msg.toForm()));
        Assert.assertTrue(msg.toForm().containsAll(expected));
    }
}
