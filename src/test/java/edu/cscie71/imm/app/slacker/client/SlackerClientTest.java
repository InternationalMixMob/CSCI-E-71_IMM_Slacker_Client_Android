package edu.cscie71.imm.app.slacker.client;

import org.junit.*;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;

import static com.github.restdriver.clientdriver.RestClientDriver.*;

public class SlackerClientTest {

    ISlackerClient mockSlack;
    ISlackerClient realSlack;
    ISlackerClient badURL;
    String token = "xoxp-10020492535-10036686290-14227963249-1cb545e1ae";
    String immTestChannel = "C0F6U0R5E";
    String message = "This is the Android test.";

    @Rule
    public ClientDriverRule clientDriver = new ClientDriverRule();

    @Before
    public void setUp() throws Exception {
        mockSlack = new SlackerClient(clientDriver.getBaseUrl());
        realSlack = new SlackerClient();
        badURL = new SlackerClient("htp://foo.bar.com");
    }

    @After
    public void tearDown() throws Exception {
        mockSlack = null;
        realSlack = null;
        badURL = null;
    }

    @Test
    public void testResponseIsOkAndContainsMessage() throws Exception {
        clientDriver.addExpectation(
                onRequestTo("/api/chat.postMessage").withMethod(Method.POST)
                        .withParam("as_user", "true")
                        .withParam("token", token)
                        .withParam("channel", immTestChannel)
                        .withParam("text", message),
                giveResponse("\"ok\":true,\"text\":\"This is the Android test.\"", "text/plain")
        );
        String okMsgResponse = mockSlack.postMessage(token, immTestChannel, message);
        Assert.assertTrue(okMsgResponse.contains("\"ok\":true"));
        Assert.assertTrue(okMsgResponse.contains("\"text\":\"This is the Android test.\""));
    }

    @Test
    public void testRealResponseIsOKAndContainsMessage() throws Exception {
        String okMsgResponse = realSlack.postMessage(token, immTestChannel, message);
        Assert.assertTrue(okMsgResponse.contains("\"ok\":true"));
        Assert.assertTrue(okMsgResponse.contains("\"text\":\"This is the Android test.\""));
    }

    @Test
    public void testGetUserInfo() throws Exception {
        clientDriver.addExpectation(
                onRequestTo("/api/users.info").withMethod(Method.GET)
                        .withParam("token", token)
                        .withParam("user", "U0A12L68J"),
                giveResponse("\"ok\":true,\"real_name\":\"Jeffry Pincus\"", "text/plain")
        );
        String userInfo = mockSlack.getUserInfo(token, "U0A12L68J");
        Assert.assertTrue(userInfo.contains("\"ok\":true"));
        Assert.assertTrue(userInfo.contains("\"real_name\":\"Jeffry Pincus\""));
    }

    @Test
    public void testRealGetUserInfoIsOKAndContainsMessage() throws Exception {
        String userInfo = realSlack.getUserInfo(token, "U0A12L68J");
        Assert.assertTrue(userInfo.contains("\"ok\":true"));
        Assert.assertTrue(userInfo.contains("\"real_name\":\"Jeffry Pincus\""));
    }

    @Test
    public void test404FromGetRequest() throws Exception {
        clientDriver.addExpectation(
                onRequestTo("/api/users.info").withMethod(Method.GET).withAnyParams(),
                giveEmptyResponse().withStatus(404)
        );
        String userInfo = mockSlack.getUserInfo(token, "U0A12L68J");
        Assert.assertTrue(userInfo.contains("\"ok\":false"));
        Assert.assertTrue(userInfo.contains("\"error\":\"io_exception:"));
    }

    @Test
    public void test404FromPostRequest() throws Exception {
        clientDriver.addExpectation(
                onRequestTo("/api/chat.postMessage").withMethod(Method.POST).withAnyParams(),
                giveEmptyResponse().withStatus(404)
        );
        String userInfo = mockSlack.postMessage(token, immTestChannel, message);
        Assert.assertTrue(userInfo.contains("\"ok\":false"));
        Assert.assertTrue(userInfo.contains("\"error\":\"io_exception:"));
    }

    @Test
    public void testIOExceptionFromGetRequest() throws Exception {
        mockSlack = new SlackerClient("https://testssl-expire.disig.sk/index.en.html");
        String userInfo = mockSlack.getUserInfo(token, "U0A12L68J");
        Assert.assertTrue(userInfo.contains("\"ok\":false"));
        Assert.assertTrue(userInfo.contains("\"error\":\"io_exception:"));
    }

    @Test
    public void testIOExceptionFromPostRequest() throws Exception {
        mockSlack = new SlackerClient("https://testssl-expire.disig.sk/index.en.html");
        String userInfo = mockSlack.postMessage(token, immTestChannel, message);
        Assert.assertTrue(userInfo.contains("\"ok\":false"));
        Assert.assertTrue(userInfo.contains("\"error\":\"io_exception:"));
    }

    @Test
    public void getOAuthToken() throws Exception {
        clientDriver.addExpectation(
                onRequestTo("/api/oauth.access").withMethod(Method.GET)
                        .withParam("client_id", "U123")
                        .withParam("client_secret", "U123")
                        .withParam("code", "X123")
                        .withParam("redirect_uri", "https://localhost"),
                giveResponse("\"access token\":\"xotx-123-123\",\"scope\":\"read\"", "text/plain")
        );
        String oAuthResponse = mockSlack.getOAuthToken("U123", "U123", "X123", "https://localhost");
        Assert.assertTrue(oAuthResponse.contains("\"access token\":\"xotx-123-123\""));
    }

    @Test
    public void testMalformedURL() throws Exception {
        String userInfo = badURL.getUserInfo(token, "U0A12L68J");
        Assert.assertTrue(userInfo.contains("\"ok\":false"));
        Assert.assertTrue(userInfo.contains("\"error\":\"malformed_url: "));
    }
}
