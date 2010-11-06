package net.rallyedu.jittr.servlet;

import net.rallyedu.jittr.factory.TwitterFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test
public class AuthServletTest {

    private TwitterFactory twitterFactory;

    @BeforeMethod
    public void beforeEach() {
        twitterFactory = mock(TwitterFactory.class);
        TwitterFactory.setInstance(twitterFactory);
    }

    @AfterMethod
    public void afterEach() {
        TwitterFactory.setInstance(null);
    }

    public void doGetShouldGenerateRequestTokenGetAuthUrlAndForwardToView() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        Twitter twitter = mock(Twitter.class);
        when(twitterFactory.getTwitter()).thenReturn(twitter);

        RequestToken requestToken = new RequestToken("token", "tokenSecret");
        when(twitter.getOAuthRequestToken()).thenReturn(requestToken);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/jsp/authForm.jsp")).thenReturn(dispatcher);

        new AuthServlet().doGet(request, null);

        verify(session).setAttribute("requestToken", requestToken);
        verify(request).setAttribute("authUrl", requestToken.getAuthorizationURL());
        verify(dispatcher).forward(request, null);
    }

    public void doGetShouldSendErrorIfTwitterExceptionThrown() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Twitter twitter = mock(Twitter.class);
        when(twitterFactory.getTwitter()).thenReturn(twitter);

        String errorMessage = "You done broke it.";
        TwitterException twitterException = new TwitterException(errorMessage, null, SC_NOT_FOUND);
        when(twitter.getOAuthRequestToken()).thenThrow(twitterException);

        new AuthServlet().doGet(request, response);

        verify(response).sendError(SC_NOT_FOUND, errorMessage);
    }

    public void doPostShouldRemoveRequestTokenSetAccessTokenAndForwardToView() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        Twitter twitter = mock(Twitter.class);
        when(twitterFactory.getTwitter()).thenReturn(twitter);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        RequestToken requestToken = new RequestToken("token", "tokenSecret");
        when(session.getAttribute("requestToken")).thenReturn(requestToken);

        String pin = "1234";
        when(request.getParameter("pin")).thenReturn(pin);

        AccessToken accessToken = new AccessToken("token", "tokenSecret");
        when(twitter.getOAuthAccessToken(requestToken, pin)).thenReturn(accessToken);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/jsp/authResults.jsp")).thenReturn(dispatcher);

        new AuthServlet().doPost(request, null);

        verify(session).removeAttribute("requestToken");
        verify(session).setAttribute("accessToken", accessToken);
        verify(dispatcher).forward(request, null);
    }

    public void doPostShouldSendErrorIfExceptionIsThrown() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Twitter twitter = mock(Twitter.class);
        when(twitterFactory.getTwitter()).thenReturn(twitter);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        RequestToken requestToken = new RequestToken("token", "tokenSecret");
        when(session.getAttribute("requestToken")).thenReturn(requestToken);

        String pin = "1234";
        when(request.getParameter("pin")).thenReturn(pin);

        String errorMessage = "You done broke it.";
        TwitterException twitterException = new TwitterException(errorMessage, null, SC_NOT_FOUND);
        when(twitter.getOAuthAccessToken(requestToken, pin)).thenThrow(twitterException);

        new AuthServlet().doPost(request, response);

        verify(session).removeAttribute("requestToken");
        verify(response).sendError(SC_NOT_FOUND, errorMessage);
    }

}
