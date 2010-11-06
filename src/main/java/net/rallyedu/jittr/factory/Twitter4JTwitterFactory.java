package net.rallyedu.jittr.factory;

import twitter4j.Twitter;
import twitter4j.http.AccessToken;

public class Twitter4JTwitterFactory extends TwitterFactory {

    private static final String CONSUMER_KEY = "GDpdLtJsTJmpx3Ng45lkA";
    private static final String CONSUMER_SECRET = "TYg5i09b4Nok9IoctRCFGQl987s9RELluKQneYHD0U";

    @Override
    public Twitter getTwitter(AccessToken accessToken) {
        return new twitter4j.TwitterFactory().getOAuthAuthorizedInstance(CONSUMER_KEY, CONSUMER_SECRET, accessToken);
    }

}
