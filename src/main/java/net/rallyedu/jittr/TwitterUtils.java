package net.rallyedu.jittr;

import twitter4j.Twitter;
import twitter4j.http.AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TwitterUtils {
    public static final String ACCESS_TOKEN_ATTRIBUTE = "accessToken";
    public static final String TWITTER_ATTRIBUTE = "twitter";

    public static AccessToken getAccessToken(HttpSession session) {
        return (AccessToken) session.getAttribute(ACCESS_TOKEN_ATTRIBUTE);
    }

    public static void setAccessToken(HttpSession session, AccessToken accessToken) {
        session.setAttribute(ACCESS_TOKEN_ATTRIBUTE, accessToken);
    }

    public static Twitter getTwitter(HttpServletRequest request) {
        return (Twitter) request.getAttribute(TWITTER_ATTRIBUTE);
    }

    public static void setTwitter(HttpServletRequest request, Twitter twitter) {
        request.setAttribute(TWITTER_ATTRIBUTE, twitter);
    }
}
