package edu.cscie71.imm.app.slacker.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SlackerClientTest {

    ISlackerClient slack;

    @Before
    public void setUp() throws Exception {
        slack = new SlackerClient();
    }

    @After
    public void tearDown() throws Exception {
        slack = null;
    }

    @Test
    public void testPostMessage() throws Exception {
        Assert.assertTrue(slack.postMessage("", null));
    }
}