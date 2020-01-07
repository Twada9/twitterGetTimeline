package com.example.twitter_timeline_test

import twitter4j.Twitter
import twitter4j.TwitterFactory


class GetTimeline {
    val twitter : Twitter = TwitterFactory.getSingleton()
    val statuses = twitter.getHomeTimeline()

    for (Status status : statuses) {
        System.out.println(status.getUser().getName() + ":" +
                status.getText())
    }
}