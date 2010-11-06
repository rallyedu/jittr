package net.rallyedu.jittr.factory;

import twitter4j.Twitter;
import twitter4j.http.AccessToken;

public abstract class TwitterFactory {

    private static TwitterFactory instance;

    public static synchronized TwitterFactory getInstance() {
        if(instance == null) {
            instance = new Twitter4JTwitterFactory();
        }
        return instance;
    }

    public static synchronized void setInstance(TwitterFactory instance) {
        TwitterFactory.instance = instance;
    }

    public Twitter getTwitter() {
        return getTwitter(null);
    }

    public abstract Twitter getTwitter(AccessToken accessToken);

}
