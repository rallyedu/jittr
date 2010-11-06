package net.rallyedu.jittr.servlet;

import org.testng.annotations.Test;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test
public class TweetServletTest {

    public void doPostShouldUpdateStatusAndRedirectToView() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Twitter twitter = mock(Twitter.class);
        when(request.getAttribute("twitter")).thenReturn(twitter);

        String text = "tweet";
        when(request.getParameter("text")).thenReturn(text);

        new TweetServlet().doPost(request, response);

        verify(twitter).updateStatus(text);
        verify(response).sendRedirect("timeline");
    }

    public void doPostShouldSendErrorIfTwitterExceptionThrown() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Twitter twitter = mock(Twitter.class);
        when(request.getAttribute("twitter")).thenReturn(twitter);

        String text = "tweet";
        when(request.getParameter("text")).thenReturn(text);
        
        String errorMessage = "You done broke it.";
        TwitterException twitterException = new TwitterException(errorMessage, null, SC_NOT_FOUND);
        when(twitter.updateStatus(text)).thenThrow(twitterException);

        new TweetServlet().doPost(request, response);

        verify(response).sendError(SC_NOT_FOUND, errorMessage);
    }
}
