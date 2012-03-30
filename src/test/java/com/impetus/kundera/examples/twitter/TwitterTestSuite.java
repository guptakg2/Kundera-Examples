/*
 * Copyright 2011 Impetus Infotech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.impetus.kundera.examples.twitter;

import java.util.List;

import junit.framework.TestCase;

import com.impetus.kundera.examples.twitter.dao.Twitter;
import com.impetus.kundera.examples.twitter.dao.TwitterService;
import com.impetus.kundera.examples.twitter.entities.ExternalLink;
import com.impetus.kundera.examples.twitter.entities.PersonalDetail;
import com.impetus.kundera.examples.twitter.entities.Preference;
import com.impetus.kundera.examples.twitter.entities.Tweet;
import com.impetus.kundera.examples.twitter.entities.User;

/**
 * Test case for MongoDB.
 * 
 * @author amresh.singh
 */
public class TwitterTestSuite extends TestCase
{

    /** The user id1. */
    String userId1;

    /** The user id2. */
    String userId2;

    /** The twitter. */
    protected Twitter twitter;

    /**
     * Sets the up internal.
     * 
     * @param persistenceUnitName
     *            the new up internal
     * @throws Exception
     *             the exception
     */
    protected void setUpInternal(String persistenceUnitName) throws Exception
    {
        userId1 = "0001";
        userId2 = "0002";

        twitter = new TwitterService(persistenceUnitName);

    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    /**
     * Tear down internal.
     * 
     * @throws Exception
     *             the exception
     */
    protected void tearDownInternal() throws Exception
    {
        if (twitter != null)
        {
            twitter.close();
        }
    }

    /**
     * Execute suite.
     */
    protected void executeTestSuite()
    {
        /*
         * addUsers(); addTweets(); savePreference(); addExternalLinks();
         * user1FollowsUser2(); getAllTweets();
         */

         //addAllUserInfo();
         getUserById();
        //mergeUser();
        //removeUser();
        // getAllUsers();
        // getAllTweets();
    }

    protected void addAllUserInfo()
    {
        User user1 = buildUser1();
        User user2 = buildUser2();

        twitter.addUser(user1);
        twitter.addUser(user2);
    }

    protected void getUserById()
    {
        User user1 = twitter.findUserById(userId1);
        System.out.println(user1);
        User user2 = twitter.findUserById(userId2);
        System.out.println(user2);
    }
    
    protected void removeUser() {
        User user1 = twitter.findUserById(userId2);
        twitter.removeUser(user1);
        System.out.println("Removed");
    }
    
    protected void mergeUser() {
        User user1 = twitter.findUserById(userId1);
        
        //EM is closed now, so all entities should be in detached state
        user1.setPersonalDetail(new PersonalDetail("Vivek", "sdgfsdfsdfd", "Married"));
        twitter.mergeUser(user1);
    }
    
    

    protected void getAllUsers()
    {
        List<User> users = twitter.getAllUsers();
        System.out.println(users);
    }

    /**
     * Adds the users.
     */
    protected void addUsers()
    {
        twitter.addUser(userId1, "Amresh", "password1", "married");
        twitter.addUser(userId2, "Saurabh", "password2", "single");
    }

    /**
     * Save preference.
     */
    protected void savePreference()
    {
        twitter.savePreference(userId1, new Preference("P1", "Motif", "2"));
        twitter.savePreference(userId2, new Preference("P2", "High Contrast", "3"));
    }

    /**
     * Adds the external links.
     */
    protected void addExternalLinks()
    {
        twitter.addExternalLink(userId1, "L1", "Facebook", "http://facebook.com/coolnerd");
        twitter.addExternalLink(userId1, "L2", "LinkedIn", "http://linkedin.com/in/devilmate");

        twitter.addExternalLink(userId2, "L3", "GooglePlus", "http://plus.google.com/inviteme");
        twitter.addExternalLink(userId2, "L4", "Yahoo", "http://yahoo.com/profiles/itsmeamry");
    }

    /**
     * Adds the tweets.
     */
    protected void addTweets()
    {
        twitter.addTweet(userId1, "Here is my first tweet", "Web");
        twitter.addTweet(userId1, "Second Tweet from me", "Mobile");

        twitter.addTweet(userId2, "Saurabh tweets for the first time", "Phone");
        twitter.addTweet(userId2, "Another tweet from Saurabh", "text");
    }

    /**
     * User1 follows user2.
     */
    protected void user1FollowsUser2()
    {
        twitter.startFollowing(userId1, userId2);
    }

    /**
     * User1 adds user2 as follower.
     */
    protected void user1AddsUser2AsFollower()
    {
        twitter.addFollower(userId1, userId2);
    }

    /**
     * Gets the all tweets.
     * 
     * @return the all tweets
     */
    protected void getAllTweets()
    {
        List<Tweet> tweetsUser1 = twitter.getAllTweets(userId1);
        List<Tweet> tweetsUser2 = twitter.getAllTweets(userId2);

        assertNotNull(tweetsUser1);
        assertNotNull(tweetsUser2);

        assertFalse(tweetsUser1.isEmpty());
        assertFalse(tweetsUser2.isEmpty());

        assertEquals(2, tweetsUser1.size());
        assertEquals(2, tweetsUser2.size());
    }

    /**
     * Gets the all followers.
     * 
     * @return the all followers
     */
    protected void getAllFollowers()
    {
        List<User> follower1 = twitter.getFollowers(userId1);
        List<User> follower2 = twitter.getFollowers(userId2);

        assertNull(follower1);
        assertNotNull(follower2);
    }

    /**
     * @return
     */
    private User buildUser2()
    {
        User user2 = new User(userId2, "Saurabh", "password2", "single");

        user2.setPreference(new Preference("P2", "High Contrast", "3"));

        user2.addExternalLink(new ExternalLink("L3", "GooglePlus", "http://plus.google.com/inviteme"));
        user2.addExternalLink(new ExternalLink("L4", "Yahoo", "http://yahoo.com/profiles/itsmeamry"));

        user2.addTweet(new Tweet("Saurabh tweets for the first time", "Phone"));
        user2.addTweet(new Tweet("Another tweet from Saurabh", "text"));
        return user2;
    }

    /**
     * @return
     */
    private User buildUser1()
    {
        User user1 = new User(userId1, "Amresh", "password1", "married");

        user1.setPreference(new Preference("P1", "Motif", "2"));

        user1.addExternalLink(new ExternalLink("L1", "Facebook", "http://facebook.com/coolnerd"));
        user1.addExternalLink(new ExternalLink("L2", "LinkedIn", "http://linkedin.com/in/devilmate"));

        user1.addTweet(new Tweet("Here is my first tweet", "Web"));
        user1.addTweet(new Tweet("Second Tweet from me", "Mobile"));
        return user1;
    }
}
