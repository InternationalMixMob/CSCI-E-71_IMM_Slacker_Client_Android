package edu.cscie71.imm.app.slacker.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessagePostTest {

    public MessagePost msg;

    @Before
    public void setUp() throws Exception {
        msg = new MessagePost("#testchannel", "This is a test.");
    }

    @After
    public void tearDown() throws Exception {
        msg = null;
    }

    @Test
    public void testGetChannel() throws Exception {
        assertEquals("#testchannel", msg.getChannel());
    }

    @Test
    public void testSetChannel() throws Exception {
        msg.setChannel("#newChannel");
        assertEquals("#newChannel", msg.getChannel());
    }

    @Test
    public void testGetMessage() throws Exception {
        assertEquals("This is a test.", msg.getMessage());
    }

    @Test
    public void testSetMessage() throws Exception {
        msg.setMessage("New message");
        assertEquals("New message", msg.getMessage());
    }

    @Test
    public void testIsAs_user_defaults_true() throws Exception {
        assertTrue(msg.isAs_user());
    }

    @Test
    public void testSetAs_user() throws Exception {
        msg.setAs_user(false);
        assertFalse(msg.isAs_user());
    }

    @Test
    public void testSetToken() throws Exception {
        msg.setToken("test-token");
        assertEquals("test-token", msg.getToken());
    }
}