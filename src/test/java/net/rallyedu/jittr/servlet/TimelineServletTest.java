package net.rallyedu.jittr.servlet;

import org.testng.annotations.Test;
import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test
public class TimelineServletTest {

    @SuppressWarnings("unchecked")
    public void doGetShouldGetHomeTimelineAndForwardToView() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        Twitter twitter = mock(Twitter.class);
        when(request.getAttribute("twitter")).thenReturn(twitter);

        ResponseList<Status> homeTimeline = mock(PagableResponseList.class);
        when(twitter.getHomeTimeline()).thenReturn(homeTimeline);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/jsp/timeline.jsp")).thenReturn(dispatcher);

        new TimelineServlet().doGet(request, null);

        verify(request).setAttribute("timeline", homeTimeline);
        verify(dispatcher).forward(request, null);
    }

    public void doGetShouldSendErrorIfTwitterExceptionThrown() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Twitter twitter = mock(Twitter.class);
        when(request.getAttribute("twitter")).thenReturn(twitter);

        String errorMessage = "You done broke it.";
        TwitterException twitterException = new TwitterException(errorMessage, null, SC_NOT_FOUND);
        when(twitter.getHomeTimeline()).thenThrow(twitterException);

        new TimelineServlet().doGet(request, response);

        verify(response).sendError(SC_NOT_FOUND, errorMessage);
    }
}
