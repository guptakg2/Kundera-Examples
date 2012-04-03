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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.Metamodel;

import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;

import junit.framework.TestCase;

import com.impetus.kundera.PersistenceProperties;
import com.impetus.kundera.examples.cli.CassandraCli;
import com.impetus.kundera.examples.cli.HBaseCli;
import com.impetus.kundera.examples.twitter.dao.Twitter;
import com.impetus.kundera.examples.twitter.dao.TwitterService;
import com.impetus.kundera.examples.twitter.entities.ExternalLink;
import com.impetus.kundera.examples.twitter.entities.PersonalDetail;
import com.impetus.kundera.examples.twitter.entities.Preference;
import com.impetus.kundera.examples.twitter.entities.Tweet;
import com.impetus.kundera.examples.twitter.entities.User;
import com.impetus.kundera.metadata.model.EntityMetadata;
import com.impetus.kundera.metadata.model.KunderaMetadata;
import com.impetus.kundera.metadata.model.MetamodelImpl;
import com.impetus.kundera.metadata.model.PersistenceUnitMetadata;

/**
 * Test case for MongoDB.
 * 
 * @author amresh.singh
 */
<<<<<<< HEAD
public class TwitterTestSuite extends TestCase {

	/** The user id1. */
	String userId1;

	/** The user id2. */
	String userId2;

	/** The twitter. */
	protected Twitter twitter;

	protected static List<Map<Class, String>> combinations = new ArrayList<Map<Class, String>>();

	/**
	 * Sets the up internal.
	 * 
	 * @param persistenceUnitName
	 *            the new up internal
	 * @throws Exception
	 *             the exception
	 */
	protected void setUpInternal(String persistenceUnitName) throws Exception {
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
	protected void tearDownInternal() throws Exception {
		if (twitter != null) {
			twitter.close();
		}
	}

	/**
	 * Execute suite.
	 */
	protected void executeTestSuite() {
		/*
		 * addTweets(); savePreference(); addExternalLinks();
		 * user1FollowsUser2(); getAllTweets();
		 */

		addUsers();
		addAllUserInfo();
		getAllUsers();
		getAllTweets();
	}

	protected void addAllUserInfo() {
		User user1 = new User(userId1, "Amresh", "password1", "married");

		user1.setPreference(new Preference("Motif", "2"));

		user1.addExternalLink(new ExternalLink("Facebook",
				"http://facebook.com/coolnerd"));
		user1.addExternalLink(new ExternalLink("LinkedIn",
				"http://linkedin.com/in/devilmate"));

		user1.addTweet(new Tweet("Here is my first tweet", "Web"));
		user1.addTweet(new Tweet("Second Tweet from me", "Mobile"));

		User user2 = new User(userId2, "Saurabh", "password2", "single");

		user2.setPreference(new Preference("High Contrast", "3"));

		user2.addExternalLink(new ExternalLink("GooglePlus",
				"http://plus.google.com/inviteme"));
		user2.addExternalLink(new ExternalLink("Yahoo",
				"http://yahoo.com/profiles/itsmeamry"));

		user2.addTweet(new Tweet("Saurabh tweets for the first time", "Phone"));
		user2.addTweet(new Tweet("Another tweet from Saurabh", "text"));

		twitter.addUser(user1);
		twitter.addUser(user2);
	}

	protected void getAllUsers() {
		List<User> users = twitter.getAllUsers();
		System.out.println(users);
	}

	/**
	 * Adds the users.
	 */
	protected void addUsers() {
		twitter.addUser(userId1, "Amresh", "password1", "married");
		twitter.addUser(userId2, "Saurabh", "password2", "single");
	}

	/**
	 * Save preference.
	 */
	protected void savePreference() {
		twitter.savePreference(userId1, new Preference("Motif", "2"));
		twitter.savePreference(userId2, new Preference("High Contrast", "3"));
	}

	/**
	 * Adds the external links.
	 */
	protected void addExternalLinks() {
		twitter.addExternalLink(userId1, "Facebook",
				"http://facebook.com/coolnerd");
		twitter.addExternalLink(userId1, "LinkedIn",
				"http://linkedin.com/in/devilmate");

		twitter.addExternalLink(userId2, "GooglePlus",
				"http://plus.google.com/inviteme");
		twitter.addExternalLink(userId2, "Yahoo",
				"http://yahoo.com/profiles/itsmeamry");
	}

	/**
	 * Adds the tweets.
	 */
	protected void addTweets() {
		twitter.addTweet(userId1, "Here is my first tweet", "Web");
		twitter.addTweet(userId1, "Second Tweet from me", "Mobile");

		twitter.addTweet(userId2, "Saurabh tweets for the first time", "Phone");
		twitter.addTweet(userId2, "Another tweet from Saurabh", "text");
	}

	/**
	 * User1 follows user2.
	 */
	protected void user1FollowsUser2() {
		twitter.startFollowing(userId1, userId2);
	}

	/**
	 * User1 adds user2 as follower.
	 */
	protected void user1AddsUser2AsFollower() {
		twitter.addFollower(userId1, userId2);
	}

	/**
	 * Gets the all tweets.
	 * 
	 * @return the all tweets
	 */
	protected void getAllTweets() {
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
	protected void getAllFollowers() {
		List<User> follower1 = twitter.getFollowers(userId1);
		List<User> follower2 = twitter.getFollowers(userId2);

		assertNull(follower1);
		assertNotNull(follower2);
	}
//
//	public void switchPersistentUnits(Map<Class, String> entityPuCol)
//			throws IOException, TException, InvalidRequestException,
//			UnavailableException, TimedOutException,
//			SchemaDisagreementException {
//
//		if (entityPuCol != null) {
//			Iterator<Class> iter = entityPuCol.keySet().iterator();
//			// log.warn("Invocation for:");
//			while (iter.hasNext()) {
//				Class clazz = iter.next();
//				String pu = entityPuCol.get(clazz);
//				// EntityMetadata mAdd = KunderaMetadataManager
//				// .getEntityMetadata(clazz);
//				Map<String, Metamodel> metaModels = KunderaMetadata.INSTANCE
//						.getApplicationMetadata().getMetamodelMap();
//				EntityMetadata mAdd = null;
//				for (Metamodel m : metaModels.values()) {
//					mAdd = ((MetamodelImpl) m).getEntityMetadataMap()
//							.get(clazz);
//					if (mAdd != null) {
//						break;
//					}
//				}
//				mAdd.setPersistenceUnit(pu);
//
//				PersistenceUnitMetadata puMetadata = KunderaMetadata.INSTANCE
//						.getApplicationMetadata()
//						.getPersistenceUnitMetadata(pu);
//
//				String client = puMetadata.getProperties().getProperty(
//						PersistenceProperties.KUNDERA_CLIENT);
//				// if (client.equalsIgnoreCase("pelops")) {
//				// CassandraCli.cassandraSetUp();
//				//
//				// } else if (client.equalsIgnoreCase("hbase")) {
//				// if (!HBaseCli.isStarted()) {
//				// HBaseCli.startCluster();
//				// }
//				String schema = puMetadata
//						.getProperty(PersistenceProperties.KUNDERA_KEYSPACE);
//				// System.out.println(schema);
//				mAdd.setSchema(schema != null ? schema : "tests");
//				// mAdd.setSchema(schema)
//
//				// log.warn("persistence unit:" + pu + "class::"
//				// + clazz.getCanonicalName());
//
//			}
//
//		}
//	}
//
//	public void init(List<Class> classes, String... persistenceUnits) {
//		// list of PUS with class.
//		Map<Class, String> puClazzMapper = null;
//
//		for (String pu : persistenceUnits) {
//			for (String p : persistenceUnits) {
//				puClazzMapper = new HashMap<Class, String>();
//				puClazzMapper.put(classes.get(0), pu);
//
//				for (Class c : classes.subList(1, classes.size())) {
//					puClazzMapper.put(c, p);
//				}
//				combinations.add(puClazzMapper);
//			}
//		}
//	}
//
//	public void operation() {
//		try {
//			for (Map<Class, String> c : combinations) {
//				switchPersistentUnits(c);
//			}
//		} catch (Exception e) {
//		}
//	}
=======
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
         //getUserById();
        //mergeUser();
        removeUser();
        // getAllUsers();
        // getAllTweets();
    }

    protected void addAllUserInfo()
    {
        User user1 = buildUser1();
        User user2 = buildUser2();

        twitter.createEntityManager();
        twitter.addUser(user1);
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        twitter.addUser(user2);
        twitter.closeEntityManager();
    }

    protected void getUserById()
    {
        twitter.createEntityManager();
        User user1 = twitter.findUserById(userId1);
        System.out.println(user1);
        User user2 = twitter.findUserById(userId2);
        twitter.closeEntityManager();
        System.out.println(user2);
    }
    
    protected void removeUser() {
        twitter.createEntityManager();
        User user1 = twitter.findUserById(userId1);
        
        
        twitter.removeUser(user1);
        twitter.closeEntityManager();
        System.out.println("Removed");
    }
    
    protected void mergeUser() {
        twitter.createEntityManager();
        User user1 = twitter.findUserById(userId1);
        twitter.closeEntityManager();
        
        //EM is closed now, so all entities should be in detached state
        twitter.createEntityManager();
        user1.setPersonalDetail(new PersonalDetail("Vivek2", "sdgfsdfsdfd", "Married"));
        twitter.mergeUser(user1);
        twitter.closeEntityManager();
    }
    
    

    protected void getAllUsers()
    {
        twitter.createEntityManager();
        List<User> users = twitter.getAllUsers();
        System.out.println(users);
        twitter.closeEntityManager();
    }

    /**
     * Adds the users.
     */
    protected void addUsers()
    {
        twitter.createEntityManager();
        twitter.addUser(userId1, "Amresh", "password1", "married");
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        twitter.addUser(userId2, "Saurabh", "password2", "single");
        twitter.closeEntityManager();
    }

    /**
     * Save preference.
     */
    protected void savePreference()
    {
        twitter.createEntityManager();
        twitter.savePreference(userId1, new Preference("P1", "Motif", "2"));
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        twitter.savePreference(userId2, new Preference("P2", "High Contrast", "3"));
        twitter.closeEntityManager();
    }

    /**
     * Adds the external links.
     */
    protected void addExternalLinks()
    {
        twitter.createEntityManager();
        twitter.addExternalLink(userId1, "L1", "Facebook", "http://facebook.com/coolnerd");
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        twitter.addExternalLink(userId1, "L2", "LinkedIn", "http://linkedin.com/in/devilmate");
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        twitter.addExternalLink(userId2, "L3", "GooglePlus", "http://plus.google.com/inviteme");
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        twitter.addExternalLink(userId2, "L4", "Yahoo", "http://yahoo.com/profiles/itsmeamry");
        twitter.closeEntityManager();
    }

    /**
     * Adds the tweets.
     */
    protected void addTweets()
    {
        twitter.createEntityManager();
        twitter.addTweet(userId1, "Here is my first tweet", "Web");
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        twitter.addTweet(userId1, "Second Tweet from me", "Mobile");
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        twitter.addTweet(userId2, "Saurabh tweets for the first time", "Phone");
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        twitter.addTweet(userId2, "Another tweet from Saurabh", "text");
        twitter.closeEntityManager();
    }

    /**
     * User1 follows user2.
     */
    protected void user1FollowsUser2()
    {
        twitter.createEntityManager();
        twitter.startFollowing(userId1, userId2);
        twitter.closeEntityManager();
    }

    /**
     * User1 adds user2 as follower.
     */
    protected void user1AddsUser2AsFollower()
    {
        twitter.createEntityManager();
        twitter.addFollower(userId1, userId2);
        twitter.closeEntityManager();
    }

    /**
     * Gets the all tweets.
     * 
     * @return the all tweets
     */
    protected void getAllTweets()
    {
        twitter.createEntityManager();
        List<Tweet> tweetsUser1 = twitter.getAllTweets(userId1);
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        List<Tweet> tweetsUser2 = twitter.getAllTweets(userId2);
        twitter.closeEntityManager();

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
        twitter.createEntityManager();
        List<User> follower1 = twitter.getFollowers(userId1);
        twitter.closeEntityManager();
        
        twitter.createEntityManager();
        List<User> follower2 = twitter.getFollowers(userId2);
        twitter.closeEntityManager();

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
>>>>>>> 042a0177dbbcdfadbed828241f3035f8dfdef57a
}
