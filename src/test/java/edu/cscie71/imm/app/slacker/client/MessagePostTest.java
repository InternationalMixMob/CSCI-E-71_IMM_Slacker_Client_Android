package edu.cscie71.imm.app.slacker.client;

import junit.framework.TestCase;
import org.apache.http.client.fluent.Form;
import org.apache.http.NameValuePair;
import java.util.List;

public class MessagePostTest extends TestCase{

    public MessagePost msg;

    public void setUp() throws Exception {
        msg = new MessagePost("#testchannel", "This is a test.");
    }

    public void tearDown() throws Exception {
        msg = null;
    }

    public void testGetChannel() throws Exception {
        assertEquals("#testchannel", msg.getChannel());
    }

    public void testSetChannel() throws Exception {
        msg.setChannel("#newChannel");
        assertEquals("#newChannel", msg.getChannel());
    }

    public void testGetMessage() throws Exception {
        assertEquals("This is a test.", msg.getText());
    }

    public void testSetMessage() throws Exception {
        msg.setText("New message");
        assertEquals("New message", msg.getText());
    }

    public void testIsAs_user_defaults_true() throws Exception {
        assertTrue(msg.isAs_user());
    }

    public void testSetAs_user() throws Exception {
        msg.setAs_user(false);
        assertFalse(msg.isAs_user());
    }

    public void testSetToken() throws Exception {
        msg.setToken("test-token");
        assertEquals("test-token", msg.getToken());
    }

    public void testToFormWithToken() throws Exception {
        List<NameValuePair> expected = Form.form()
                .add("token", "test-token")
                .add("channel", "#testchannel")
                .add("text", "This is a test.")
                .add("as_user", "true")
                .build();
        msg.setToken("test-token");
        assertTrue(expected.containsAll(msg.toForm()));
        assertTrue(msg.toForm().containsAll(expected));
    }
}
